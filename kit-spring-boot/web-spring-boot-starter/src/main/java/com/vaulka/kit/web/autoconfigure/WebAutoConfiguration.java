package com.vaulka.kit.web.autoconfigure;

import com.vaulka.kit.web.aspect.ControllerAspect;
import com.vaulka.kit.web.config.JacksonConfiguration;
import com.vaulka.kit.web.config.SpringMvcConfiguration;
import com.vaulka.kit.web.configurer.ControllerAttrConfigurer;
import com.vaulka.kit.web.filter.RepeatedlyReadFilter;
import com.vaulka.kit.web.filter.XssFilter;
import com.vaulka.kit.web.interceptor.ControllerAttrInterceptor;
import com.vaulka.kit.web.properties.WebLogProperties;
import com.vaulka.kit.web.properties.XssProperties;
import com.vaulka.kit.web.utils.SpringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * Web 自动装配
 *
 * @author Vaulka
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({WebLogProperties.class, XssProperties.class})
@Import({
        JacksonConfiguration.class,
        SpringMvcConfiguration.class,
        SpringUtils.class,
        ControllerAspect.class,
        ControllerAttrConfigurer.class,
        ControllerAttrInterceptor.class
})
public class WebAutoConfiguration {

    /**
     * Request Body 数据多次读取
     *
     * @return Request Body 数据多次读取
     */
    @Bean
    public FilterRegistrationBean<RepeatedlyReadFilter> repeatedlyReadFilter() {
        FilterRegistrationBean<RepeatedlyReadFilter> registration = new FilterRegistrationBean<>();
        RepeatedlyReadFilter filter = new RepeatedlyReadFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName(filter.getClass().getName());
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    /**
     * XSS 防御
     *
     * @param properties XSS 防御 参数配置
     * @return XSS 防御
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties) {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        XssFilter filter = new XssFilter(properties.getExcludes());
        registration.setFilter(filter);
        registration.addUrlPatterns(properties.getUrlPatterns().toArray(new String[0]));
        registration.setName(filter.getClass().getName());
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

    /**
     * 配置跨域访问
     *
     * @param source source
     * @return 配置跨域访问
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(UrlBasedCorsConfigurationSource source) {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        CorsFilter filter = new CorsFilter(source);
        registration.setFilter(filter);
        registration.setName(filter.getClass().getName());
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

    /**
     * 配置跨域访问
     * <p>
     * 兼容 Spring Security 跨域访问
     *
     * @return 配置跨域访问
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        // 允许客户端发送 Cookie 等凭证信息
        cors.setAllowCredentials(true);
        // 允许所有原始域进行跨域访问
        cors.setAllowedOrigins(Collections.singletonList("*"));
        // 允许所所有请求头携带访问
        cors.setAllowedHeaders(Collections.singletonList("*"));
        // 允许所有请求方式访问
        cors.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 跨域访问对所有接口生效
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
