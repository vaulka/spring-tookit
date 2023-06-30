package com.vaulka.kit.ip;

import com.vaulka.kit.ip.autoconfigure.IpAutoConfiguration;
import com.vaulka.kit.ip.config.IpSearcher;
import com.vaulka.kit.ip.model.IpInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Vaulka
 */
@SpringBootTest(classes = IpAutoConfiguration.class)
public class IpSpringTest {

    @Autowired
    private IpSearcher ipSearcher;

    /**
     * IP 解析
     */
    @Test
    public void ipParser() {
        IpInfo parser = ipSearcher.parser("211.20.157.208");
        System.out.println(parser.toString());
    }

}
