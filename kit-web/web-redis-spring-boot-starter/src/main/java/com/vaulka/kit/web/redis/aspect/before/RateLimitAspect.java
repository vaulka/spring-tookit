package com.vaulka.kit.web.redis.aspect.before;

import com.vaulka.kit.common.exception.FrequencyException;
import com.vaulka.kit.redis.properties.RedisCacheProperties;
import com.vaulka.kit.web.redis.handler.RateLimitHandler;
import com.vaulka.kit.web.redis.properties.RateLimitProperties;
import com.vaulka.kit.web.redis.script.RedisRateLimitScript;
import com.vaulka.kit.web.utils.IpUtils;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collections;

/**
 * 令牌桶限流
 *
 * @author Vaulka
 */
@Aspect
@ConditionalOnProperty(name = "kit.redis.rate-limit.enabled", havingValue = "true", matchIfMissing = true)
public class RateLimitAspect {

    private final RateLimitHandler handler;
    private final RedisCacheProperties cacheProperties;
    private final RateLimitProperties rateLimitProperties;
    private final RedisTemplate<String, String> redisTemplate;

    public RateLimitAspect(RedisCacheProperties cacheProperties,
                           RateLimitProperties rateLimitProperties,
                           RedisTemplate<String, String> redisTemplate,
                           ApplicationContext applicationContext) {
        this.cacheProperties = cacheProperties;
        this.rateLimitProperties = rateLimitProperties;
        this.redisTemplate = redisTemplate;
        this.handler = applicationContext.getBeansOfType(RateLimitHandler.class).values().stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * 默认限流 key
     * <p>
     * ip:method:uri
     */
    private static final String DEFAULT_RATE_LIMIT_KEY = "rate-limit:{0}:{1}:{2}";

    @Before("(@within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)) ")
    public void exec(JoinPoint point) {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 判断是否放行
        String key;
        int rate, max;
        if (handler == null) {
            key = MessageFormat.format(DEFAULT_RATE_LIMIT_KEY, IpUtils.getIp(request), request.getMethod(), request.getRequestURI());
            rate = rateLimitProperties.getBucketRate();
            max = rateLimitProperties.getBucketMax();
        } else {
            boolean result = handler.release(request, signature, method);
            if (result) {
                return;
            }
            key = handler.getKey(request, signature, method);
            rate = handler.getBucketRate(request, signature, method);
            max = handler.getBucketMax(request, signature, method);
        }
        this.validation(key, max, rate);
    }

    /**
     * 限流 lua 脚本
     */
    private static final RedisRateLimitScript REDIS_RATE_LIMIT_SCRIPT = new RedisRateLimitScript();


    /**
     * 校验是否要限流
     *
     * @param key  key
     * @param max  令牌桶大小
     * @param rate 令牌每秒恢复速度
     */
    private void validation(String key, int max, int rate) {
        String result = redisTemplate.execute(REDIS_RATE_LIMIT_SCRIPT,
                Collections.singletonList(MessageFormat.format("{0}:{1}", cacheProperties.getPrefix(), key)),
                Integer.toString(max),
                Integer.toString(rate),
                Long.toString(System.currentTimeMillis()));
        if (RedisRateLimitScript.ERROR_RESULT.equals(result)) {
            throw new FrequencyException(rateLimitProperties.getExceptionMessage());
        }
    }

}
