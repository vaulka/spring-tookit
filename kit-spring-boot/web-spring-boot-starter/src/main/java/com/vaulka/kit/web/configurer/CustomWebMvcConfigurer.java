package com.vaulka.kit.web.configurer;

import com.vaulka.kit.web.interceptor.ControllerAttrInterceptor;
import com.vaulka.kit.web.response.GlobalResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Vaulka
 */
@RequiredArgsConstructor
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    private final GlobalResponseHandler globalResponseHandler;
    private final ControllerAttrInterceptor controllerAttrInterceptor;

    /**
     * 配置 Controller Attr 属性拦截器
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerAttrInterceptor).addPathPatterns("/**");
    }

    /**
     * 全局响应配置器
     *
     * @param returnValueHandlers returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(globalResponseHandler);
    }

}
