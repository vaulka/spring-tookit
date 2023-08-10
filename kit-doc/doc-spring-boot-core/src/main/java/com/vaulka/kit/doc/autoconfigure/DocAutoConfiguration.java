package com.vaulka.kit.doc.autoconfigure;

import com.vaulka.kit.doc.properties.DocProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口文档自动装配
 *
 * @author Vaulka
 **/
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DocProperties.class})
public class DocAutoConfiguration {

    private final DocProperties properties;
    private final ApplicationContext applicationContext;

    /**
     * 注入接口文档信息
     *
     * @return 注入接口文档信息
     */
    @Bean
    public OpenAPI defaultOpenApi() {
        Map<String, SecurityScheme> securitySchemes = properties.getRequestParameters().entrySet().stream()
                .map(e -> e.getValue().stream()
                        .map(v -> new SecurityScheme()
                                .name(v)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(e.getKey()))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(SecurityScheme::getName, v -> v));
        SecurityRequirement securityRequirement = new SecurityRequirement();
        properties.getRequestParameters().values().stream()
                .flatMap(Collection::stream)
                .toList().forEach(securityRequirement::addList);
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact().name(properties.getAuthor()))
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .version(properties.getVersion()))
                .components(new Components().securitySchemes(securitySchemes))
                .addSecurityItem(securityRequirement);
    }

    /**
     * 默认分组
     */
    private static final DocProperties.GroupOpenApi DEFAULT_GROUPED_OPEN_API = new DocProperties.GroupOpenApi("default", "default");

    /**
     * 注入接口文档组别信息
     *
     * @return 注入接口文档组别信息
     */
    @Bean
    public GroupedOpenApi defaultGroupedOpenApi() {
        if (properties.getGroups().size() == 0) {
            // 未填写组别则会有一个默认分组
            // 兼容 Knife4j 源码，没有分组则会报错
            return DEFAULT_GROUPED_OPEN_API.build(properties.getRequestParameters(), properties.isEnabled());
        }
        List<String> displayNames = properties.getGroups().stream()
                .map(DocProperties.GroupOpenApi::getDisplayName)
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        if (displayNames.size() > 0) {
            throw new IllegalArgumentException(MessageFormat.format("组别名称 displayName {0} 出现重复", displayNames));
        }
        List<String> groups = properties.getGroups().stream()
                .map(DocProperties.GroupOpenApi::getGroup)
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        if (groups.size() > 0) {
            throw new IllegalArgumentException(MessageFormat.format("组别路径 group {0} 出现重复", groups));
        }
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
                ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        GroupedOpenApi defaultGroupedOpenApi = null;
        for (int i = 0; i < properties.getGroups().size(); i++) {
            GroupedOpenApi groupedOpenApi = properties.getGroups().get(i).build(properties.getRequestParameters(), properties.isEnabled());
            if (i == 0) {
                defaultGroupedOpenApi = groupedOpenApi;
                continue;
            }
            beanFactory.registerSingleton(MessageFormat.format("{0}-GroupedOpenAPI", groupedOpenApi.getDisplayName()), groupedOpenApi);
        }
        return defaultGroupedOpenApi;
    }

}
