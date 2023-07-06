package com.vaulka.kit.validation.validator;

import com.vaulka.kit.validation.enums.FieldType;
import com.vaulka.kit.validation.utils.ReflectUtils;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import lombok.Setter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Comparable 校验器
 *
 * @author Vaulka
 */
@Setter
public class ComparableValidator {

    /**
     * 起始字段名称
     */
    private String startFieldName;

    /**
     * 起始名称
     */
    private String startName;

    /**
     * 结束字段名称
     */
    private String endFieldName;

    /**
     * 结束名称
     */
    private String endName;

    /**
     * 字段类型列表
     */
    private List<FieldType> fieldTypes;

    protected boolean validation(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            this.setErrorMessage(context, "校验对象不能为空");
            return false;
        }
        Field startField, endField;
        Object start, end;
        try {
            startField = this.getField(obj, startFieldName);
            start = ReflectUtils.getValue(obj, startField);
            endField = this.getField(obj, endFieldName);
            end = ReflectUtils.getValue(obj, endField);
        } catch (ValidationException e) {
            this.setErrorMessage(context, e.getLocalizedMessage());
            return false;
        }
        if (start == null || end == null) {
            return true;
        }
        FieldType minFieldType = FieldType.parserType(startField);
        FieldType maxFieldType = FieldType.parserType(endField);
        if (minFieldType != maxFieldType) {
            this.setErrorMessage(context, MessageFormat.format("{0}与{1}字段数据类型不一致", startName, endName));
            return false;
        }
        if (!fieldTypes.contains(minFieldType)) {
            this.setErrorMessage(context, MessageFormat.format("{0}字段数据类型不支持校验", startName));
            return false;
        }
        int compareTo = switch (minFieldType) {
            case FLOAT -> this.compareTo((Float) start, (Float) end);
            case DOUBLE -> this.compareTo((Double) start, (Double) end);
            case BYTE -> this.compareTo((Byte) start, (Byte) end);
            case SHORT -> this.compareTo((Short) start, (Short) end);
            case INTEGER -> this.compareTo((Integer) start, (Integer) end);
            case LONG -> this.compareTo((Long) start, (Long) end);
            case BIG_INTEGER -> this.compareTo((BigInteger) start, (BigInteger) end);
            case BIG_DECIMAL -> this.compareTo((BigDecimal) start, (BigDecimal) end);
            case DATE -> this.compareTo((Date) start, (Date) end);
            case LOCAL_DATE -> this.compareTo((LocalDate) start, (LocalDate) end);
            case LOCAL_TIME -> this.compareTo((LocalTime) start, (LocalTime) end);
            case LOCAL_DATE_TIME -> this.compareTo((LocalDateTime) start, (LocalDateTime) end);
            default -> -1;
        };
        if (compareTo > 0) {
            this.setErrorMessage(context, MessageFormat.format("{0}不能大于{1}", startName, endName));
            return false;
        }
        return true;
    }

    /**
     * 获取字段
     *
     * @param obj       对象
     * @param fieldName 字段名称
     * @return 获取字段
     * @throws ValidationException 校验异常
     */
    private Field getField(Object obj, String fieldName) {
        Field field = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
        if (field == null) {
            throw new ValidationException(MessageFormat.format("{0} 字段不存在", fieldName));
        }
        return field;
    }

    /**
     * 设置错误信息
     *
     * @param context context
     * @param message 错误信息
     */
    private void setErrorMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

    /**
     * 比较大小
     *
     * @param c1  c1
     * @param c2  c2
     * @param <T> 泛型
     * @return 比较结果
     */
    private <T extends Comparable<T>> int compareTo(T c1, T c2) {
        return c1.compareTo(c2);
    }

}
