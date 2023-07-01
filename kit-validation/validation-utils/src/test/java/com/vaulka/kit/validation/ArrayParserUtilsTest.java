package com.vaulka.kit.validation;


import com.pongsky.kit.validation.utils.ArrayParserUtils;
import com.vaulka.kit.validation.model.FieldUser;
import com.vaulka.kit.validation.model.User;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class ArrayParserUtilsTest {

    /**
     * 测试数组解析
     *
     * @throws NoSuchFieldException 方法不存在异常
     */
    @Test
    public void validation() throws NoSuchFieldException {
        FieldUser user = new FieldUser();
        Class<?> fieldClementType = ArrayParserUtils.getElementType(user.getClass().getDeclaredField("array"));
        assert fieldClementType.getName().equals(User.class.getName());


        FieldUser[] users = {new FieldUser(), new FieldUser()};
        Class<?> classElementType = ArrayParserUtils.getElementType(users.getClass());
        assert classElementType.getName().equals(FieldUser.class.getName());
    }

}
