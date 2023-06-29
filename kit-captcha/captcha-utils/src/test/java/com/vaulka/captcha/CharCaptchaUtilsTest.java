package com.vaulka.captcha;

import com.vaulka.kit.captcha.utils.CharCaptchaUtils;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vaulka
 */
public class CharCaptchaUtilsTest {

    /**
     * 验证码数量
     */
    int codeNum = 5;

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
     * 字符验证码工具
     */
    CharCaptchaUtils captchaUtils = new CharCaptchaUtils(codeNum,
            codeNum * 25, imageHeight,
            drawCount, lineWidth);

    /**
     * 创建图像验证码 Stream 流
     */
    @Test
    public void createImageByStream() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("build/char.jpg");
        captchaUtils.createImageByStream(captchaUtils.createCode(), outputStream);
    }

    /**
     * 创建图像验证码 Base64 信息
     *
     * @throws IOException IO 异常
     */
    @Test
    public void createImageByBase64() throws IOException {
        System.out.println("char captcha image base64: " + captchaUtils.createImageByBase64(captchaUtils.createCode()));
    }

}
