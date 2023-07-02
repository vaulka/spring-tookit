package com.vaulka.kit.web.response.processor.fail;

/**
 * 非法参数异常处理器
 *
 * @author Vaulka
 */
public class IllegalArgumentExceptionFailProcessor implements FailProcessor<IllegalArgumentException> {

    @Override
    public Integer code() {
        return 103;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == IllegalArgumentException.class;
    }

}
