package com.pongsky.kit.validation.utils;

import java.util.List;

/**
 * 类解析工具
 *
 * @author Vaulka
 **/
public class ClassParserUtils {

    /**
     * 递归获取父 class 列表
     *
     * @param classes class 列表
     * @param clazz   class
     */
    public static void getSuperClasses(List<Class<?>> classes, Class<?> clazz) {
        classes.add(clazz);
        if (!clazz.getSuperclass().getName().equals(Object.class.getName())) {
            ClassParserUtils.getSuperClasses(classes, clazz.getSuperclass());
        }
    }

}
