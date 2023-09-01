package com.vaulka.kit.mqtt.callback;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT 默认回调
 *
 * @author Vaulka
 */
@Slf4j
public class DefaultMqttCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        log.error("MQTT connection lost [{}]", cause.getLocalizedMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("MQTT msg received success topic [{}] payload [{}] QoS [{}] retained [{}]",
                topic, new String(message.getPayload()), message.getQos(), message.isRetained());
    }

    @SneakyThrows
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        MqttMessage message = token.getMessage();
        log.info("MQTT msg sent success topic [{}] payload [{}] QoS [{}] retained [{}]",
                token.getTopics()[0], new String(message.getPayload()), message.getQos(), message.isRetained());
    }
}
