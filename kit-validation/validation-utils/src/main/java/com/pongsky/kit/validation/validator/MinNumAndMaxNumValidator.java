package com.pongsky.kit.validation.validator;


import com.pongsky.kit.validation.annotation.validator.MinNumAndMaxNum;
import com.pongsky.kit.validation.enums.FieldType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * 校验最小数值以及最大数值校验器
 *
 * @author Vaulka
 **/
public class MinNumAndMaxNumValidator extends ComparableValidator implements ConstraintValidator<MinNumAndMaxNum, Object> {

    @Override
    public void initialize(MinNumAndMaxNum annotation) {
        this.setStartFieldName(annotation.minNumFieldName());
        this.setStartName(annotation.minNumName());
        this.setEndFieldName(annotation.maxNumFieldName());
        this.setEndName(annotation.maxNumName());
        this.setFieldTypes(Arrays.asList(
                FieldType.FLOAT, FieldType.DOUBLE,
                FieldType.BYTE, FieldType.SHORT,
                FieldType.INTEGER, FieldType.LONG,
                FieldType.BIG_INTEGER, FieldType.BIG_DECIMAL
        ));
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return super.validation(obj, context);
    }

}
