package com.vaulka.kit.watermark.utils.impl;

import com.vaulka.kit.watermark.model.FullScreenTextRenderStyle;
import com.vaulka.kit.watermark.utils.PhotoTextWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片全屏文本水印
 *
 * @author Vaulka
 */
public class PhotoFullScreenTextWatermarkUtils implements PhotoTextWatermarkUtils<FullScreenTextRenderStyle> {

    /**
     * 图片加文本水印
     *
     * @param originFile 源文件
     * @param text       水印内容
     * @return 图片字节数组
     * @throws IOException IO 异常
     */
    @Override
    public BufferedImage mark(File originFile, String text) throws IOException {
        return this.mark(new FullScreenTextRenderStyle(), originFile, text);
    }

    /**
     * 图片加文本水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param text       水印内容
     * @return 图片字节数组
     * @throws IOException IO 异常
     */
    @Override
    public BufferedImage mark(FullScreenTextRenderStyle style, File originFile, String text) throws IOException {
        BufferedImage image = ImageIO.read(originFile);
        // 图片宽、高
        int width = image.getWidth();
        int height = image.getHeight();
        // 图片缓存
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 创建绘图工具
        Graphics2D graphics = bufImage.createGraphics();
        // 画入原始图像
        graphics.drawImage(image, 0, 0, width, height, null);
        // 设置水印字体
        graphics.setFont(style.getFont());
        // 设置水印颜色
        graphics.setColor(style.getColor());
        // 设置水印透明度
        graphics.setComposite(style.getComposite());
        // 消除 java.awt.Font 字体的锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置倾斜角度
        graphics.rotate(Math.toRadians(-35), (double) bufImage.getWidth() / 2, (double) bufImage.getHeight() / 2);
        int x = -width / 2;
        int y;
        // 字体长度
        int markWidth = style.getFont().getSize() * this.getTextLength(text);
        // 字体高度
        int markHeight = style.getFont().getSize();
        // 循环添加水印
        while (x < width * 1.5) {
            y = -height / 2;
            while (y < height * 1.5) {
                graphics.drawString(text, x, y);
                y += markHeight + style.getX();
            }
            x += markWidth + style.getY();
        }
        // 释放画图工具
        graphics.dispose();
        return bufImage;
    }

}