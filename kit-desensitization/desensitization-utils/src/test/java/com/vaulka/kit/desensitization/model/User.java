package com.vaulka.kit.desensitization.model;

import com.vaulka.kit.desensitization.annotation.Desensitization;
import com.vaulka.kit.desensitization.handler.BankCardDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.EmailDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.IdCardDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.NameDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.PasswordDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.PhoneDesensitizationHandler;
import lombok.Data;

/**
 * @author Vaulka
 */
@Data
public class User {

    /**
     * 银行卡脱敏
     */
    @Desensitization(handler = BankCardDesensitizationHandler.class)
    private String bankCard;

    /**
     * 邮箱脱敏
     */
    @Desensitization(handler = EmailDesensitizationHandler.class)
    private String email;

    /**
     * 身份证脱敏
     */
    @Desensitization(handler = IdCardDesensitizationHandler.class)
    private String idCard;

    /**
     * 姓名脱敏
     */
    @Desensitization(handler = NameDesensitizationHandler.class)
    private String name;

    /**
     * 密码脱敏
     */
    @Desensitization(handler = PasswordDesensitizationHandler.class)
    private String password;

    /**
     * 手机号脱敏
     */
    @Desensitization(handler = PhoneDesensitizationHandler.class)
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 描述
     */
    private String desc;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
