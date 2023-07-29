package com.vaulka.kit.web.response.processor.fail;

import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * 文件上传大小异常处理器
 *
 * @author Vaulka
 */
public class MaxUploadSizeExceededExceptionFailProcessor implements FailProcessor<MaxUploadSizeExceededException> {

    @Override
    public Integer code() {
        return 203;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == MaxUploadSizeExceededException.class;
    }

    /**
     * 默认错误信息
     */
    private static final String MESSAGE = "上传的文件体积超过限制，请缩小文件后重试";

    @Override
    public Object exec(MaxUploadSizeExceededException exception) {
        return exec(exception, MESSAGE);
    }

}
