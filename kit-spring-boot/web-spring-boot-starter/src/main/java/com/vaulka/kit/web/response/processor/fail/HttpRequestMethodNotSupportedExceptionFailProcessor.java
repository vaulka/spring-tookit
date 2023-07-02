package com.vaulka.kit.web.response.processor.fail;

import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.text.MessageFormat;

/**
 * 方法不存在异常处理器
 *
 * @author Vaulka
 */
public class HttpRequestMethodNotSupportedExceptionFailProcessor implements FailProcessor<HttpRequestMethodNotSupportedException> {

    @Override
    public Integer code() {
        return 104;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == HttpRequestMethodNotSupportedException.class;
    }

    @Override
    public Object exec(HttpRequestMethodNotSupportedException exception) {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        String message = MessageFormat.format("{0} 方法不存在", request.getRequestURI());
        return exec(exception, message);
    }

}
