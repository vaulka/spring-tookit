package com.vaulka.kit.desensitization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaulka.kit.desensitization.annotation.Desensitization;
import com.vaulka.kit.desensitization.handler.BankCardDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.EmailDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.IdCardDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.NameDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.PasswordDesensitizationHandler;
import com.vaulka.kit.desensitization.handler.PhoneDesensitizationHandler;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class DesensitizationUtilsTest {

    public static class User {

        /**
         * 银行卡脱敏
         */
        @Desensitization(handler = BankCardDesensitizationHandler.class)
        private String bankCard = "6217681830027807807";

        /**
         * 邮箱脱敏
         */
        @Desensitization(handler = EmailDesensitizationHandler.class)
        private String email = "vaulka7@gmail.com";

        /**
         * 身份证脱敏
         */
        @Desensitization(handler = IdCardDesensitizationHandler.class)
        private String idCard = "371257199507072277";

        /**
         * 姓名脱敏
         */
        @Desensitization(handler = NameDesensitizationHandler.class)
        private String name = "彭X豪";

        /**
         * 密码脱敏
         */
        @Desensitization(handler = PasswordDesensitizationHandler.class)
        private String password = "123456";

        /**
         * 手机号脱敏
         */
        @Desensitization(handler = PhoneDesensitizationHandler.class)
        private String phone = "15753678879";

        private String username = "username";

        private String account = null;

        private String desc;

        public String getBankCard() {
            return bankCard;
        }

        public String getEmail() {
            return email;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        System.out.println(MAPPER.writeValueAsString(new User()));
        System.out.println(new User());
    }

}
