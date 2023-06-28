package com.pongsky.kit.validation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
     * String
     */
    STRING("String", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == String.class;
        }
    },

    /**
     * Byte
     */
    BYTE("Byte", 0) {
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
    SHORT("Short", 0) {
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
    INTEGER("Integer", 0) {
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
    LONG("Long", 0) {
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
    FLOAT("Float", 0) {
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
    DOUBLE("Double", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == double.class || field.getType() == Double.class;
        }
    },

    /**
     * Character
     */
    CHARACTER("Character", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == char.class || field.getType() == Character.class;
        }
    },

    /**
     * Boolean
     */
    BOOLEAN("Boolean", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == boolean.class || field.getType() == Boolean.class;
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
    LIST("List", 0) {
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
    SET("Set", 0) {
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
    MAP("Map", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == Map.class;
        }
    },

    /**
     * BigInteger
     */
    BIG_INTEGER("BigInteger", 0) {
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
    BIG_DECIMAL("BigDecimal", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == BigDecimal.class;
        }
    },

    /**
     * Date
     */
    DATE("Date", 0) {
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
    LOCAL_DATE("LocalDate", 0) {
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
    LOCAL_TIME("LocalTime", 0) {
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
    LOCAL_DATE_TIME("LocalDateTime", 0) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType() == LocalDateTime.class;
        }
    },

    /**
     * DayOfWeek
     */
    DAY_OF_WEEK("DayOfWeek", 0) {
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
    MONTH("Month", 0) {
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
    ENUM("Enum", Integer.MAX_VALUE) {
        @Override
        public boolean parser(Field field) {
            if (field == null) {
                return false;
            }
            return field.getType().isEnum();
        }
    },

    /**
     * Null
     */
    NULL("Null", Integer.MAX_VALUE) {
        @Override
        public boolean parser(Field field) {
            return field == null;
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
