package com.vaulka.kit.mqtt.utils;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;

/**
 * MQTT 工具
 *
 * @author Vaulka
 */
@RequiredArgsConstructor
public class MqttUtils {

    private final MqttClient client;

    /**
     * 发布信息
     *
     * @param topic   发布 topic
     * @param message 消息内容
     */
    public void publish(String topic, String message) throws MqttException {
        this.publish(topic, message, 0, false);
    }

    /**
     * 发布信息
     *
     * @param topic    发布 topic
     * @param message  消息内容
     * @param qos      发布 QoS
     * @param retained 是否保留标志
     */
    public void publish(String topic, String message, int qos, boolean retained) throws MqttException {
        client.publish(topic, message.getBytes(StandardCharsets.UTF_8), qos, retained);
    }

}
