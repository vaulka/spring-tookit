package com.vaulka.kit.web.response.processor.fail;

/**
 * 默认【失败】全局响应处理器
 *
 * @author Vaulka
 */
public class DefaultFailProcessor implements FailProcessor<Throwable> {

    @Override
    public Integer code() {
        return 1000;
    }

    @Override
    public boolean isSprintStackTrace() {
        return true;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return true;
    }

    @Override
    public int order() {
        return -1;
    }

}
