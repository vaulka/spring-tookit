package com.vaulka.kit.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * Spring 工具
 *
 * @author Vaulka
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * Bean 工厂
     */
    private static ConfigurableListableBeanFactory beanFactory = null;

    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext = null;

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取 Bean 工厂
     *
     * @return 获取 Bean 工厂
     */
    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 获取应用上下文
     *
     * @return 获取应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return SpringUtils.getHttpServletRequest(false);
    }

    /**
     * 获取 HttpServletRequest
     *
     * @param canBeNull 是否可以为空
     * @return 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest(boolean canBeNull) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            if (canBeNull) {
                return null;
            }
            throw new RuntimeException("attributes is null");
        }
        return attributes.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return 获取 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return SpringUtils.getHttpServletResponse(false);
    }

    /**
     * 获取 HttpServletResponse
     *
     * @param canBeNull 是否可以为空
     * @return 获取 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse(boolean canBeNull) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            if (canBeNull) {
                return null;
            }
            throw new RuntimeException("attributes is null");
        }
        return attributes.getResponse();
    }

    /**
     * 请求ID
     */
    public static final String REQ_ID = "reqID";

    /**
     * 获取请求ID
     *
     * @return 获取请求ID
     */
    public static String getReqId() {
        HttpServletRequest request = SpringUtils.getHttpServletRequest(true);
        if (request == null) {
            return "";
        }
        return Optional.ofNullable(request.getAttribute(REQ_ID))
                .map(Object::toString).orElse("");
    }

}
