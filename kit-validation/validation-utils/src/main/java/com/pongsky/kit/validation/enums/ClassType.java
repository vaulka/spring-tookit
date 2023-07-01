package com.pongsky.kit.validation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类类型
 *
 * @author Vaulka
 **/
@Getter
@AllArgsConstructor
public enum ClassType {

    /**
     * Byte
     */
    BYTE(Byte.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Byte;
        }
    },

    /**
     * Short
     */
    SHORT(Short.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Short;
        }
    },

    /**
     * Integer
     */
    INTEGER(Integer.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Integer;
        }
    },

    /**
     * Long
     */
    LONG(Long.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Long;
        }
    },

    /**
     * Float
     */
    FLOAT(Float.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Float;
        }
    },

    /**
     * Double
     */
    DOUBLE(Double.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Double;
        }
    },

    /**
     * BigInteger
     */
    BIG_INTEGER(BigInteger.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof BigInteger;
        }
    },

    /**
     * BigDecimal
     */
    BIG_DECIMAL(BigDecimal.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof BigDecimal;
        }
    },

    /**
     * Boolean
     */
    BOOLEAN(Boolean.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Boolean;
        }
    },

    /**
     * Character
     */
    CHARACTER(Character.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Character;
        }
    },

    /**
     * String
     */
    STRING(String.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof String;
        }
    },

    /**
     * Array
     */
    ARRAY("Array", 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Object[];
        }
    },

    /**
     * List
     */
    LIST(List.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof List;
        }
    },

    /**
     * Set
     */
    SET(Set.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Set;
        }
    },

    /**
     * Map
     */
    MAP(Map.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Map;
        }
    },

    /**
     * Date
     */
    DATE(Date.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Date;
        }
    },

    /**
     * LocalDate
     */
    LOCAL_DATE(LocalDate.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof LocalDate;
        }
    },

    /**
     * LocalTime
     */
    LOCAL_TIME(LocalTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof LocalTime;
        }
    },

    /**
     * LocalDateTime
     */
    LOCAL_DATE_TIME(LocalDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof LocalDateTime;
        }
    },

    /**
     * MonthDay
     */
    MONTH_DAY(MonthDay.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof MonthDay;
        }
    },

    /**
     * Instant
     */
    INSTANT(Instant.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Instant;
        }
    },

    /**
     * OffsetDateTime
     */
    OFFSET_DATE_TIME(OffsetDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof OffsetDateTime;
        }
    },

    /**
     * OffsetTime
     */
    OFFSET_TIME(OffsetTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof OffsetTime;
        }
    },

    /**
     * Year
     */
    YEAR(Year.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Year;
        }
    },

    /**
     * YearMonth
     */
    YEAR_MONTH(YearMonth.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof YearMonth;
        }
    },

    /**
     * ZonedDateTime
     */
    ZONED_DATE_TIME(ZonedDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof ZonedDateTime;
        }
    },

    /**
     * DayOfWeek
     */
    DAY_OF_WEEK(DayOfWeek.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof DayOfWeek;
        }
    },

    /**
     * Month
     */
    MONTH(Month.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Month;
        }
    },

    /**
     * Enum
     */
    ENUM(Enum.class.getSimpleName(), Integer.MAX_VALUE) {
        @Override
        public boolean parser(Object obj) {
            return obj instanceof Enum;
        }
    },

    /**
     * Null
     */
    NULL("Null", Integer.MAX_VALUE) {
        @Override
        public boolean parser(Object obj) {
            return obj == null;
        }
    },

    /**
     * Object
     * <p>
     * 兜底类型
     */
    OBJECT("Object", Integer.MAX_VALUE) {
        @Override
        public boolean parser(Object obj) {
            return false;
        }
    },

    ;

    /**
     * 类类型
     */
    private final String type;

    /**
     * 类解析顺序
     * <p>
     * 有些 Java 内置常用枚举优先解析成特定类型
     */
    private final Integer sort;

    /**
     * 校验类类型是否匹配
     *
     * @param obj obj
     * @return 校验类类型是否匹配
     */
    public abstract boolean parser(Object obj);

    /**
     * 所有类类型列表
     */
    private static final List<ClassType> ALL = Arrays.stream(values())
            .sorted(Comparator.comparing(ct -> ct.sort))
            .toList();

    /**
     * 解析类类型
     *
     * @param obj 类
     * @return 解析类类型
     */
    public static ClassType parserType(Object obj) {
        return ALL.stream()
                .filter(ct -> ct.parser(obj))
                .findFirst()
                .orElse(ClassType.OBJECT);
    }

}
