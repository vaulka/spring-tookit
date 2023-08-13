package com.vaulka.kit.watermark.utils.text.impl;

import com.vaulka.kit.watermark.model.text.impl.SingleTextRenderStyle;
import com.vaulka.kit.watermark.utils.text.ImageTextWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片单文本水印
 *
 * @author Vaulka
 */
public class ImageSingleTextWatermarkUtils implements ImageTextWatermarkUtils<SingleTextRenderStyle> {

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
        return this.mark(new SingleTextRenderStyle(), originFile, text);
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
    @SuppressWarnings("Duplicates")
    @Override
    public BufferedImage mark(SingleTextRenderStyle style, File originFile, String text) throws IOException {
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
        // 创建字体渲染上下文
        FontRenderContext frc = new FontRenderContext(null, true, true);
        // 计算字符串的宽度和高度
        Rectangle2D bounds = style.getFont().getStringBounds(text, frc);
        // 水印字符宽度
        int watermarkWidth = (int) bounds.getWidth();
        // 水印字符高度
        int watermarkHeight = (int) bounds.getHeight();
        // 设置水印的位置
        switch (style.getPosition()) {
            case TOP_LEFT -> graphics.drawString(text,
                    Math.max(0, (width - watermarkWidth) / 100 * style.getX()),
                    Math.max(0, height - watermarkHeight) / 100 * style.getY());
            case TOP_RIGHT -> graphics.drawString(text,
                    Math.max(0, (width - watermarkWidth) / 100 * (100 - style.getX())),
                    Math.max(0, height - watermarkHeight) / 100 * style.getY());
            case BOTTOM_LEFT -> graphics.drawString(text,
                    Math.max(0, (width - watermarkWidth) / 100 * style.getX()),
                    Math.max(0, height - watermarkHeight) / 100 * (100 - style.getY()));
            case BOTTOM_RIGHT -> graphics.drawString(text,
                    Math.max(0, (width - watermarkWidth) / 100 * (100 - style.getX())),
                    Math.max(0, height - watermarkHeight) / 100 * (100 - style.getY()));
            default -> graphics.drawString(text,
                    Math.max(0, (width - watermarkWidth) / 2),
                    Math.max(0, (height - watermarkHeight) / 2));
        }
        // 释放画图工具
        graphics.dispose();
        return bufImage;
    }

}