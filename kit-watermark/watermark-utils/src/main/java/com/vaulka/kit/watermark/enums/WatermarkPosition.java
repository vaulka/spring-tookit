package com.vaulka.kit.watermark.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 水印位置
 *
 * @author Vaulka
 */
@Getter
@AllArgsConstructor
public enum WatermarkPosition {

    /**
     * 居中
     */
    CENTER,

    /**
     * 左上
     */
    TOP_LEFT,

    /**
     * 左下
     */
    BOTTOM_LEFT,

    /**
     * 右上
     */
    TOP_RIGHT,

    /**
     * 右下
     */
    BOTTOM_RIGHT

}
