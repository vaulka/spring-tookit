package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.common.exception.FrequencyException;

/**
 * 频率异常处理器
 *
 * @author Vaulka
 */
public class FrequencyExceptionFailProcessor implements FailProcessor<FrequencyException> {

    @Override
    public Integer code() {
        return 301;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == FrequencyException.class;
    }

}
