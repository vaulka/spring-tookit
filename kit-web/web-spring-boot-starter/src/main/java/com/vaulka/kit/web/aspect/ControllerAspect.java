package com.vaulka.kit.web.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaulka.kit.web.filter.RepeatedlyReadRequestWrapper;
import com.vaulka.kit.web.properties.LogProperties;
import com.vaulka.kit.web.utils.HttpServletRequestUtils;
import com.vaulka.kit.web.utils.IpUtils;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Optional;

/**
 * 接口请求日志打印
 *
 * @author Vaulka
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ControllerAspect {

    private final ObjectMapper jsonMapper;
    private final LogProperties properties;

    /**
     * Controller 切入点
     */
    @Pointcut("(@within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)) ")
    public void point() {
    }

    /**
     * Controller 请求日志打印
     *
     * @throws IOException IO 异常
     */
    @Before("com.vaulka.kit.web.aspect.ControllerAspect.point()")
    public void exec() throws IOException {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        String reqId = SpringUtils.getReqId();
        boolean isLog = properties.getTypes().stream().anyMatch(m -> m.toString().equals(request.getMethod()));
        if (!isLog) {
            return;
        }
        String ip = IpUtils.getIp(request);
        String userAgent = Optional.ofNullable(request.getHeader(HttpHeaders.USER_AGENT)).orElse("");
        String authorization = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");
        log.info("");
        log.info("req ID [{}] IP [{}] User-Agent [{}] Authorization [{}]", reqId, ip, userAgent, authorization);
        log.info("req ID [{}] method [{}] URI [{}]", reqId, request.getMethod(), request.getRequestURI());
        log.info("req ID [{}] query [{}]", reqId, Optional.ofNullable(request.getQueryString()).orElse(""));
        log.info("req ID [{}] body [{}]", reqId, request instanceof RepeatedlyReadRequestWrapper
                ? HttpServletRequestUtils.getBody(request)
                : "");
    }

    /**
     * Controller 请求日志打印
     *
     * @param point point
     * @return 响应数据
     * @throws Throwable Throwable
     */
    @Around("com.vaulka.kit.web.aspect.ControllerAspect.point()")
    public Object exec(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        boolean isLog = properties.getTypes().stream().anyMatch(m -> m.toString().equals(request.getMethod()));
        if (!isLog) {
            return point.proceed();
        }
        String reqId = SpringUtils.getReqId();
        long start = System.currentTimeMillis();
        try {
            return point.proceed();
        } finally {
            log.info("req ID [{}] cost [{}] ms", reqId, System.currentTimeMillis() - start);
        }
    }

    /**
     * Controller 请求日志打印
     *
     * @param result 响应结果
     */
    @AfterReturning(value = "com.vaulka.kit.web.aspect.ControllerAspect.point()", returning = "result")
    public void exec(Object result) {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        boolean isLog = properties.getTypes().stream().anyMatch(m -> m.toString().equals(request.getMethod()));
        if (!isLog) {
            return;
        }
        String reqId = SpringUtils.getReqId();
        try {
            log.info("resp ID [{}] success [{}]", reqId, jsonMapper.writeValueAsString(Optional.ofNullable(result).orElse("")));
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            log.info("resp ID [{}] success [{}]", reqId, Optional.ofNullable(result).orElse(""));
        }
    }

    /**
     * Controller 请求日志打印
     *
     * @param exception 异常
     */
    @AfterThrowing(value = "com.vaulka.kit.web.aspect.ControllerAspect.point()", throwing = "exception")
    public void exec(Throwable exception) {
        String reqId = SpringUtils.getReqId();
        log.error("resp ID [{}] fail [{}]", reqId, Optional.ofNullable(exception.getLocalizedMessage()).orElse(""));
    }

}
