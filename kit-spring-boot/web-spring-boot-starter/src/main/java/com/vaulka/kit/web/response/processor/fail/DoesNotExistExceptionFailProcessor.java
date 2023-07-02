package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.common.exception.DoesNotExistException;

/**
 * 不存在异常处理器
 *
 * @author Vaulka
 */
public class DoesNotExistExceptionFailProcessor implements FailProcessor<DoesNotExistException> {

    @Override
    public Integer code() {
        return 202;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == DoesNotExistException.class;
    }

}
