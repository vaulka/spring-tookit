package com.vaulka.kit.captcha;

import com.vaulka.kit.captcha.autoconfigure.CaptchaAutoConfiguration;
import com.vaulka.kit.captcha.utils.CaptchaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vaulka
 */
@TestPropertySource("classpath:application-math.yml")
@SpringBootTest(classes = CaptchaAutoConfiguration.class)
public class MathCaptchaSpringTest {

    @Autowired
    private CaptchaUtils captchaUtils;

    /**
     * 创建图像验证码 Stream 流
     */
    @Test
    public void createImageByStream() throws IOException {
        FileOutputStream inputCharOutputStream = new FileOutputStream("build/math.jpg");
        captchaUtils.createImageByStream(captchaUtils.createCode(), inputCharOutputStream);
    }

    /**
     * 创建图像验证码 Base64 信息
     *
     * @throws IOException IO 异常
     */
    @Test
    public void createImageByBase64() throws IOException {
        System.out.println("math captcha image base64: " + captchaUtils.createImageByBase64(captchaUtils.createCode()));
    }


}
