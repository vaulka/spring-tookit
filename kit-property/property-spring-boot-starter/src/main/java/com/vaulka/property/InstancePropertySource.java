package com.vaulka.property;

import lombok.NonNull;
import org.springframework.core.env.PropertySource;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;


/**
 * 应用实例 ID 属性配置信息
 *
 * @author Vaulka
 */
public class InstancePropertySource extends PropertySource<Instance> {

    /**
     * brokerId 属性名称
     */
    private static final String BROKER_ID_PROPERTY_NAME = "brokerId";

    /**
     * 初始化属性资源
     */
    public InstancePropertySource() {
        super(BROKER_ID_PROPERTY_NAME, new Instance());
    }

    @Override
    public Object getProperty(@NonNull String name) {
        if (!BROKER_ID_PROPERTY_NAME.equals(name)) {
            return null;
        }
        try {
            return getSource().getId();
        } catch (UnknownHostException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}