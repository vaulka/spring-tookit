package com.vaulka.kit.web.response.processor.fail;


/**
 * 空指针异常处理器
 *
 * @author Vaulka
 */
public class NullPointerExceptionFailProcessor implements FailProcessor<NullPointerException> {

    @Override
    public Integer code() {
        return 506;
    }

    @Override
    public boolean isSprintStackTrace() {
        return true;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == NullPointerException.class;
    }

}
