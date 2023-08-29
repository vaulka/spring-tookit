package com.vaulka.kit.mqtt;

import com.vaulka.kit.mqtt.autoconfigure.MqttAutoConfiguration;
import com.vaulka.kit.mqtt.utils.MqttUtils;
import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author Vaulka
 */
@EnableAutoConfiguration
@SpringBootTest(classes = {MqttAutoConfiguration.class})
public class MqttTest {

    @Resource
    private MqttUtils mqttUtils;

    /**
     * 测试 mqtt 发送消息
     */
    @Test
    public void publish() throws MqttException {
        mqttUtils.publish("VAULKA", UUID.randomUUID().toString());
    }

}
