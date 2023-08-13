package com.vaulka.kit.watermark.model.text.impl;

import com.vaulka.kit.watermark.enums.WatermarkPosition;
import com.vaulka.kit.watermark.model.text.TextRenderStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单文本渲染样式
 *
 * @author Vaulka
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleTextRenderStyle extends TextRenderStyle {

    /**
     * 水印位置
     */
    private WatermarkPosition position = WatermarkPosition.CENTER;

    /**
     * 水印之间 X 轴间隔百分比
     */
    private int x = 15;

    /**
     * 水印之间 Y 轴间隔百分比
     */
    private int y = 15;

}
