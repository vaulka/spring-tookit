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
@ConfigurationProperties("kit.captcha")
public class CaptchaProperties {

    /**
     * 验证码类型
     */
    private CaptchaType type = CaptchaType.CHAR;

}
