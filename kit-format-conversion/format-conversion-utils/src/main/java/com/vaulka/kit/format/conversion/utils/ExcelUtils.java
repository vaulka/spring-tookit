package com.vaulka.kit.format.conversion.utils;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.vaulka.kit.format.conversion.enums.LicenseType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Excel 工具
 *
 * @author Vaulka
 */
public class ExcelUtils {

    /**
     * Excel 转 PDF
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @throws FileNotFoundException 文件未找到异常
     */
    public static void toPdf(File inFile, File outFile) throws FileNotFoundException {
        toPdf(new FileInputStream(inFile), new FileOutputStream(outFile));
    }

    /**
     * Excel 转 PDF
     *
     * @param inputStream  input 流
     * @param outputStream output 流
     */
    public static void toPdf(InputStream inputStream, OutputStream outputStream) {
        if (!LicenseType.getLicense(LicenseType.CELLS)) {
            return;
        }
        try {
            Workbook wb = new Workbook(inputStream);
            PdfSaveOptions options = new PdfSaveOptions();
            options.setOnePagePerSheet(true);
            wb.save(outputStream, options);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

}
