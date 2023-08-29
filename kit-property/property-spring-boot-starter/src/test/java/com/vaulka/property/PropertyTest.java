package com.vaulka.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Vaulka
 */
@EnableAutoConfiguration
@SpringBootTest(classes = {InstancePropertyEnvironmentPostProcessor.class})
public class PropertyTest {

    @Value("${brokerId}")
    private String brokerId;

    @Value("${application.id}")
    private String applicationId;

    /**
     * 测试 brokerId Property
     */
    @Test
    public void property() {
        System.out.println("brokerId: " + brokerId);
        System.out.println("applicationId: " + applicationId);
        assert applicationId.equals(brokerId);
    }

}
