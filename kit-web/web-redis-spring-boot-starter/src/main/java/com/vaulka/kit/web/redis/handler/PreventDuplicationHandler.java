package com.vaulka.kit.web.redis.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 防重处理器
 *
 * @author Vaulka
 */
public interface PreventDuplicationHandler {

    /**
     * 是否放行
     *
     * @param request   request
     * @param signature signature
     * @param method    method
     * @return 是否放行
     */
    boolean release(HttpServletRequest request, MethodSignature signature, Method method);

}
