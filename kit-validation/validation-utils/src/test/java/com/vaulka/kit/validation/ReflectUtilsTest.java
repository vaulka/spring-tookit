package com.vaulka.kit.validation;


import com.pongsky.kit.validation.utils.ReflectUtils;
import com.vaulka.kit.validation.model.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * @author Vaulka
 */
public class ReflectUtilsTest {

    /**
     * 测试反射解析
     *
     * @throws NoSuchFieldException 方法不存在异常
     */
    @Test
    public void validation() throws NoSuchFieldException {
        User user = new User();
        String name = "Vaulka";
        Field field = user.getClass().getDeclaredField("name");
        ReflectUtils.setValue(user, field, name);
        assert ReflectUtils.getValue(user, field).equals(name);
    }

}
