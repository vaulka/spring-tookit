package com.vaulka.kit.web.response.processor.fail;


import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.MessageFormat;

/**
 * 接口不存在异常处理器
 *
 * @author Vaulka
 */
public class NoHandlerFoundExceptionFailProcessor implements FailProcessor<NoHandlerFoundException> {

    @Override
    public Integer code() {
        return 105;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == NoHandlerFoundException.class;
    }

    @Override
    public Object exec(NoHandlerFoundException exception) {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        String message = MessageFormat.format("{0} 接口不存在", request.getRequestURI());
        return this.buildResult(message);
    }

}
