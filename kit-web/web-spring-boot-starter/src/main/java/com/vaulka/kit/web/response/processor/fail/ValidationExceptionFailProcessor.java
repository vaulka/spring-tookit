package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.common.exception.ValidationException;
import jakarta.validation.ConstraintViolationException;

/**
 * 校验异常处理器
 *
 * @author Vaulka
 */
public class ValidationExceptionFailProcessor implements FailProcessor<ValidationException> {

    @Override
    public Integer code() {
        return 101;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == ValidationException.class
                || exception.getClass() == ConstraintViolationException.class;
    }

}
