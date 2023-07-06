package com.vaulka.kit.validation;


import com.vaulka.kit.validation.enums.FieldType;
import com.vaulka.kit.validation.model.FieldUser;
import org.junit.jupiter.api.Test;

/**
 * @author Vaulka
 */
public class FieldTypeTest {

    /**
     * 测试字段类型
     *
     * @throws NoSuchFieldException 方法不存在异常
     */
    @Test
    public void validation() throws NoSuchFieldException {
        FieldUser user = new FieldUser();
        assert FieldType.parserType(user.getClass().getDeclaredField("by1")) == FieldType.BYTE;
        assert FieldType.parserType(user.getClass().getDeclaredField("by2")) == FieldType.BYTE;

        assert FieldType.parserType(user.getClass().getDeclaredField("sh1")) == FieldType.SHORT;
        assert FieldType.parserType(user.getClass().getDeclaredField("sh2")) == FieldType.SHORT;

        assert FieldType.parserType(user.getClass().getDeclaredField("in1")) == FieldType.INTEGER;
        assert FieldType.parserType(user.getClass().getDeclaredField("in2")) == FieldType.INTEGER;

        assert FieldType.parserType(user.getClass().getDeclaredField("lo1")) == FieldType.LONG;
        assert FieldType.parserType(user.getClass().getDeclaredField("lo2")) == FieldType.LONG;

        assert FieldType.parserType(user.getClass().getDeclaredField("fl1")) == FieldType.FLOAT;
        assert FieldType.parserType(user.getClass().getDeclaredField("fl2")) == FieldType.FLOAT;

        assert FieldType.parserType(user.getClass().getDeclaredField("do1")) == FieldType.DOUBLE;
        assert FieldType.parserType(user.getClass().getDeclaredField("do2")) == FieldType.DOUBLE;

        assert FieldType.parserType(user.getClass().getDeclaredField("bi")) == FieldType.BIG_INTEGER;
        assert FieldType.parserType(user.getClass().getDeclaredField("bd")) == FieldType.BIG_DECIMAL;

        assert FieldType.parserType(user.getClass().getDeclaredField("ch1")) == FieldType.CHARACTER;
        assert FieldType.parserType(user.getClass().getDeclaredField("ch2")) == FieldType.CHARACTER;
        assert FieldType.parserType(user.getClass().getDeclaredField("st")) == FieldType.STRING;

        assert FieldType.parserType(user.getClass().getDeclaredField("bo1")) == FieldType.BOOLEAN;
        assert FieldType.parserType(user.getClass().getDeclaredField("bo2")) == FieldType.BOOLEAN;

        assert FieldType.parserType(user.getClass().getDeclaredField("array")) == FieldType.ARRAY;

        assert FieldType.parserType(user.getClass().getDeclaredField("list")) == FieldType.LIST;
        assert FieldType.parserType(user.getClass().getDeclaredField("set")) == FieldType.SET;

        assert FieldType.parserType(user.getClass().getDeclaredField("map")) == FieldType.MAP;

        assert FieldType.parserType(user.getClass().getDeclaredField("date")) == FieldType.DATE;

        assert FieldType.parserType(user.getClass().getDeclaredField("localDate")) == FieldType.LOCAL_DATE;
        assert FieldType.parserType(user.getClass().getDeclaredField("localTime")) == FieldType.LOCAL_TIME;
        assert FieldType.parserType(user.getClass().getDeclaredField("localDateTime")) == FieldType.LOCAL_DATE_TIME;

        assert FieldType.parserType(user.getClass().getDeclaredField("monthDay")) == FieldType.MONTH_DAY;
        assert FieldType.parserType(user.getClass().getDeclaredField("instant")) == FieldType.INSTANT;
        assert FieldType.parserType(user.getClass().getDeclaredField("offsetDateTime")) == FieldType.OFFSET_DATE_TIME;
        assert FieldType.parserType(user.getClass().getDeclaredField("offsetTime")) == FieldType.OFFSET_TIME;
        assert FieldType.parserType(user.getClass().getDeclaredField("year")) == FieldType.YEAR;
        assert FieldType.parserType(user.getClass().getDeclaredField("yearMonth")) == FieldType.YEAR_MONTH;
        assert FieldType.parserType(user.getClass().getDeclaredField("zonedDateTime")) == FieldType.ZONED_DATE_TIME;

        assert FieldType.parserType(user.getClass().getDeclaredField("dayOfWeek")) == FieldType.DAY_OF_WEEK;
        assert FieldType.parserType(user.getClass().getDeclaredField("month")) == FieldType.MONTH;

        assert FieldType.parserType(user.getClass().getDeclaredField("classType")) == FieldType.ENUM;

        assert FieldType.parserType(user.getClass().getDeclaredField("user")) == FieldType.OBJECT;
    }
}
