package com.vaulka.kit.web.response.processor.fail;

import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * request body 数据转换异常处理器
 *
 * @author Vaulka
 */
public class HttpMessageNotReadableExceptionFailProcessor implements FailProcessor<HttpMessageNotReadableException> {

    @Override
    public Integer code() {
        return 106;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == HttpMessageNotReadableException.class;
    }

    /**
     * 默认错误信息
     */
    private static final String MESSAGE = "request body 数据转换异常";

    @Override
    public Object exec(HttpMessageNotReadableException exception) {
        return this.buildResult(MESSAGE);
    }

}
