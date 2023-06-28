package com.pongsky.kit.validation.utils;

import com.pongsky.kit.validation.annotation.Property;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校验工具
 *
 * @author Vaulka
 **/
public class ValidationUtils {

    /**
     * 参数校验错误总数信息
     */
    private static final String ERROR_COUNT_MSG = "参数校验失败，一共有 {0} 处错误，详情如下：";

    /**
     * 获取校验异常信息
     *
     * @param bindingResult bindingResult
     * @return 获取校验异常信息
     */
    public static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(ERROR_COUNT_MSG, bindingResult.getErrorCount()));
        if (bindingResult.getTarget() == null) {
            bindingResult.getGlobalErrors().forEach(error ->
                    ValidationUtils.buildErrorMessage(sb, error.getDefaultMessage()));
            bindingResult.getFieldErrors().forEach(error ->
                    ValidationUtils.buildErrorMessage(sb, error.getField(), error.getDefaultMessage()));
            return sb.toString();
        }
        for (ObjectError error : bindingResult.getGlobalErrors()) {
            ValidationUtils.buildErrorMessage(sb, error.getDefaultMessage());
        }
        for (FieldError error : bindingResult.getFieldErrors()) {
            String fieldProperty = ValidationUtils.getFieldProperty(bindingResult.getTarget().getClass(), error.getField());
            ValidationUtils.buildErrorMessage(sb, fieldProperty, error.getDefaultMessage());
        }
        return sb.toString();
    }

    /**
     * 获取字段属性
     *
     * @param clazz clazz
     * @param field 字段
     * @return 获取字段属性
     */
    private static String getFieldProperty(Class<?> clazz, String field) {
        List<Field> fields = new ArrayList<>();
        Map<Field, String> indexMap = new HashMap<>(16);
        ValidationUtils.getField(fields, indexMap, clazz, field);
        return fields.stream()
                .map(f -> {
                    Property property = f.getAnnotation(Property.class);
                    String fieldName = property != null ? property.value() : f.getName();
                    String index = indexMap.get(f);
                    if (index != null) {
                        fieldName += index;
                    }
                    return fieldName;
                }).collect(Collectors.joining(POINT));
    }

    /**
     * 构建错误信息详情
     *
     * @param sb      sb
     * @param message 错误信息
     */
    private static void buildErrorMessage(StringBuilder sb, String message) {
        sb.append(" ").append(message).append(";");
    }

    /**
     * 构建错误信息详情
     *
     * @param sb        sb
     * @param fieldName 字段名称
     * @param message   错误信息
     */
    private static void buildErrorMessage(StringBuilder sb, String fieldName, String message) {
        sb.append(" ").append(fieldName).append(message).append(";");
    }

    /**
     * 点符号
     */
    private static final String POINT = ".";

    /**
     * 左大括号
     */
    private static final String LEFT_BRACE = "[";

    /**
     * 右大括号
     */
    private static final String RIGHT_BRACE = "]";

    /**
     * 获取多层级属性
     *
     * @param fields    字段列表
     * @param indexMap  字段下标列表
     * @param clazz     待解析的类
     * @param fieldName 字段名称
     */
    private static void getField(List<Field> fields,
                                 Map<Field, String> indexMap,
                                 Class<?> clazz,
                                 String fieldName) {
        List<Field> rootFields = FieldParserUtils.getSuperFields(clazz, true);
        String baseFieldName = fieldName.contains(POINT)
                ? fieldName.substring(0, fieldName.indexOf(POINT))
                : "",
                subFieldName = fieldName.contains(POINT)
                        ? fieldName.substring(baseFieldName.length() + 1)
                        : "";
        if (fieldName.contains(POINT)) {
            if (baseFieldName.contains(LEFT_BRACE) && baseFieldName.contains(RIGHT_BRACE)) {
                // 示例：user.name.tags[x]
                // 示例：user.name.customer[x].name
                ValidationUtils.getFieldByCollections(rootFields, fields, indexMap, baseFieldName, subFieldName);
            } else {
                // 示例：user.name
                rootFields.stream()
                        .filter(f -> f.getName().equals(baseFieldName))
                        .findFirst().ifPresent(f -> {
                            fields.add(f);
                            ValidationUtils.getField(fields, indexMap, f.getType(), subFieldName);
                        });
            }
        } else if (fieldName.contains(LEFT_BRACE) && fieldName.contains(RIGHT_BRACE)) {
            // 示例：tags[x]
            ValidationUtils.getFieldByCollections(rootFields, fields, indexMap, fieldName, subFieldName);
        } else {
            // 示例：name
            rootFields.stream()
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst()
                    .ifPresent(fields::add);
        }
    }

    /**
     * 获取集合的字段信息
     *
     * @param rootFields    解析类的所有字段列表
     * @param fields        当前字段解析成功的字段列表
     * @param indexMap      当前字段的下标信息 MAP
     * @param baseFieldName 基类的字段名称
     * @param subFieldName  子类的字段名称
     */
    private static void getFieldByCollections(List<Field> rootFields,
                                              List<Field> fields,
                                              Map<Field, String> indexMap,
                                              String baseFieldName,
                                              String subFieldName) {
        // 示例：user.name.tags[x]
        // 示例：user.name.customer[x].name
        String arrayFieldName = baseFieldName.substring(0, baseFieldName.indexOf(LEFT_BRACE));
        String index = baseFieldName.substring(baseFieldName.indexOf(LEFT_BRACE));
        rootFields.stream()
                .filter(f -> f.getName().equals(arrayFieldName))
                .findFirst()
                .ifPresent(f -> {
                    fields.add(f);
                    indexMap.put(f, index);
                    if (subFieldName.length() > 0) {
                        Class<?> elementType = CollectionParserUtils.getElementType(f);
                        ValidationUtils.getField(fields, indexMap, elementType, subFieldName);
                    }
                });
    }

    /**
     * 对数据进行校验
     *
     * @param data 数据
     * @param <T>  范型
     * @return 错误信息
     */
    public static <T> String validation(T data) {
        return validation(data, Default.class);
    }

    /**
     * 校验器
     */
    private static final Validator VALIDATOR = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator(Collections.emptySet(),
                    Locale.CHINA, new DefaultLocaleResolver(), false))
            .buildValidatorFactory()
            .getValidator();

    /**
     * 对数据进行校验
     *
     * @param data  数据
     * @param group 组别
     * @param <T>   范型
     * @return 错误信息
     */
    public static <T> String validation(T data, Class<?> group) {
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(data, group);
        if (validate == null || validate.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(ERROR_COUNT_MSG, validate.size()));
        for (ConstraintViolation<T> cv : validate) {
            String fieldProperty = ValidationUtils.getFieldProperty(cv.getRootBeanClass(), cv.getPropertyPath().toString());
            ValidationUtils.buildErrorMessage(sb, fieldProperty, cv.getMessage());
        }
        return sb.toString();
    }

}
