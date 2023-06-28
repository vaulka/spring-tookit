package com.pongsky.kit.validation.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段解析工具
 *
 * @author Vaulka
 **/
public class FieldParserUtils {

    /**
     * 递归获取父 字段 列表
     *
     * @param clazz         class
     * @param isFilterFinal 是否过滤 final 修饰的字段
     * @return 递归获取父 字段 列表
     */
    public static List<Field> getSuperFields(Class<?> clazz, boolean isFilterFinal) {
        List<Class<?>> classes = new ArrayList<>();
        ClassParserUtils.getSuperClasses(classes, clazz);
        return classes.stream()
                .map(c -> Arrays.stream(c.getDeclaredFields()).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .filter(f -> isFilterFinal && !Modifier.isFinal(f.getModifiers()))
                .collect(Collectors.toList());
    }

}
