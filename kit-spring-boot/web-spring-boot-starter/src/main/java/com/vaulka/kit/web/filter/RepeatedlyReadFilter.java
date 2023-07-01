package com.vaulka.kit.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Request Body 数据多次读取
 *
 * @author Vaulka
 */
public class RepeatedlyReadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        ServletRequest servletRequest = request;
        if (contentType == null
                || contentType.contains(MediaType.APPLICATION_JSON_VALUE)
                || contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            servletRequest = new RepeatedlyReadRequestWrapper((HttpServletRequest) request);
        }
        chain.doFilter(servletRequest, response);
    }

}
