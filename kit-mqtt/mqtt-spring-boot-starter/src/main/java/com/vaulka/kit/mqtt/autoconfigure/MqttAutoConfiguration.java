package com.vaulka.kit.mqtt.autoconfigure;

import com.vaulka.kit.mqtt.properties.MqttProperties;
import com.vaulka.kit.mqtt.utils.MqttUtils;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

/**
 * MQTT 自动配置
 *
 * @author Vaulka
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({MqttProperties.class})
public class MqttAutoConfiguration {

    private MqttClient client;

    /**
     * 注册 MQTT Client
     *
     * @param properties MQTT 配置
     * @return 注册 MQTT Client
     * @throws MqttException             MQTT 异常
     * @throws ClassNotFoundException    类不存在异常
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     * @throws NoSuchMethodException     无搜索方法异常
     * @throws InvocationTargetException 调用目标异常
     */
    @Bean
    public MqttClient mqttClient(MqttProperties properties) throws MqttException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // TODO ssl
        client = new MqttClient(properties.getUrl(), properties.getClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(properties.getUsername());
        if (properties.getPassword() != null) {
            options.setPassword(properties.getPassword().toCharArray());
        }
        options.setMqttVersion(properties.getVersion());
        options.setCleanSession(properties.isCleanSession());
        options.setAutomaticReconnect(properties.isAutomaticReconnect());
        options.setConnectionTimeout(properties.getConnectionTimeout());
        options.setKeepAliveInterval(properties.getKeepAliveInterval());
        if (properties.getWillMessage() != null) {
            MqttProperties.WillMessage willMessage = properties.getWillMessage();
            options.setWill(willMessage.getTopic(), willMessage.getMessage().getBytes(), willMessage.getQos(), willMessage.isRetained());
        }
        client.setCallback((MqttCallback) Class.forName(properties.getCallback()).getDeclaredConstructor().newInstance());
        client.connect(options);
        return client;
    }

    /**
     * 注册 MQTT 工具
     *
     * @param client MQTT Client
     * @return 注册 MQTT 工具
     */
    @Bean
    public MqttUtils mqttUtils(MqttClient client) {
        return new MqttUtils(client);
    }

    /**
     * 关闭 MQTT Client 连接
     *
     * @throws MqttException MQTT 异常
     */
    @PreDestroy
    public void destroy() throws MqttException {
        client.disconnect();
    }

}
