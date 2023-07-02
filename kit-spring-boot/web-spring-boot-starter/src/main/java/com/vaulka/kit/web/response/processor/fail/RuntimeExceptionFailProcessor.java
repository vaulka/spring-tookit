package com.vaulka.kit.web.response.processor.fail;

/**
 * 运行时异常处理器
 *
 * @author Vaulka
 */
public class RuntimeExceptionFailProcessor implements FailProcessor<RuntimeException> {

    @Override
    public Integer code() {
        return 500;
    }

    @Override
    public boolean isSprintStackTrace() {
        return true;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == RuntimeException.class;
    }

}
