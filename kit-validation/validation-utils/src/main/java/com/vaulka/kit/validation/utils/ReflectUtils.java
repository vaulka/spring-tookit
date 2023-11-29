package com.vaulka.kit.validation.utils;

import java.lang.reflect.Field;

/**
 * 反射工具
 *
 * @author Vaulka
 */
public class ReflectUtils {

    /**
     * 获取属性值
     *
     * @param obj   obj
     * @param field 字段
     * @return 获取属性值
     */
    public static Object getValue(Object obj, Field field) {
        Object result;
        try {
            field.setAccessible(true);
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        } finally {
            field.setAccessible(false);
        }
        return result;
    }

    /**
     * 设置属性值
     *
     * @param obj   obj
     * @param field 字段
     * @param value 值
     */
    public static void setValue(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        } finally {
            field.setAccessible(false);
        }
    }

}
