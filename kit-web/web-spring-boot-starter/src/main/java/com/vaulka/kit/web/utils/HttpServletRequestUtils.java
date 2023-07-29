package com.vaulka.kit.web.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * httpServletRequest 工具
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
        return stringBuilder.toString();
    }

}
