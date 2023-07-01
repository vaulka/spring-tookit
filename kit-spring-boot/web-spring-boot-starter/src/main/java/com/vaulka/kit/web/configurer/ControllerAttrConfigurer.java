package com.vaulka.kit.web.configurer;

import com.vaulka.kit.web.interceptor.ControllerAttrInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置 Controller Attr 属性拦截器
 *
 * @author Vaulka
 */
@RequiredArgsConstructor
public class ControllerAttrConfigurer implements WebMvcConfigurer {

    private final ControllerAttrInterceptor controllerAttrInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerAttrInterceptor).addPathPatterns("/**");
    }

}
