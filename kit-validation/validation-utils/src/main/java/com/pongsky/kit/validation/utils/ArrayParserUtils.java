package com.pongsky.kit.validation.utils;

import java.lang.reflect.Field;

/**
 * 数组解析工具
 *
 * @author Vaulka
 **/
public class ArrayParserUtils {

    /**
     * 获取数组的元素类型
     *
     * @param field 字段
     * @return 获取数组的元素类型
     */
    public static Class<?> getElementType(Field field) {
        if (field == null) {
            return null;
        }
        return field.getType().getComponentType();
    }

    /**
     * 获取数组的元素类型
     *
     * @param clazz 类
     * @return 获取数组的元素类型
     */
    public static Class<?> getElementType(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getComponentType();
    }

}
