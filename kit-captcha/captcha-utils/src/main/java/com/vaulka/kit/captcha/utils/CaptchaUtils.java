package com.vaulka.kit.captcha.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码工具
 *
 * @author Vaulka
 */
public interface CaptchaUtils {

    /**
     * 最大颜色数
     */
    int MAX_COLOR_NUM = 255;

    /**
     * 取得给定范围随机颜色
     *
     * @param fc fc
     * @param bc bc
     * @return 颜色
     */
    default Color getRandColor(int fc, int bc) {
        final Random random = new Random();
        if (fc > MAX_COLOR_NUM) {
            fc = MAX_COLOR_NUM;
        }
        if (bc > MAX_COLOR_NUM) {
            bc = MAX_COLOR_NUM;
        }
        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 创建验证码
     *
     * @return 创建验证码
     */
    String createCode();

    /**
     * 绘画干扰线
     *
     * @param graphics    图像
     * @param imageWidth  图像宽度
     * @param imageHeight 图像高度
     * @param drawCount   干扰线数量
     * @param lineWidth   干扰线的长度 = 1.414 * LINE_WIDTH
     */
    default void drawInterferenceLines(Graphics2D graphics,
                                       int imageWidth, int imageHeight,
                                       int drawCount, int lineWidth) {
        // 设定背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        final Random random = new Random();
        // 随机产生干扰线，使图像中的认证码不易被其它程序探测到
        for (int i = 0; i < drawCount; i++) {
            graphics.setColor(getRandColor(150, 200));
            // 保证画在边框之内
            final int x = random.nextInt(imageWidth - lineWidth - 1) + 1;
            final int y = random.nextInt(imageHeight - lineWidth - 1) + 1;
            final int xl = random.nextInt(lineWidth);
            final int yl = random.nextInt(lineWidth);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
    }

    /**
     * 创建图像验证码
     *
     * @param code 验证码
     * @return 创建图像验证码
     */
    BufferedImage createImage(String code);

    /**
     * 图片格式类型
     */
    String FORMAT_NAME = "jpg";

    /**
     * 创建图像验证码，并写入 Stream 流
     *
     * @param code         验证码
     * @param outputStream outputStream
     * @throws IOException IO 异常
     */
    default void createImageByStream(String code, OutputStream outputStream) throws IOException {
        BufferedImage image = this.createImage(code);
        try (outputStream) {
            ImageIO.write(image, FORMAT_NAME, outputStream);
        }
    }

    /**
     * 创建图像验证码 Stream 流
     *
     * @param code 验证码
     * @return 创建验证码 Stream 流
     * @throws IOException IO 异常
     */
    default ByteArrayOutputStream createImageByStream(String code) throws IOException {
        BufferedImage image = this.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, FORMAT_NAME, outputStream);
        }
        return outputStream;
    }

    /**
     * 创建图像验证码 Base64 信息
     *
     * @param code 验证码
     * @return 创建验证码 Base64 信息
     * @throws IOException IO 异常
     */
    default String createImageByBase64(String code) throws IOException {
        ByteArrayOutputStream outputStream = this.createImageByStream(code);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

}
