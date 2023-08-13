package com.vaulka.kit.watermark.utils.image;

import com.vaulka.kit.watermark.model.image.ImageRenderStyle;
import com.vaulka.kit.watermark.utils.ImageWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 图片图片水印
 *
 * @author Vaulka
 */
public interface ImageImageWatermarkUtils<S extends ImageRenderStyle> extends ImageWatermarkUtils<S, File> {

    /**
     * 图片加图片水印
     *
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @Override
    BufferedImage mark(File originFile, File watermarkFile) throws IOException;

    /**
     * 图片加图片水印
     *
     * @param style         渲染样式
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @Override
    BufferedImage mark(S style, File originFile, File watermarkFile) throws IOException;

    /**
     * 图片加图片水印
     *
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @param outputStream  outputStream
     * @throws IOException IO 异常
     */
    @Override
    default void markByStream(File originFile, File watermarkFile, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(originFile, watermarkFile);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加图片水印
     *
     * @param style         渲染样式
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @param outputStream  outputStream
     * @throws IOException IO 异常
     */
    @Override
    default void markByStream(S style, File originFile, File watermarkFile, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(style, originFile, watermarkFile);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加图片水印
     *
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @throws IOException IO 异常
     */
    @Override
    default ByteArrayOutputStream markByStream(File originFile, File watermarkFile) throws IOException {
        BufferedImage image = this.mark(originFile, watermarkFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加图片水印
     *
     * @param style         渲染样式
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @throws IOException IO 异常
     */
    @Override
    default ByteArrayOutputStream markByStream(S style, File originFile, File watermarkFile) throws IOException {
        BufferedImage image = this.mark(style, originFile, watermarkFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加图片水印
     *
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @throws IOException IO 异常
     */
    @Override
    default String markByBase64(File originFile, File watermarkFile) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(originFile, watermarkFile);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 图片加图片水印
     *
     * @param style         渲染样式
     * @param originFile    源文件
     * @param watermarkFile 水印图片
     * @throws IOException IO 异常
     */
    @Override
    default String markByBase64(S style, File originFile, File watermarkFile) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(style, originFile, watermarkFile);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

}