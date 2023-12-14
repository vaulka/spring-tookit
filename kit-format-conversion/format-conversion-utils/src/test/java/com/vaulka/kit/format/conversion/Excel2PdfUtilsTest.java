package com.vaulka.kit.format.conversion;

import com.vaulka.kit.format.conversion.utils.Excel2PdfUtils;

import java.io.FileNotFoundException;

/**
 * Markdown 转 HTML 工具
 *
 * @author Vaulka
 **/
public class Excel2PdfUtilsTest {
    public static void main(String[] args) throws FileNotFoundException {
        String inputExcelPath = "C:\\Users\\kelry\\Downloads\\test.xlsx";
        String outputPdfPath = "C:\\Users\\kelry\\Downloads\\test.pdf";
        Excel2PdfUtils.excel2pdf(inputExcelPath, outputPdfPath);
    }

}
