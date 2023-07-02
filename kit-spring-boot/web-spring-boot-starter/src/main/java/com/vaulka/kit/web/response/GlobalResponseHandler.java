package com.vaulka.kit.web.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaulka.kit.web.response.processor.fail.FailAroundProcessor;
import com.vaulka.kit.web.response.processor.fail.FailProcessor;
import com.vaulka.kit.web.response.processor.success.SuccessAroundProcessor;
import com.vaulka.kit.web.response.processor.success.SuccessProcessor;
import com.vaulka.kit.web.response.processor.supports.SupportsReturnTypeProcessor;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Spring MVC 全局响应处理器
 * <p>
 * code 标识如下：
 * <p>
 * 0：接口请求成功， > 0 接口请求失败
 * <p>
 * 101 ~ 199：Java、Spring MVC 内置异常
 * <p>
 * 201 ~ 299：操作异常
 * <p>
 * 301 ~ 399：用户行为异常
 * <p>
 * 401 ~ 499：登录态异常
 * <p>
 * 500 ~ 599：接口异常
 * <p>
 * 1000：系统内部异常
 *
 * @author Vaulka
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseHandler extends RequestResponseBodyMethodProcessor implements AsyncHandlerMethodReturnValueHandler {

    private final ObjectMapper jsonMapper;

    public GlobalResponseHandler(MappingJackson2HttpMessageConverter converter, ObjectMapper jsonMapper) {
        super(Collections.singletonList(converter));
        this.jsonMapper = jsonMapper;
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, @NonNull MethodParameter returnType) {
        return supportsReturnType(returnType);
    }

    /**
     * 是否执行全局响应处理器列表
     */
    private static final List<SupportsReturnTypeProcessor> SUPPORTS_RETURN_TYPE_PROCESSORS = new CopyOnWriteArrayList<>();

    /**
     * 【失败】全局响应环绕处理器列表
     */
    @SuppressWarnings({"rawtypes"})
    private static final List<FailAroundProcessor> FAIL_AROUND_PROCESSORS = new CopyOnWriteArrayList<>();

    /**
     * 【失败】全局响应处理器列表
     */
    @SuppressWarnings({"rawtypes"})
    private static final List<FailProcessor> FAIL_PROCESSORS = new CopyOnWriteArrayList<>();

    /**
     * 【成功】全局响应环绕处理器列表
     */
    private static final List<SuccessAroundProcessor> SUCCESS_AROUND_PROCESSORS = new CopyOnWriteArrayList<>();

    /**
     * 【成功】全局响应处理器列表
     */
    private static final List<SuccessProcessor> SUCCESS_PROCESSORS = new CopyOnWriteArrayList<>();

    /**
     * 添加所有的异常环绕处理器列表、异常处理器列表
     */
    @PostConstruct
    public void syncProcessors() {
        ApplicationContext applicationContext = SpringUtils.getApplicationContext();

        SUPPORTS_RETURN_TYPE_PROCESSORS.addAll(applicationContext.getBeansOfType(SupportsReturnTypeProcessor.class).values());

        SUCCESS_AROUND_PROCESSORS.addAll(applicationContext.getBeansOfType(SuccessAroundProcessor.class).values());
        SUCCESS_AROUND_PROCESSORS.sort(Comparator.comparing(SuccessAroundProcessor::order));
        SUCCESS_PROCESSORS.addAll(applicationContext.getBeansOfType(SuccessProcessor.class).values());

        FAIL_AROUND_PROCESSORS.addAll(applicationContext.getBeansOfType(FailAroundProcessor.class).values());
        FAIL_AROUND_PROCESSORS.sort(Comparator.comparing(FailAroundProcessor::order));
        FAIL_PROCESSORS.addAll(applicationContext.getBeansOfType(FailProcessor.class).values());
    }

    /**
     * 是否执行全局响应
     * <p>
     * 可自定义一些请求进行放行
     * <p>
     * 返回 true 则为放行
     *
     * @param returnType returnType
     * @return 是否执行全局响应
     */
    @Override
    public boolean supportsReturnType(@NonNull MethodParameter returnType) {
        boolean isExist = SUPPORTS_RETURN_TYPE_PROCESSORS.stream()
                .anyMatch(p -> p.supportsReturnType(returnType));
        if (isExist) {
            return false;
        }
        return super.supportsReturnType(returnType);
    }

    /**
     * 统一处理响应数据体
     * <p>
     * 包含特殊业务请求、成功请求、失败请求
     * <p>
     * 在 {@link RequestResponseBodyMethodProcessor#handleReturnValue(Object, MethodParameter, ModelAndViewContainer, NativeWebRequest)} 执行业务处理前，替换 returnValue 值，替换完后再接着走对应的业务处理
     * <p>
     * 不使用 implements {@link org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#beforeBodyWrite(Object, MethodParameter, org.springframework.http.MediaType, Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)} 方式去实现替换值的原因如下：
     * <p>
     * 在执行 {@link org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#beforeBodyWrite(Object, MethodParameter, org.springframework.http.MediaType, Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)} 之前已经确认好 {@link org.springframework.http.MediaType}、{@link HttpMessageConverter} ，
     * 在上层逻辑无法进行动态的修改这两个参数（这块都是 Spring 提供的，要修改源码破坏性太大，以及有一个其他办法。那就是自定义 {@link HttpMessageConverter} ，但是自带的就能用，没必要专门写一个 99% 重复代码率的类，只差在范型以及对应接收的请求头类型参数上）
     * <p>
     * 目前理论上能拦截所有请求进行封装，但是有一个漏网之鱼
     * <p>
     * 那就是 {link org.springframework.boot.autoconfigure.web.WebProperties.Resources.addMappings} 参数设置为 {@link Boolean#TRUE} 时，请求不存在的接口时会报 404 Not Found。
     *
     * @param returnValue  returnValue
     * @param returnType   returnType
     * @param mavContainer mavContainer
     * @param webRequest   webRequest
     * @throws HttpMediaTypeNotAcceptableException HTTP 媒体类型不可接受异常
     * @throws IOException                         IO 异常
     */
    @Override
    public void handleReturnValue(Object returnValue,
                                  @NonNull MethodParameter returnType,
                                  @NonNull ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException, IOException {
        Object result = returnValue;
        Class<?> methodReturnType = Objects.requireNonNull(returnType.getMethod()).getReturnType();
        HttpServletRequest request = SpringUtils.getHttpServletRequest(true);
        try {
            if (result != null && result.getClass() == ResponseEntity.class || methodReturnType == ResponseEntity.class) {
                if (returnType.getContainingClass() == GlobalExceptionHandler.class) {
                    // 已在全局异常处理过了，直接返回
                    result = this.getResponseEntityBody(false, result);
                    return;
                } else if (returnType.getContainingClass() == BasicErrorController.class && request != null) {
                    // 是否是 404 Not Found
                    Throwable exception = new NoHandlerFoundException(request.getMethod(), request.getRequestURI(),
                            new ServletServerHttpRequest(request).getHeaders());
                    result = this.processFail(exception);
                    return;
                }
                // 其他 Web 资源信息
                result = this.getResponseEntityBody(true, result);
                return;
            }
            if (request != null) {
                Object exception = request.getAttribute(GlobalExceptionHandler.class.getName());
                if (exception != null) {
                    result = this.processFail((Throwable) exception);
                    return;
                }
            }
            result = this.processSuccess(result);
        } finally {
            super.handleReturnValue(result, returnType, mavContainer, webRequest);
        }
    }

    /**
     * 获取 responseEntity body 数据
     *
     * @param isSuccess      请求是否成功
     * @param responseEntity responseEntity
     * @return 获取 responseEntity body 数据
     */
    private Object getResponseEntityBody(boolean isSuccess, Object responseEntity) {
        Object body = ((ResponseEntity<?>) responseEntity).getBody();
        String reqId = SpringUtils.getReqId();
        if (isSuccess) {
            try {
                log.info("resp ID [{}] success result [{}]", reqId, jsonMapper.writeValueAsString(Optional.ofNullable(body).orElse("")));
            } catch (JsonProcessingException e) {
                log.error(e.getLocalizedMessage());
                log.info("resp ID [{}] success result [{}]", reqId, Optional.ofNullable(body).orElse(""));
            }
        } else {
            try {
                log.error("resp ID [{}] fail result [{}]", reqId, jsonMapper.writeValueAsString(Optional.ofNullable(body).orElse("")));
            } catch (JsonProcessingException e) {
                log.error(e.getLocalizedMessage());
                log.error("resp ID [{}] fail result [{}]", reqId, Optional.ofNullable(body).orElse(""));
            }
        }
        return body;
    }

    /**
     * 处理失败请求
     *
     * @param exception 异常
     * @return 响应数据体
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object processFail(Throwable exception) {
        Object result = exception.getLocalizedMessage();
        boolean isHitAroundProcessor = false;
        // 首先执行环绕处理器
        for (FailAroundProcessor aroundProcessor : FAIL_AROUND_PROCESSORS) {
            if (!aroundProcessor.isHitProcessor(exception)) {
                continue;
            }
            result = aroundProcessor.exec(exception);
            aroundProcessor.execAfter(exception);
            isHitAroundProcessor = true;
            break;
        }
        // 其次匹配处理器
        Optional<FailProcessor> opProcessor = FAIL_PROCESSORS.stream()
                .filter(p -> p.isHitProcessor(exception))
                .max(Comparator.comparing(FailProcessor::order));
        if (!isHitAroundProcessor && opProcessor.isPresent()) {
            FailProcessor processor = opProcessor.get();
            result = processor.exec(exception);
            processor.log(exception);
            processor.execAfter(exception);
            HttpServletResponse response = SpringUtils.getHttpServletResponse();
            if (response != null) {
                response.setStatus(processor.httpStatus().value());
            }
        }
        String reqId = SpringUtils.getReqId();
        try {
            log.error("resp ID [{}] fail result [{}]", reqId, jsonMapper.writeValueAsString(Optional.ofNullable(result).orElse("")));
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            log.error("resp ID [{}] fail result [{}]", reqId, Optional.ofNullable(result).orElse(""));
        }
        return result;
    }

    /**
     * 处理成功请求
     *
     * @param body body
     * @return 响应数据体
     */
    private Object processSuccess(Object body) {
        Object result = body;
        boolean isHitAroundProcessor = false;
        // 首先执行环绕处理器
        for (SuccessAroundProcessor aroundProcessor : SUCCESS_AROUND_PROCESSORS) {
            if (!aroundProcessor.isHitProcessor(body)) {
                continue;
            }
            result = aroundProcessor.exec(body);
            aroundProcessor.execAfter(result);
            isHitAroundProcessor = true;
            break;
        }
        // 其次匹配处理器
        Optional<SuccessProcessor> opProcessor = SUCCESS_PROCESSORS.stream()
                .filter(SuccessProcessor::isHitProcessor)
                .max(Comparator.comparing(SuccessProcessor::order));
        if (!isHitAroundProcessor && opProcessor.isPresent()) {
            SuccessProcessor processor = opProcessor.get();
            result = processor.exec(body);
            processor.execAfter(result);
            HttpServletResponse response = SpringUtils.getHttpServletResponse();
            if (response != null) {
                response.setStatus(processor.httpStatus().value());
            }
        }
        String reqId = SpringUtils.getReqId();
        try {
            log.info("resp ID [{}] success result [{}]", reqId, jsonMapper.writeValueAsString(Optional.ofNullable(result).orElse("")));
        } catch (JsonProcessingException e) {
            log.info(e.getLocalizedMessage());
            log.info("resp ID [{}] success result [{}]", reqId, Optional.ofNullable(result).orElse(""));
        }
        return result;
    }

}