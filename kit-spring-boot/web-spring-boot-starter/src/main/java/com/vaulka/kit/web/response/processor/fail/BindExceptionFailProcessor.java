package com.vaulka.kit.web.response.processor.fail;

import com.pongsky.kit.validation.utils.ValidationUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * request body 数据校验异常 / param 数据校验异常 处理器
 *
 * @author Vaulka
 */
public class BindExceptionFailProcessor implements FailProcessor<BindException> {

    @Override
    public Integer code() {
        return 107;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == MethodArgumentNotValidException.class
                || exception.getClass() == BindException.class;
    }

    @Override
    public Object exec(BindException exception) {
        String message = ValidationUtils.getErrorMessage(exception.getBindingResult());
        return this.buildResult(message);
    }

}
