package com.vaulka.kit.watermark.model.text;

import lombok.Data;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;

/**
 * 文本渲染样式
 *
 * @author Vaulka
 */
@Data
public abstract class TextRenderStyle {

    /**
     * 水印颜色
     */
    private Color color = Color.DARK_GRAY;

    /**
     * 水印字体
     */
    private Font font = new Font("微软雅黑", Font.PLAIN, 14);

    /**
     * 透明度
     */
    private AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);

}
