package com.vaulka.kit.validation;


import com.pongsky.kit.validation.enums.ClassType;
import com.vaulka.kit.validation.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vaulka
 */
public class ClassTypeTest {

    /**
     * 测试类类型
     */
    @Test
    public void validation() {
        assert ClassType.parserType((byte) 1) == ClassType.BYTE;
        assert ClassType.parserType(Byte.valueOf("1")) == ClassType.BYTE;

        assert ClassType.parserType((short) 1) == ClassType.SHORT;
        assert ClassType.parserType(Short.valueOf("1")) == ClassType.SHORT;

        assert ClassType.parserType(1) == ClassType.INTEGER;
        assert ClassType.parserType(Integer.valueOf("1")) == ClassType.INTEGER;

        assert ClassType.parserType((long) 1) == ClassType.LONG;
        assert ClassType.parserType(Long.valueOf("1")) == ClassType.LONG;

        assert ClassType.parserType((float) 1) == ClassType.FLOAT;
        assert ClassType.parserType(Float.valueOf("1")) == ClassType.FLOAT;

        assert ClassType.parserType((double) 1) == ClassType.DOUBLE;
        assert ClassType.parserType(Double.valueOf("1")) == ClassType.DOUBLE;

        assert ClassType.parserType(new BigInteger("1")) == ClassType.BIG_INTEGER;
        assert ClassType.parserType(new BigDecimal("1")) == ClassType.BIG_DECIMAL;

        assert ClassType.parserType((char) 1) == ClassType.CHARACTER;
        assert ClassType.parserType('1') == ClassType.CHARACTER;
        assert ClassType.parserType("1") == ClassType.STRING;

        assert ClassType.parserType(true) == ClassType.BOOLEAN;
        assert ClassType.parserType(Boolean.valueOf("true")) == ClassType.BOOLEAN;

        assert ClassType.parserType(new Integer[]{0, 1}) == ClassType.ARRAY;

        assert ClassType.parserType(List.of(1, 2)) == ClassType.LIST;
        assert ClassType.parserType(Set.of(1, 2)) == ClassType.SET;

        assert ClassType.parserType(Map.of(1, 2)) == ClassType.MAP;

        assert ClassType.parserType(new Date()) == ClassType.DATE;

        assert ClassType.parserType(LocalDate.now()) == ClassType.LOCAL_DATE;
        assert ClassType.parserType(LocalTime.now()) == ClassType.LOCAL_TIME;
        assert ClassType.parserType(LocalDateTime.now()) == ClassType.LOCAL_DATE_TIME;

        assert ClassType.parserType(MonthDay.now()) == ClassType.MONTH_DAY;
        assert ClassType.parserType(Instant.now()) == ClassType.INSTANT;
        assert ClassType.parserType(OffsetDateTime.now()) == ClassType.OFFSET_DATE_TIME;
        assert ClassType.parserType(OffsetTime.now()) == ClassType.OFFSET_TIME;
        assert ClassType.parserType(Year.now()) == ClassType.YEAR;
        assert ClassType.parserType(YearMonth.now()) == ClassType.YEAR_MONTH;
        assert ClassType.parserType(ZonedDateTime.now()) == ClassType.ZONED_DATE_TIME;

        assert ClassType.parserType(DayOfWeek.FRIDAY) == ClassType.DAY_OF_WEEK;
        assert ClassType.parserType(Month.AUGUST) == ClassType.MONTH;

        assert ClassType.parserType(ClassType.CHARACTER) == ClassType.ENUM;

        assert ClassType.parserType(null) == ClassType.NULL;

        assert ClassType.parserType(new User()) == ClassType.OBJECT;
    }
}
