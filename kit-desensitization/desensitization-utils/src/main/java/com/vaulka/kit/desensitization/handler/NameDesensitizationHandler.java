package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 名称脱敏
 * <p>
 * 示例：彭*
 * 示例：彭*豪
 * 示例：彭**豪
 *
 * @author Vaulka
 */
public class NameDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 1, s.length() == 2 ? 0 : 1);
    }

}
