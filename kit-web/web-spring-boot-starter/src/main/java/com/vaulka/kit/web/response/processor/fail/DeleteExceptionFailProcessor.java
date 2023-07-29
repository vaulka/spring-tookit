package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.common.exception.DeleteException;

/**
 * 删除异常处理器
 *
 * @author Vaulka
 */
public class DeleteExceptionFailProcessor implements FailProcessor<DeleteException> {

    @Override
    public Integer code() {
        return 505;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == DeleteException.class;
    }

}
