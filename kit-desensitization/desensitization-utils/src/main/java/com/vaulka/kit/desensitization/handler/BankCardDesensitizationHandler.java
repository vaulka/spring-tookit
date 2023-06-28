package com.vaulka.kit.desensitization.handler;

import com.vaulka.kit.desensitization.utils.DesensitizationUtils;

import java.util.function.Function;

/**
 * 银行卡号脱敏
 * <p>
 * 示例：6217********1201
 *
 * @author Vaulka
 */
public class BankCardDesensitizationHandler implements Function<String, String> {

    @Override
    public String apply(String s) {
        return DesensitizationUtils.exec(s, 4, 4);
    }

}
