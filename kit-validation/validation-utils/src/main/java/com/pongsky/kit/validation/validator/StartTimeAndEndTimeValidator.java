package com.pongsky.kit.validation.validator;


import com.pongsky.kit.validation.annotation.validator.StartTimeAndEndTime;
import com.pongsky.kit.validation.enums.FieldType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * 校验开始时间和结束时间 校验器
 *
 * @author Vaulka
 **/
public class StartTimeAndEndTimeValidator extends ComparableValidator implements ConstraintValidator<StartTimeAndEndTime, Object> {

    @Override
    public void initialize(StartTimeAndEndTime annotation) {
        this.setStartFieldName(annotation.startTimeFieldName());
        this.setStartName(annotation.startTimeName());
        this.setEndFieldName(annotation.endTimeFieldName());
        this.setEndName(annotation.endTimeName());
        this.setFieldTypes(Arrays.asList(
                FieldType.LONG, FieldType.DATE,
                FieldType.LOCAL_DATE, FieldType.LOCAL_TIME, FieldType.LOCAL_DATE_TIME
        ));
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return this.validation(obj, context);
    }

}
