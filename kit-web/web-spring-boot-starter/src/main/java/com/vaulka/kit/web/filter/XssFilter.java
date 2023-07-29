package com.vaulka.kit.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * XSS 防御
 *
 * @author Vaulka
 */
public class XssFilter implements Filter {

    /**
     * 排除列表
     */
    private final List<Pattern> excludePatterns;


    public XssFilter(List<String> excludes) {
        this.excludePatterns = excludes.stream()
                .map(e -> Pattern.compile("^" + e))
                .collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        boolean noIsHit = excludePatterns.stream()
                .noneMatch(ep -> ep.matcher(uri).find());
        ServletRequest servletRequest = request;
        // 如果在放行列表，则不做拦截处理
        if (noIsHit) {
            servletRequest = new XssRequestWrapper((HttpServletRequest) request);
        }
        chain.doFilter(servletRequest, response);
    }

}
