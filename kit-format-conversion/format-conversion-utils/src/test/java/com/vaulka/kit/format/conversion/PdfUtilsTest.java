package com.vaulka.kit.format.conversion;

import com.vaulka.kit.format.conversion.utils.PdfUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * PDF 工具
 *
 * @author Vaulka
 **/
public class PdfUtilsTest {

    /**
     * 输入文件
     */
    File inFile = new File("src/test/resources/default.pdf");

    @Test
    public void toWord() throws IOException {
        File outFile = new File("build/default.docx");
        // TODO OutOfMemoryError: Java heap space
        PdfUtils.toWord(inFile, outFile);
    }

    @Test
    public void toHtml() throws IOException {
        File outFile = new File("build/default.html");
        // TODO OutOfMemoryError: Inconsistent saving options detected : 'CustomStrategyOfCssUrlCreation','CustomCssSavingStrategy','CustomResourceSavingStrategy' may not be null when requested saving to stream!
        PdfUtils.toHtml(inFile, outFile);
    }

}
