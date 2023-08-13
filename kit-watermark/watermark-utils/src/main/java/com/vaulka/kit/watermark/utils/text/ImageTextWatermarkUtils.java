package com.vaulka.kit.watermark.utils.text;

import com.vaulka.kit.watermark.model.text.TextRenderStyle;
import com.vaulka.kit.watermark.utils.ImageWatermarkUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 图片文本水印
 *
 * @author Vaulka
 */
public interface ImageTextWatermarkUtils<S extends TextRenderStyle> extends ImageWatermarkUtils<S, String> {

    /**
     * 图片加文本水印
     *
     * @param originFile 源文件
     * @param text       水印内容
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @Override
    BufferedImage mark(File originFile, String text) throws IOException;

    /**
     * 图片加文本水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param text       水印内容
     * @return 水印图片
     * @throws IOException IO 异常
     */
    @Override
    BufferedImage mark(S style, File originFile, String text) throws IOException;

    /**
     * 图片加文本水印
     *
     * @param originFile   源文件
     * @param text         水印内容
     * @param outputStream outputStream
     * @throws IOException IO 异常
     */
    @Override
    default void markByStream(File originFile, String text, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(originFile, text);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加文本水印
     *
     * @param style        渲染样式
     * @param originFile   源文件
     * @param text         水印内容
     * @param outputStream outputStream
     * @throws IOException IO 异常
     */
    @Override
    default void markByStream(S style, File originFile, String text, OutputStream outputStream) throws IOException {
        BufferedImage image = this.mark(style, originFile, text);
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
    }

    /**
     * 图片加文本水印
     *
     * @param originFile 源文件
     * @param text       水印内容
     * @throws IOException IO 异常
     */
    @Override
    default ByteArrayOutputStream markByStream(File originFile, String text) throws IOException {
        BufferedImage image = this.mark(originFile, text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加文本水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param text       水印内容
     * @throws IOException IO 异常
     */
    @Override
    default ByteArrayOutputStream markByStream(S style, File originFile, String text) throws IOException {
        BufferedImage image = this.mark(style, originFile, text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            ImageIO.write(image, this.getFormatName(originFile), outputStream);
        }
        return outputStream;
    }

    /**
     * 图片加文本水印
     *
     * @param originFile 源文件
     * @param text       水印内容
     * @throws IOException IO 异常
     */
    @Override
    default String markByBase64(File originFile, String text) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(originFile, text);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 图片加文本水印
     *
     * @param style      渲染样式
     * @param originFile 源文件
     * @param text       水印内容
     * @throws IOException IO 异常
     */
    @Override
    default String markByBase64(S style, File originFile, String text) throws IOException {
        ByteArrayOutputStream outputStream = this.markByStream(style, originFile, text);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 计算水印内容长度
     * <li>中文长度即文本长度</li>
     * <li>英文长度为文本长度二分之一</li>
     *
     * @param text 水印内容
     * @return 水印内容长度
     */
    default int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        return length % 2 == 0 ? length / 2 : length / 2 + 1;
    }

}