package com.vaulka.kit.web.response.processor.fail;

import com.vaulka.kit.common.exception.InsertException;

/**
 * 保存异常处理器
 *
 * @author Vaulka
 */
public class InsertExceptionFailProcessor implements FailProcessor<InsertException> {

    @Override
    public Integer code() {
        return 503;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == InsertException.class;
    }

}
