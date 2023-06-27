package com.vaulka.captcha;

import com.vaulka.kit.captcha.utils.CharCaptchaUtils;
import com.vaulka.kit.captcha.utils.MathCaptchaUtils;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vaulka
 */
public class CaptchaUtilsTest {

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
    CharCaptchaUtils charCaptchaUtils = new CharCaptchaUtils(codeNum,
            codeNum * 25, imageHeight,
            drawCount, lineWidth);

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
    MathCaptchaUtils mathCaptchaUtils = new MathCaptchaUtils(minCode, maxCode,
            (String.valueOf(maxCode).length() + 3) * 25,
            imageHeight, drawCount, lineWidth);

    /**
     * 创建图像验证码 Stream 流
     */
    @Test
    public void createImageByStream() throws IOException {
        FileOutputStream inputCharOutputStream = new FileOutputStream("build/char.jpg");
        FileOutputStream mathOutputStream = new FileOutputStream("build/math.jpg");
        charCaptchaUtils.createImageByStream(charCaptchaUtils.createCode(), inputCharOutputStream);
        mathCaptchaUtils.createImageByStream(mathCaptchaUtils.createCode(), mathOutputStream);
    }

    /**
     * 创建图像验证码 Base64 信息
     *
     * @throws IOException IO 异常
     */
    @Test
    public void createImageByBase64() throws IOException {
        System.out.println("input char image base64: " + charCaptchaUtils.createImageByBase64(charCaptchaUtils.createCode()));
        System.out.println("input math image base64: " + mathCaptchaUtils.createImageByBase64(mathCaptchaUtils.createCode()));
    }

}
