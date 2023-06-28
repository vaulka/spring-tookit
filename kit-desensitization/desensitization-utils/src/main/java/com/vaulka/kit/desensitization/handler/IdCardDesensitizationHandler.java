package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 身份证脱敏
 * <p>
 * 示例：350***********2510
 *
 * @author Vaulka
 */
public class IdCardDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 3, 4);
    }

}
