package com.vaulka.kit.format.conversion;

import com.vaulka.kit.format.conversion.utils.Md2HtmlUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * Markdown 转 HTML 工具
 *
 * @author Vaulka
 **/
public class Md2HtmlUtilsTest {

    /**
     * 源文件
     */
    File file = new File("src/test/resources/default.md");

    @Test
    public void md2Html() throws IOException {
        String html = Md2HtmlUtils.conversion(file);
        System.out.println(html);
    }

}
