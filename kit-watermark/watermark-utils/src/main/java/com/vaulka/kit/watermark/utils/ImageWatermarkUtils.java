package com.vaulka.kit.watermark.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 图片水印
 *
 * @author Vaulka
 */
public interface ImageWatermarkUtils<S, R> {

    /**
     * 图片加水印
     *
     * @param originFile 源文件
     * @param obj        水印内容
     * @return 水印图片
     * @throws IOException IO 异常
     */
    BufferedImage mark(File originFile, R obj) throws IOException;

    /**
     * 图片加水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param obj        水印内容
     * @return 水印图片
     * @throws IOException IO 异常
     */
    BufferedImage mark(S style, File originFile, R obj) throws IOException;

    /**
     * 图片加水印
     *
     * @param originFile   源文件
     * @param obj          水印内容
     * @param outputStream outputStream
     * @throws IOException IO 异常
     */
    default void markByStream(File originFile, R obj, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(originFile, obj);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加水印
     *
     * @param style        渲染样式
     * @param originFile   源文件
     * @param obj          水印内容
     * @param outputStream outputStream
     * @throws IOException IO 异常
     */
    default void markByStream(S style, File originFile, R obj, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(style, originFile, obj);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加水印
     *
     * @param originFile 源文件
     * @param obj        水印内容
     * @return 字节数组 outputStream
     * @throws IOException IO 异常
     */
    default ByteArrayOutputStream markByStream(File originFile, R obj) throws IOException {
        BufferedImage image = this.mark(originFile, obj);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param obj        水印内容
     * @return 字节数组 outputStream
     * @throws IOException IO 异常
     */
    default ByteArrayOutputStream markByStream(S style, File originFile, R obj) throws IOException {
        BufferedImage image = this.mark(style, originFile, obj);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加水印
     *
     * @param originFile 源文件
     * @param obj        水印内容
     * @return base64 字符串
     * @throws IOException IO 异常
     */
    default String markByBase64(File originFile, R obj) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(originFile, obj);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 图片加水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param obj        水印内容
     * @return base64 字符串
     * @throws IOException IO 异常
     */
    default String markByBase64(S style, File originFile, R obj) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(style, originFile, obj);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 获取文件格式名称
     *
     * @param originFile 源文件
     * @return 获取文件格式名称
     */
    default String getFormatName(File originFile) {
        return originFile.getName().substring(originFile.getName().lastIndexOf(".") + 1);
    }

}