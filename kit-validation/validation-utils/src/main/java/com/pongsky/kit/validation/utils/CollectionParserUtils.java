package com.pongsky.kit.validation.utils;

import java.lang.reflect.Field;

/**
 * 集合解析工具
 *
 * @author Vaulka
 **/
public class CollectionParserUtils {

    /**
     * 左尖括号
     */
    private static final String LEFT_ANGLE_BRACKET = "<";

    /**
     * 左尖括号
     */
    private static final String RIGHT_ANGLE_BRACKET = ">";

    /**
     * 获取集合的元素类型
     *
     * @param field 字段
     * @return 获取集合的元素类型
     */
    public static Class<?> getElementType(Field field) {
        if (field == null) {
            return null;
        }
        String typeName = field.getGenericType().getTypeName();
        int beginIndex = typeName.indexOf(LEFT_ANGLE_BRACKET) + 1;
        int endIndex = typeName.indexOf(RIGHT_ANGLE_BRACKET);
        try {
            String clazzName = typeName.substring(beginIndex, endIndex);
            return Class.forName(clazzName);
        } catch (IndexOutOfBoundsException | ClassNotFoundException e) {
            return null;
        }
    }

}
