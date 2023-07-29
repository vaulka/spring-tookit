package com.vaulka.kit.web.response.processor.success;

import com.vaulka.kit.common.response.annotation.ResponseResult;
import com.vaulka.kit.web.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 默认【成功】全局响应处理器
 *
 * @author Vaulka
 */
public class DefaultSuccessProcessor implements SuccessProcessor {

    @Override
    public boolean isHitProcessor() {
        HttpServletRequest request = SpringUtils.getHttpServletRequest(true);
        return request != null && request.getAttribute(ResponseResult.class.getName()) != null;
    }

}
