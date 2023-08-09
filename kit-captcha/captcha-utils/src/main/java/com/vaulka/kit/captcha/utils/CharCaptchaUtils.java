package com.vaulka.kit.captcha.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 字符验证码工具
 *
 * @author Vaulka
 */
public class CharCaptchaUtils implements CaptchaUtils {

    /**
     * 验证码数量
     */
    private final int codeNum;

    /**
     * 图像宽度
     */
    private final int imageWidth;

    /**
     * 图像高度
     */
    private final int imageHeight;

    /**
     * 干扰线数量
     */
    private final int drawCount;

    /**
     * 干扰线的长度 = 1.414 * LINE_WIDTH
     */
    private final int lineWidth;

    /**
     * 初始化字符验证码工具
     *
     * @param codeNum     验证码数量
     * @param imageWidth  图像宽度
     * @param imageHeight 图像高度
     * @param drawCount   干扰线数量
     * @param lineWidth   干扰线的长度
     */
    public CharCaptchaUtils(int codeNum,
                            int imageWidth, int imageHeight,
                            int drawCount, int lineWidth) {
        this.codeNum = codeNum;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.drawCount = drawCount;
        this.lineWidth = lineWidth;
    }

    @Override
    public BufferedImage createImage(String code) {
        // 在内存中创建图像
        final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 绘画干扰线
        this.drawInterferenceLines(graphics, imageWidth, imageHeight, drawCount, lineWidth);
        // 取随机产生的认证码
        for (int i = 0; i < code.length(); i++) {
            // 将认证码显示到图像中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            // 设置字体颜色
            graphics.setColor(Color.BLACK);
            // 设置字体样式
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            // 设置字符，字符间距，上边距
            graphics.drawString(String.valueOf(code.charAt(i)), (23 * i) + 8, 26);
        }
        // 图像生效
        graphics.dispose();
        return image;
    }

    @Override
    public String createCode() {
        return RandomStringUtils.randomAlphanumeric(codeNum);
    }

}
