package com.vaulka.kit.watermark;

import com.vaulka.kit.watermark.utils.impl.PhotoFullScreenTextWatermarkUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Vaulka
 */
public class PhotoFullScreenTextWatermarkUtilsTest {

    /**
     * 源文件
     */
    File file = new File("src/test/resources/xyz.jpg");

    /**
     * 全屏图片水印
     */
    PhotoFullScreenTextWatermarkUtils utils = new PhotoFullScreenTextWatermarkUtils();

    /**
     * 图片打水印 Stream 流
     */
    @Test
    public void markByStream() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("build/xyz.jpg");
        utils.markByStream(file, "超级无敌美少女", outputStream);
    }

    /**
     * 图片打水印 Base64 信息
     *
     * @throws IOException IO 异常
     */
    @Test
    public void markByBase64() throws IOException {
        System.out.println("math captcha image base64: " + utils.markByBase64(file, "Vaulka"));
    }

}
