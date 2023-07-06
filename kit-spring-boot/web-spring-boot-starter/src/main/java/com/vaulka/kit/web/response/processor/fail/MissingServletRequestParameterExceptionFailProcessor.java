package com.vaulka.kit.web.response.processor.fail;

import org.springframework.web.bind.MissingServletRequestParameterException;

import java.text.MessageFormat;

/**
 * param 数据校验异常处理器
 *
 * @author Vaulka
 */
public class MissingServletRequestParameterExceptionFailProcessor implements FailProcessor<MissingServletRequestParameterException> {

    @Override
    public Integer code() {
        return 108;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == MissingServletRequestParameterException.class;
    }

    @Override
    public Object exec(MissingServletRequestParameterException exception) {
        String message = MessageFormat.format("参数校验失败，一共有 1 处错误，详情如下： {0} 不能为 null", exception.getParameterName());
        return this.buildResult(message);
    }

}
