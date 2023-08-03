package com.vaulka.kit.web.redis.aspect.before;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaulka.kit.common.exception.FrequencyException;
import com.vaulka.kit.web.filter.RepeatedlyReadRequestWrapper;
import com.vaulka.kit.web.redis.annotation.PreventDuplication;
import com.vaulka.kit.web.redis.handler.PreventDuplicationHandler;
import com.vaulka.kit.web.redis.properties.PreventDuplicationProperties;
import com.vaulka.kit.web.utils.HttpServletRequestUtils;
import com.vaulka.kit.web.utils.IpUtils;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 防重检测
 *
 * @author Vaulka
 */
@Aspect
@ConditionalOnProperty(name = "kit.redis.prevent-duplication.enabled", havingValue = "true", matchIfMissing = true)
public class PreventDuplicationAspect {

    private final ObjectMapper jsonMapper;
    private final PreventDuplicationHandler handler;
    private final PreventDuplicationProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;

    public PreventDuplicationAspect(ObjectMapper jsonMapper,
                                    PreventDuplicationProperties properties,
                                    RedisTemplate<String, Object> redisTemplate,
                                    ApplicationContext applicationContext) {
        this.jsonMapper = jsonMapper;
        this.properties = properties;
        this.redisTemplate = redisTemplate;
        this.handler = applicationContext.getBeansOfType(PreventDuplicationHandler.class).values().stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * 防重 key
     * <p>
     * ip:sign
     */
    private static final String PREVENT_DUPLICATION_KEY = "prevent-duplication:{0}:{1}";

    @Before("(@within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)) ")
    public void exec(JoinPoint point) throws IOException {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 判断是否放行
        boolean result = handler != null && handler.release(request, signature, method);
        if (result) {
            return;
        }
        // 先在方法上寻找该注解
        PreventDuplication preventDuplication = Optional.ofNullable(AnnotationUtils.findAnnotation(method, PreventDuplication.class))
                // 方法上没有的话，则再类上寻找该注解
                .orElse(AnnotationUtils.findAnnotation(signature.getDeclaringType(), PreventDuplication.class));
        // 没有设置防重频率则默认为 100ms 间隔
        int frequency = preventDuplication != null ? preventDuplication.frequency() : PreventDuplication.DEFAULT_FREQUENCY;
        TimeUnit unit = preventDuplication != null ? preventDuplication.unit() : PreventDuplication.DEFAULT_UNIT;
        String ip = IpUtils.getIp(request);
        RequestInfo requestInfo = RequestInfo.builder()
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .query(Optional.ofNullable(request.getQueryString()).orElse(""))
                .requestBody(request instanceof RepeatedlyReadRequestWrapper
                        ? HttpServletRequestUtils.getBody(request)
                        : "")
                .build();
        String sign = DigestUtils.sha1DigestAsHex(jsonMapper.writeValueAsString(requestInfo));
        String key = MessageFormat.format(PREVENT_DUPLICATION_KEY, ip, sign);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            throw new FrequencyException(properties.getExceptionMessage());
        }
        redisTemplate.opsForValue().set(key, "", frequency, unit);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {

        /**
         * method
         */
        private String method;

        /**
         * uri
         */
        private String uri;

        /**
         * query
         */
        private String query;

        /**
         * requestBody
         */
        private String requestBody;

    }

}
