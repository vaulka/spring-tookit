package com.vaulka.kit.watermark.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全屏文本渲染样式
 *
 * @author Vaulka
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FullScreenTextRenderStyle extends TextRenderStyle {

    /**
     * 水印之间 X 轴间隔
     */
    private int x = 150;

    /**
     * 水印之间 Y 轴间隔
     */
    private int y = 200;

}
