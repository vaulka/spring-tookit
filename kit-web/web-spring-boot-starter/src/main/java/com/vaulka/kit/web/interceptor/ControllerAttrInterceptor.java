package com.vaulka.kit.web.interceptor;

import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;

/**
 * 将调用到接口的 Controller、Method 注解加入 HttpServletRequest.Attribute 属性中
 *
 * @author Vaulka
 */
public class ControllerAttrInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        long reqId = RandomUtils.nextLong();
        request.setAttribute(SpringUtils.REQ_ID, reqId);
        if (handler instanceof HandlerMethod handlerMethod) {
            for (Annotation annotation : handlerMethod.getBeanType().getAnnotations()) {
                request.setAttribute(annotation.annotationType().getName(), annotation);
            }
            for (Annotation annotation : handlerMethod.getMethod().getAnnotations()) {
                request.setAttribute(annotation.annotationType().getName(), annotation);
            }
        }
        return true;
    }

}
