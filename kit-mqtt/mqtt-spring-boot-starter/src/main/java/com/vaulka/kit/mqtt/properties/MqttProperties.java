package com.vaulka.kit.mqtt.properties;

import com.vaulka.kit.mqtt.callback.DefaultMqttCallback;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


/**
 * MQTT 配置信息
 *
 * @author Vaulka
 **/
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "kit.mqtt")
public class MqttProperties {

    /**
     * MQTT 地址
     */
    @NotBlank
    private String url;

    /**
     * MQTT ClientId
     */
    @NotBlank
    private String clientId;

    /**
     * MQTT 用户名
     */
    private String username;

    /**
     * MQTT 密码
     */
    private String password;

    /**
     * MQTT 版本，默认 3.1.1，如果失败则回落到 3.1
     */
    private int version = MqttConnectOptions.MQTT_VERSION_DEFAULT;

    /**
     * 是否保持会话状态
     */
    private boolean cleanSession = MqttConnectOptions.CLEAN_SESSION_DEFAULT;

    /**
     * 是否自动重连
     */
    private boolean automaticReconnect = false;

    /**
     * 连接超时时间（秒），0 将禁用超时处理
     */
    private int connectionTimeout = MqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT;

    /**
     * 心跳检测时间（秒），0 将禁用心跳检测
     */
    private int keepAliveInterval = MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT;

    /**
     * 遗嘱消息
     */
    private WillMessage willMessage;

    /**
     * 回调类全限定类名
     */
    private String callback = DefaultMqttCallback.class.getName();

    /**
     * 遗嘱消息
     */
    @Getter
    @Setter
    @Validated
    public static class WillMessage {

        /**
         * 遗嘱消息 topic
         */
        @NotBlank
        private String topic;

        /**
         * 遗嘱消息 QoS
         */
        @Range(min = 0, max = 2)
        private int qos;

        /**
         * 遗嘱内容
         */
        @NotBlank
        private String message;

        /**
         * 是否保留标志
         */
        private boolean retained = true;

    }

}
