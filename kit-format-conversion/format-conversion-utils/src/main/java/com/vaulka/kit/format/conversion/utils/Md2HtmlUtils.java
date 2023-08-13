package com.vaulka.kit.format.conversion.utils;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Markdown 转 HTML 工具
 *
 * @author Vaulka
 **/
public class Md2HtmlUtils {

    /**
     * Markdown 转 HTML
     *
     * @param file markdown 文件
     * @return HTML 内容
     */
    public static String conversion(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return Md2HtmlUtils.conversion(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
        }
    }

    /**
     * Markdown 转 HTML
     *
     * @param md markdown 内容
     * @return HTML 内容
     */
    public static String conversion(String md) {
        // 数据解析选项
        DataHolder options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(
                        DefinitionExtension.create(),
                        EmojiExtension.create(),
                        FootnoteExtension.create(),
                        StrikethroughSubscriptExtension.create(),
                        InsExtension.create(),
                        SuperscriptExtension.create(),
                        TablesExtension.create(),
                        TocExtension.create(),
                        SimTocExtension.create(),
                        WikiLinkExtension.create()
                ));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Document document = parser.parse(md);
        return renderer.render(document);
    }

}
