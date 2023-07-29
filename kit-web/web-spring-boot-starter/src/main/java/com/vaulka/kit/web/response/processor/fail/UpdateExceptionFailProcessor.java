package com.vaulka.kit.web.response.processor.fail;

import com.vaulka.kit.common.exception.UpdateException;

/**
 * 修改异常处理器
 *
 * @author Vaulka
 */
public class UpdateExceptionFailProcessor implements FailProcessor<UpdateException> {

    @Override
    public Integer code() {
        return 504;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == UpdateException.class;
    }

}
