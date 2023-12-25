package com.vaulka.kit.format.conversion.utils;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlSaveOptions;
import com.vaulka.kit.format.conversion.enums.LicenseType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * PDF 工具
 *
 * @author Vaulka
 */
public class PdfUtils {

    /**
     * PDF 转 Word
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @throws FileNotFoundException 文件未找到异常
     */
    public static void toWord(File inFile, File outFile) throws FileNotFoundException {
        toWord(new FileInputStream(inFile), new FileOutputStream(outFile));
    }

    /**
     * PDF 转 Word
     *
     * @param inputStream  input 流
     * @param outputStream output 流
     */
    public static void toWord(InputStream inputStream, OutputStream outputStream) {
        if (!LicenseType.getLicense(LicenseType.PDF)) {
            return;
        }
        try {
            Document document = new Document(inputStream);
            DocSaveOptions options = new DocSaveOptions();
            options.setFormat(DocSaveOptions.DocFormat.Doc);
            options.setMode(DocSaveOptions.RecognitionMode.Flow);
            options.setRelativeHorizontalProximity(2.5f);
            options.setRecognizeBullets(true);
            document.save(outputStream, options);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * PDF 转 HTML
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @throws FileNotFoundException 文件未找到异常
     */
    public static void toHtml(File inFile, File outFile) throws FileNotFoundException {
        toHtml(new FileInputStream(inFile), new FileOutputStream(outFile));
    }

    /**
     * PDF 转 HTML
     *
     * @param inputStream  input 流
     * @param outputStream output 流
     */
    public static void toHtml(InputStream inputStream, OutputStream outputStream) {
        if (!LicenseType.getLicense(LicenseType.PDF)) {
            return;
        }
        try {
            Document document = new Document(inputStream);
            HtmlSaveOptions options = new HtmlSaveOptions();
            options.setSplitIntoPages(true);
            document.save(outputStream, options);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * PDF 转 PNG
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @throws FileNotFoundException 文件未找到异常
     */
    public static void toPng(File inFile, File outFile) throws FileNotFoundException {
        toPng(new FileInputStream(inFile), new FileOutputStream(outFile));
    }

    /**
     * PDF 转 PNG
     *
     * @param inputStream  input 流
     * @param outputStream output 流
     */
    public static void toPng(InputStream inputStream, OutputStream outputStream) {
        if (!LicenseType.getLicense(LicenseType.PDF)) {
            return;
        }
        try {
            Document document = new Document(inputStream);
            HtmlSaveOptions options = new HtmlSaveOptions();
            options.setSplitIntoPages(true);
            document.save(outputStream, options);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

}
