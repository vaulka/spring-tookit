package com.vaulka.kit.validation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
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
 * 字段类型
 *
 * @author Vaulka
 **/
@Getter
@AllArgsConstructor
public enum FieldType {

    /**
     * Byte
     */
    BYTE(Byte.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == byte.class || field.getType() == Byte.class;
        }
    },

    /**
     * Short
     */
    SHORT(Short.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == short.class || field.getType() == Short.class;
        }
    },

    /**
     * Integer
     */
    INTEGER(Integer.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == int.class || field.getType() == Integer.class;
        }
    },

    /**
     * Long
     */
    LONG(Long.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == long.class || field.getType() == Long.class;
        }
    },

    /**
     * Float
     */
    FLOAT(Float.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == float.class || field.getType() == Float.class;
        }
    },

    /**
     * Double
     */
    DOUBLE(Double.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == double.class || field.getType() == Double.class;
        }
    },

    /**
     * BigInteger
     */
    BIG_INTEGER(BigInteger.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == BigInteger.class;
        }
    },

    /**
     * BigDecimal
     */
    BIG_DECIMAL(BigDecimal.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == BigDecimal.class;
        }
    },

    /**
     * Boolean
     */
    BOOLEAN(Boolean.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == boolean.class || field.getType() == Boolean.class;
        }
    },

    /**
     * Character
     */
    CHARACTER(Character.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == char.class || field.getType() == Character.class;
        }
    },

    /**
     * String
     */
    STRING(String.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == String.class;
        }
    },

    /**
     * Array
     */
    ARRAY("Array", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType().isArray();
        }
    },

    /**
     * List
     */
    LIST(List.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == List.class;
        }
    },

    /**
     * Set
     */
    SET(Set.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Set.class;
        }
    },

    /**
     * Map
     */
    MAP(Map.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Map.class;
        }
    },

    /**
     * Date
     */
    DATE(Date.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Date.class;
        }
    },

    /**
     * LocalDate
     */
    LOCAL_DATE(LocalDate.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == LocalDate.class;
        }
    },

    /**
     * LocalTime
     */
    LOCAL_TIME(LocalTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == LocalTime.class;
        }
    },

    /**
     * LocalDateTime
     */
    LOCAL_DATE_TIME(LocalDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == LocalDateTime.class;
        }
    },

    /**
     * * MonthDay
     */
    MONTH_DAY(MonthDay.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == MonthDay.class;
        }
    },

    /**
     * Instant
     */
    INSTANT(Instant.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Instant.class;
        }
    },

    /**
     * OffsetDateTime
     */
    OFFSET_DATE_TIME(OffsetDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == OffsetDateTime.class;
        }
    },

    /**
     * OffsetTime
     */
    OFFSET_TIME(OffsetTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == OffsetTime.class;
        }
    },

    /**
     * Year
     */
    YEAR(Year.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Year.class;
        }
    },

    /**
     * YearMonth
     */
    YEAR_MONTH(YearMonth.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == YearMonth.class;
        }
    },

    /**
     * ZonedDateTime
     */
    ZONED_DATE_TIME(ZonedDateTime.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == ZonedDateTime.class;
        }
    },

    /**
     * DayOfWeek
     */
    DAY_OF_WEEK(DayOfWeek.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == DayOfWeek.class;
        }
    },

    /**
     * Month
     */
    MONTH(Month.class.getSimpleName(), 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Month.class;
        }
    },

    /**
     * Enum
     */
    ENUM(Enum.class.getSimpleName(), Integer.MAX_VALUE) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType().isEnum();
        }
    },

    /**
     * Object
     * <p>
     * 兜底类型
     */
    OBJECT("Object", Integer.MAX_VALUE) {
        @Override
        public boolean parser(Field field) {
            return false;
        }
    },

    ;

    /**
     * 字段类型
     */
    private final String type;

    /**
     * 字段解析顺序
     * <p>
     * 有些 Java 内置常用枚举优先解析成特定类型
     */
    private final Integer sort;

    /**
     * 校验字段类型是否匹配
     *
     * @param field 字段
     * @return 校验字段类型是否匹配
     */
    public abstract boolean parser(Field field);

    /**
     * 所有 字段类型 列表
     */
    private static final List<FieldType> ALL = Arrays.stream(values())
            .sorted(Comparator.comparing(ft -> ft.sort))
            .toList();

    /**
     * 解析字段类型
     *
     * @param field 字段
     * @return 解析字段类型
     */
    public static FieldType parserType(Field field) {
        return ALL.stream()
                .filter(ft -> ft.parser(field))
                .findFirst()
                .orElse(FieldType.OBJECT);
    }

}
