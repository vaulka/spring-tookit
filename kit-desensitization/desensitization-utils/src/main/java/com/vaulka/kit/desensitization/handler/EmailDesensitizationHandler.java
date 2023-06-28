package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 邮箱脱敏
 * <p>
 * 示例：ke***@vip.qq.com
 *
 * @author Vaulka
 */
public class EmailDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 2, s.length() - s.indexOf("@"));
    }

}
