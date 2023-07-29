package com.vaulka.kit.web.response.processor.fail;

import org.springframework.web.multipart.MultipartException;


/**
 * 空文件上传异常处理器
 *
 * @author Vaulka
 */
public class MultipartExceptionFailProcessor implements FailProcessor<MultipartException> {

    @Override
    public Integer code() {
        return 102;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == MultipartException.class;
    }

    /**
     * 默认错误信息
     */
    private static final String MESSAGE = "请选择文件进行上传";

    @Override
    public Object exec(MultipartException exception) {
        return exec(exception, MESSAGE);
    }

}
