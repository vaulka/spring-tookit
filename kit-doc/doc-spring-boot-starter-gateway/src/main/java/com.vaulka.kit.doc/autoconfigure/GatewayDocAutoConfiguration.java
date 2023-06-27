package com.vaulka.kit.doc.autoconfigure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

/**
 * 网关接口文档自动装配
 *
 * @author Vaulka
 **/
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class GatewayDocAutoConfiguration {

    /**
     * 服务端口号
     */
    @Value("${server.port}")
    private Integer port;

    /**
     * 注入网关路由定位器
     *
     * @param builder builder
     * @return 注入网关路由定位器
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("swagger", r -> r
                        .path("/v3/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/v3/api-docs/(?<path>.*)", "/$\\{path}/v3/api-docs"))
                        .uri(MessageFormat.format("http://localhost:{0}", port.toString())))
                .build();
    }

}
