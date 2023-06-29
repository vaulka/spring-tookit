package com.vaulka.kit.desensitization.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vaulka.kit.desensitization.model.User;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class DesensitizationUtilsTest {

    /**
     * Jackson 序列化/反序列化 配置
     */
    public static final ObjectMapper MAPPER = JsonMapper.builder()
            // 只序列号非空属性
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
            // 反序列化的时候如果多了其他属性,不抛出异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    /**
     * 数据脱敏
     *
     * @throws JsonProcessingException JSON 处理异常
     */
    @Test
    public void desensitization() throws JsonProcessingException {
        User user = new User();
        user.setBankCard("6217681830027807807");
        user.setEmail("vaulka7@gmail.com");
        user.setIdCard("371257199507072277");
        user.setName("Vaulka");
        user.setPassword("123456");
        user.setPhone("15753678879");
        user.setUsername("vaulka");
        String json = MAPPER.writeValueAsString(user);
        System.out.println(json);
        user = MAPPER.readValue(json, User.class);
        assert user.getBankCard().equals("6217***********7807");
        assert user.getEmail().equals("va*****@gmail.com");
        assert user.getIdCard().equals("371***********2277");
        assert user.getName().equals("V****a");
        assert user.getPassword().equals("******");
        assert user.getPhone().equals("157****8879");
        assert user.getUsername().equals("vaulka");
    }

}
