package com.vaulka.kit.captcha.autoconfigure;

import com.vaulka.kit.captcha.properties.CaptchaProperties;
import com.vaulka.kit.captcha.utils.CaptchaUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 验证码工具自动装配
 *
 * @author Vaulka
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CaptchaProperties.class})
public class CaptchaAutoConfiguration {

    /**
     * 注入验证码工具
     *
     * @param properties 验证码参数配置
     * @return 注入验证码工具
     */
    @Bean
    public CaptchaUtils captchaUtils(CaptchaProperties properties) {
        return properties.getType().getUtils();
    }

}
