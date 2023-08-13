package com.vaulka.kit.watermark.model.image.impl;

import com.vaulka.kit.watermark.enums.WatermarkPosition;
import com.vaulka.kit.watermark.model.image.ImageRenderStyle;
import com.vaulka.kit.watermark.model.text.impl.SingleTextRenderStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单图片渲染样式
 *
 * @author Vaulka
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleImageRenderStyle extends ImageRenderStyle {

    /**
     * 水印位置
     */
    private WatermarkPosition position = WatermarkPosition.CENTER;

    /**
     * 是否固定坐标
     * <p>
     * 是的话，则 {@link SingleImageRenderStyle#x}、{@link SingleImageRenderStyle#y} 轴参数为固定数值
     * <p>
     * 否的话，则 {@link SingleImageRenderStyle#x}、{@link SingleImageRenderStyle#y} 轴参数则为百分比数值
     */
    private boolean isFixedCoordinates = false;

    /**
     * 水印 X 轴
     */
    private int x = 15;

    /**
     * 水印 Y 轴
     */
    private int y = 15;

}
