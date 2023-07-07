package com.vaulka.kit.dynamic.datasource.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.util.Utils;
import com.vaulka.kit.dynamic.datasource.around.DynamicDatasourceAspect;
import com.vaulka.kit.dynamic.datasource.core.DynamicDatasource;
import com.vaulka.kit.dynamic.datasource.core.DynamicDatasourceContextHolder;
import com.vaulka.kit.dynamic.datasource.processor.DatasourceBeanFactoryPostProcessor;
import com.vaulka.kit.dynamic.datasource.properties.DynamicDatasourceProperties;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 动态数据源自动装配
 *
 * @author Vaulka
 **/
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DynamicDatasourceProperties.class})
@Import({DatasourceBeanFactoryPostProcessor.class, DynamicDatasourceAspect.class})
public class DynamicDatasourceAutoConfiguration {

    private final ApplicationContext applicationContext;

    /**
     * 动态数据源配置
     *
     * @param properties 多数据源配置
     * @return 动态数据源
     */
    @Bean
    @Primary
    public DynamicDatasource dynamicDataSource(DynamicDatasourceProperties properties) {
        String defaultDatasourceName = DynamicDatasourceContextHolder.DEFAULT_DATA_SOURCE_NAME;
        Map<Object, Object> targetDatasource = new ConcurrentHashMap<>(16);
        // 配置默认数据源
        DruidDataSourceWrapper defaultDataSource = this.registerDatasource(defaultDatasourceName);
        targetDatasource.put(defaultDatasourceName, defaultDataSource);
        // 校验配置信息
        this.validationMultiDataSourceProperties(properties, defaultDatasourceName, defaultDataSource);
        if (properties.getDynamicDatasource().size() > 0) {
            // 配置额外数据源
            // 连接池参数复用 druid 连接池参数，数据库连接配置信息未填写则复用默认配置信息
            for (Map.Entry<String, DynamicDatasourceProperties.DatasourceProperties> entry :
                    properties.getDynamicDatasource().entrySet()) {
                String datasourceName = entry.getKey();
                DynamicDatasourceProperties.DatasourceProperties prop = entry.getValue();
                DruidDataSourceWrapper datasource = this.registerDatasource(datasourceName);
                datasource.setUrl(prop.getUrl());
                Optional.ofNullable(prop.getDriverClassName()).filter(StringUtils::isNotBlank).ifPresent(datasource::setDriverClassName);
                Optional.ofNullable(prop.getUsername()).filter(StringUtils::isNotBlank).ifPresent(datasource::setUsername);
                Optional.ofNullable(prop.getPassword()).filter(StringUtils::isNotBlank).ifPresent(datasource::setPassword);
                targetDatasource.put(datasourceName, datasource);
            }
        }
        DynamicDatasourceContextHolder.setDataSourceNames(targetDatasource.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        return new DynamicDatasource(defaultDataSource, targetDatasource);
    }

    /**
     * 校验多数据源配置信息
     *
     * @param properties            多数据源配置信息
     * @param defaultDataSourceName 默认数据源名称
     * @param defaultDataSource     默认数据源
     */
    private void validationMultiDataSourceProperties(DynamicDatasourceProperties properties,
                                                     String defaultDataSourceName,
                                                     DruidDataSourceWrapper defaultDataSource) {
        if (properties.getDynamicDatasource().size() == 0) {
            return;
        }
        if (properties.getDynamicDatasource().containsKey(defaultDataSourceName)) {
            throw new IllegalArgumentException(MessageFormat.format("[{0}] 为默认数据源名称，禁止重名", defaultDataSourceName));
        }
        long duplicateUrls = properties.getDynamicDatasource().entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue().getUrl(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .count();
        if (duplicateUrls > 0) {
            throw new IllegalArgumentException(MessageFormat.format("动态数据源 url {0} 出现重复", duplicateUrls));
        }
        long count = properties.getDynamicDatasource().values().stream()
                .filter(d -> d.getUrl().equals(defaultDataSource.getUrl()))
                .count();
        if (count > 0) {
            throw new IllegalArgumentException(MessageFormat.format("动态数据源 url 与默认数据源 url [{0}] 出现重复", defaultDataSource.getUrl()));
        }
    }

    /**
     * 注册并获取数据源
     *
     * @param datasourceName 数据源名称
     * @return 注册并获取数据源
     */
    private DruidDataSourceWrapper registerDatasource(String datasourceName) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
                ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DruidDataSourceWrapper.class);
        beanFactory.registerBeanDefinition(datasourceName, beanDefinition);
        return (DruidDataSourceWrapper) beanFactory.getBean(datasourceName);
    }

    /**
     * 去除 druid 监控底部广告
     *
     * @return 拦截器
     * @throws IOException IOException
     */
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true")
    public FilterRegistrationBean<Filter> removeDruidAdvertiseFilter() throws IOException {
        String text = Utils.readFromResource("support/http/resources/js/common.js");
        text = text.replaceAll("this.buildFooter\\(\\);", "");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.addUrlPatterns("/druid/js/common.js");
        String finalText = text;
        registration.setFilter((servletRequest, servletResponse, filterChain) -> {
            servletResponse.resetBuffer();
            servletResponse.setContentType("text/javascript;charset=utf-8");
            servletResponse.getWriter().write(finalText);
        });
        return registration;
    }

}
