package com.vaulka.kit.validation;


import com.pongsky.kit.validation.utils.ClassParserUtils;
import com.vaulka.kit.validation.model.FieldUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaulka
 */
public class ClassParserUtilsTest {

    /**
     * 测试类解析
     */
    @Test
    public void validation() {
        List<Class<?>> classes = new ArrayList<>();
        ClassParserUtils.getSuperClasses(classes, FieldUser.class);
        assert classes.size() == 2;
    }

}
