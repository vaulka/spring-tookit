package com.vaulka.kit.web.response.processor.fail;

import org.springframework.beans.TypeMismatchException;
import org.springframework.util.ClassUtils;

import java.text.MessageFormat;

/**
 * 数据类型转换异常处理器
 *
 * @author Vaulka
 */
public class TypeMismatchExceptionFailProcessor implements FailProcessor<TypeMismatchException> {

    @Override
    public Integer code() {
        return 110;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == TypeMismatchException.class;
    }

    @Override
    public Object exec(TypeMismatchException exception) {
        String message = MessageFormat.format("无法将 \"{0}\" 值从 \"{1}\" 类型 转换为 \"{2}\" 类型",
                exception.getValue(), ClassUtils.getDescriptiveType(exception.getValue()),
                ClassUtils.getDescriptiveType(exception.getRequiredType()));
        return this.buildResult(message);
    }

}
