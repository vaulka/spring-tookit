package com.vaulka.kit.common.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;

/**
 * httpServletRequest 工具类
 *
 * @author Vaulka
 */
public class HttpServletRequestUtils {

    /**
     * 获取 request body 数据
     *
     * @param request request
     * @return 获取 request body 数据
     */
    public static String getBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (Exception ignored) {
        }
        return StringUtils.isNotBlank(stringBuilder.toString()) ? stringBuilder.toString() : null;
    }

}
