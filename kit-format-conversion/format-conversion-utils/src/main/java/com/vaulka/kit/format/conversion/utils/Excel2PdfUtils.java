package com.vaulka.kit.format.conversion.utils;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Markdown 转 HTML 工具
 *
 * @author Vaulka
 **/
public class Excel2PdfUtils {

    /**
     * excel 转为 pdf 输出。
     *
     * @param excelPath excel 路径
     * @param pdfPath   pad 路径
     * @throws FileNotFoundException 文件未找到异常
     */
    public static void excel2pdf(String excelPath, String pdfPath) throws FileNotFoundException {
        Excel2PdfUtils.excel2pdf(new FileInputStream(excelPath), new FileOutputStream(pdfPath));
    }

    /**
     * excel 转为 pdf 输出。
     *
     * @param excelInputStream excel input 流
     * @param pdfOutputStream  pdf output 流
     */
    public static void excel2pdf(InputStream excelInputStream, OutputStream pdfOutputStream) {
        // 验证 License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return;
        }
        try {
            Workbook wb = new Workbook(excelInputStream);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            int[] autoDrawSheets = {3};
            // 当 excel 中对应的 sheet 页宽度太大时，在 PDF 中会拆断并分页。此处等比缩放。
//            autoDraw(wb,autoDrawSheets);
            int[] showSheets = {0};
            // 隐藏 workbook 中不需要的 sheet 页。
            printSheetPage(wb, showSheets);
            wb.save(pdfOutputStream, pdfSaveOptions);
            pdfOutputStream.flush();
            pdfOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 获取 license 去除水印
     *
     * @return 获取 license 去除水印
     */
    private static boolean getLicense() {
        try {
            InputStream is = Excel2PdfUtils.class.getClassLoader().getResourceAsStream("\\aspose\\license.xml");
            License license = new License();
            license.setLicense(is);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
        return true;
    }

    /**
     * 设置打印的 sheet 自动拉伸比例
     *
     * @param wb   工作表
     * @param page 自动拉伸的页的 sheet 数组
     */
    private static void autoDraw(Workbook wb, int[] page) {
        if (null != page && page.length > 0) {
            for (int i = 0; i < page.length; i++) {
                wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
                wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
            }
        }
    }

    /**
     * 隐藏 workbook 中不需要的 sheet 页。
     *
     * @param wb   工作表
     * @param page 显示页的 sheet 数组
     */
    private static void printSheetPage(Workbook wb, int[] page) {
        for (int i = 1; i < wb.getWorksheets().getCount(); i++) {
            wb.getWorksheets().get(i).setVisible(false);
        }
        if (null == page || page.length == 0) {
            wb.getWorksheets().get(0).setVisible(true);
        } else {
            for (int i = 0; i < page.length; i++) {
                wb.getWorksheets().get(i).setVisible(true);
            }
        }
    }

}
