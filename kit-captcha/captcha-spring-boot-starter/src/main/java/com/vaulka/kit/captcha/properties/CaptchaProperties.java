package com.vaulka.kit.captcha.properties;

import com.vaulka.kit.captcha.enums.CaptchaType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码参数配置
 *
 * @author Vaulka
 */
@Getter
@Setter
@ConfigurationProperties(CaptchaProperties.PREFIX)
public class CaptchaProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "kit.captcha";

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 验证码类型
     */
    private CaptchaType type;

}
