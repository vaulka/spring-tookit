package com.vaulka.kit.watermark.model.image;

import lombok.Data;

import java.awt.AlphaComposite;

/**
 * 单图片渲染样式
 *
 * @author Vaulka
 */
@Data
public abstract class ImageRenderStyle {

    /**
     * 透明度
     */
    private AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);

}
