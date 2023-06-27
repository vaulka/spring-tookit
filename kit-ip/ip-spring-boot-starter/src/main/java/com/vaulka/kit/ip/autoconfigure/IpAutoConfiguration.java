package com.vaulka.kit.ip.autoconfigure;

import com.vaulka.kit.ip.config.IpSearcherImpl;
import com.vaulka.kit.ip.properties.IpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * IP 解析自动装配
 *
 * @author Vaulka
 */
@Configuration(proxyBeanMethods = false)
@Import({IpSearcherImpl.class})
@EnableConfigurationProperties({IpProperties.class})
public class IpAutoConfiguration {


}
