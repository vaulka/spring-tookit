package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 密码脱敏
 * <p>
 * 示例：******
 *
 * @author Vaulka
 */
public class PasswordDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 0, 0);
    }

}
