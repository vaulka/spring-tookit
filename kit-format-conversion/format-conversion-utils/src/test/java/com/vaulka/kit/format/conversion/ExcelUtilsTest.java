package com.vaulka.kit.format.conversion;

import com.vaulka.kit.format.conversion.utils.ExcelUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * Excel 工具
 *
 * @author Vaulka
 **/
public class ExcelUtilsTest {

    /**
     * 输入文件
     */
    File inFile = new File("src/test/resources/default.xlsx");

    /**
     * 输出文件
     */
    File outFile = new File("build/default.pdf");

    @Test
    public void toPdf() throws IOException {
        ExcelUtils.toPdf(inFile, outFile);
    }

}
