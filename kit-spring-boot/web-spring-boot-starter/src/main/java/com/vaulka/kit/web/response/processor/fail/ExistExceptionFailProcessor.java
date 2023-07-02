package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.common.exception.ExistException;

/**
 * 存在异常处理器
 *
 * @author Vaulka
 */
public class ExistExceptionFailProcessor implements FailProcessor<ExistException> {

    @Override
    public Integer code() {
        return 201;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == ExistException.class;
    }

}
