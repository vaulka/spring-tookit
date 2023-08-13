package com.vaulka.kit.watermark.utils.image.impl;

import com.vaulka.kit.watermark.model.image.impl.SingleImageRenderStyle;
import com.vaulka.kit.watermark.utils.image.ImageImageWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片单图片水印
 *
 * @author Vaulka
 */
public class ImageSingleImageWatermarkUtils implements ImageImageWatermarkUtils<SingleImageRenderStyle> {

    /**
     * 图片加图片水印
     *
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @Override
    public BufferedImage mark(File originFile, File watermarkFile) throws IOException {
        return this.mark(new SingleImageRenderStyle(), originFile, watermarkFile);
    }

    /**
     * 图片加图片水印
     *
     * @param style         渲染样式
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @SuppressWarnings("Duplicates")
    @Override
    public BufferedImage mark(SingleImageRenderStyle style, File originFile, File watermarkFile) throws IOException {
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
        // 设置水印透明度
        graphics.setComposite(style.getComposite());
        BufferedImage watermarkImage = ImageIO.read(watermarkFile);
        // 水印图片宽度
        int watermarkWidth = watermarkImage.getWidth();
        // 水印图片高度
        int watermarkHeight = watermarkImage.getHeight();
        // 设置水印的位置
        switch (style.getPosition()) {
            case TOP_LEFT -> graphics.drawImage(watermarkImage,
                    Math.max(0, (width - watermarkWidth) / 100 * style.getX()),
                    Math.max(0, height - watermarkHeight) / 100 * style.getY(),
                    watermarkWidth, watermarkHeight, null);
            case TOP_RIGHT -> graphics.drawImage(watermarkImage,
                    Math.max(0, (width - watermarkWidth) / 100 * (100 - style.getX())),
                    Math.max(0, height - watermarkHeight) / 100 * style.getY(),
                    watermarkWidth, watermarkHeight, null);
            case BOTTOM_LEFT -> graphics.drawImage(watermarkImage,
                    Math.max(0, (width - watermarkWidth) / 100 * style.getX()),
                    Math.max(0, height - watermarkHeight) / 100 * (100 - style.getY()),
                    watermarkWidth, watermarkHeight, null);
            case BOTTOM_RIGHT -> graphics.drawImage(watermarkImage,
                    Math.max(0, (width - watermarkWidth) / 100 * (100 - style.getX())),
                    Math.max(0, height - watermarkHeight) / 100 * (100 - style.getY()),
                    watermarkWidth, watermarkHeight, null);
            default -> graphics.drawImage(watermarkImage,
                    Math.max(0, (width - watermarkWidth) / 2),
                    Math.max(0, (height - watermarkHeight) / 2),
                    watermarkWidth, watermarkHeight, null);
        }
        // 释放画图工具
        graphics.dispose();
        return bufImage;
    }

}