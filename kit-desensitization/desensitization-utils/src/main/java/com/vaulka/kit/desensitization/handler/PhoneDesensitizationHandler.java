package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 手机号脱敏
 * <p>
 * 示例：151***5510
 *
 * @author Vaulka
 */
public class PhoneDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 3, 4);
    }

}
