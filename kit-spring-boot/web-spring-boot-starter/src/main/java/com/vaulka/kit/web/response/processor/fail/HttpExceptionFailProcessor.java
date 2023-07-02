package com.vaulka.kit.web.response.processor.fail;

import com.vaulka.kit.common.exception.HttpException;

/**
 * HTTP 请求异常处理器
 *
 * @author Vaulka
 */
public class HttpExceptionFailProcessor implements FailProcessor<HttpException> {

    @Override
    public Integer code() {
        return 204;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == HttpException.class;
    }

}
