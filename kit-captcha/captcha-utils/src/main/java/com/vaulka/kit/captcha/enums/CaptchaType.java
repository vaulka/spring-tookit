package com.vaulka.kit.captcha.enums;

import com.vaulka.kit.captcha.utils.CaptchaUtils;
import com.vaulka.kit.captcha.utils.CharCaptchaUtils;
import com.vaulka.kit.captcha.utils.MathCaptchaUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码类型
 *
 * @author Vaulka
 */
@Getter
@AllArgsConstructor
public enum CaptchaType {

    /**
     * 字符
     */
    CHAR(new CharCaptchaUtils(5,
            5 * 25, 35,
            200, 2)),

    /**
     * 算数
     */
    MATH(new MathCaptchaUtils(10, 99,
            (String.valueOf(99).length() + 3) * 25, 35,
            200, 2)),

    ;

    /**
     * 验证码工具
     */
    private final CaptchaUtils utils;

}
