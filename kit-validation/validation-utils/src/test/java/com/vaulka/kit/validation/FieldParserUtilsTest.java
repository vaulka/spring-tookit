package com.vaulka.kit.validation;


import com.pongsky.kit.validation.utils.FieldParserUtils;
import com.vaulka.kit.validation.model.FieldUser;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Vaulka
 */
public class FieldParserUtilsTest {

    /**
     * 测试字段解析
     */
    @Test
    public void validation() {
        List<Field> fields = FieldParserUtils.getSuperFields(FieldUser.class, true);
        assert fields.size() == 40;

        fields = FieldParserUtils.getSuperFields(FieldUser.class, false);
        assert fields.size() == 41;
    }

}
