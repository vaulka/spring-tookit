package com.vaulka.kit.validation;


import com.pongsky.kit.validation.utils.CollectionParserUtils;
import com.vaulka.kit.validation.model.FieldUser;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class CollectionParserUtilsTest {

    /**
     * 测试集合解析
     */
    @Test
    public void validation() throws NoSuchFieldException {
        FieldUser user = new FieldUser();
        Class<?> elementType = CollectionParserUtils.getElementType(user.getClass().getDeclaredField("list"));
        assert elementType.getName().equals(Integer.class.getName());
    }

}
