package com.vaulka.captcha;

import com.vaulka.kit.captcha.utils.MathCaptchaUtils;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vaulka
 */
public class MathCaptchaUtilsTest {

    /**
     * 图像高度
     */
    int imageHeight = 35;

    /**
     * 干扰线数量
     */
    int drawCount = 200;

    /**
     * 干扰线的长度 = 1.414 * LINE_WIDTH
     */
    int lineWidth = 2;

    /**
     * 验证码最小值
     */
    int minCode = 10;

    /**
     * 验证码最大值
     */
    int maxCode = 99;

    /**
     * 算数验证码工具
     */
    MathCaptchaUtils captchaUtils = new MathCaptchaUtils(minCode, maxCode,
            (String.valueOf(maxCode).length() + 3) * 25,
            imageHeight, drawCount, lineWidth);

    /**
     * 创建图像验证码 Stream 流
     */
    @Test
    public void createImageByStream() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("build/math.jpg");
        captchaUtils.createImageByStream(captchaUtils.createCode(), outputStream);
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
