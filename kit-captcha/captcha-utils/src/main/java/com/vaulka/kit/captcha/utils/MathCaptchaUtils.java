package com.vaulka.kit.captcha.utils;

import com.vaulka.kit.captcha.enums.MathType;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.Random;

/**
 * 算数验证码工具
 *
 * @author Vaulka
 */
public class
MathCaptchaUtils implements CaptchaUtils {

    /**
     * 最小验证码数值
     * <p>
     * 最小 10
     */
    private final int minCode;

    /**
     * 最大验证码数值
     */
    private final int maxCode;

    /**
     * 图像宽度
     */
    private final int imageWidth;

    /**
     * 图像高度
     */
    private final int imageHeight;

    /**
     * 干扰线数量
     */
    private final int drawCount;

    /**
     * 干扰线的长度 = 1.414 * LINE_WIDTH
     */
    private final int lineWidth;

    public MathCaptchaUtils(int minCode, int maxCode,
                            int imageWidth, int imageHeight,
                            int drawCount, int lineWidth) {
        this.minCode = minCode;
        this.maxCode = maxCode;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.drawCount = drawCount;
        this.lineWidth = lineWidth;
    }

    @Override
    public BufferedImage createImage(String code) {
        String codeText = this.createCode(Integer.parseInt(code));
        // 在内存中创建图像
        final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 绘画干扰线
        this.drawInterferenceLines(graphics, imageWidth, imageHeight, drawCount, lineWidth);
        // 取随机产生的认证码
        int x = 0;
        for (int i = 0; i < codeText.length(); i++) {
            // 将认证码显示到图像中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            // 设置字体颜色
            graphics.setColor(Color.BLACK);
            // 设置字体样式
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            // 设置字符，字符间距，上边距
            String str = String.valueOf(codeText.charAt(i));
            if (NumberUtils.isCreatable(str)) {
                // 数字与数字之间间隔小点
                x += 6 + 8;
            } else {
                // 遇到符号则间隔大点
                // FORMAT 中间有空格，所以这边的 x 轴增加较小
                x += 4 + 4;
            }
            graphics.drawString(str, x, 26);
        }
        // 图像生效
        graphics.dispose();
        return image;
    }

    /**
     * 验证码格式
     */
    private static final String CODE_FORMAT = "{0} {1} {2} =  ?";

    /**
     * 最大数值
     */
    private static final int MAX_NUM = 100;

    /**
     * 获取验证码文本
     *
     * @param code 验证码
     * @return 获取验证码文本
     */
    private String createCode(int code) {
        int num = RandomUtils.nextInt(1, 9);
        MathType mathType = MathType.ALL.get(new Random().nextInt(MathType.ALL.size()));
        switch (mathType) {
            case ADD -> {
                return MessageFormat.format(CODE_FORMAT,
                        code - num,
                        mathType.getCode(), num);
            }
            case SUBTRACT -> {
                return MessageFormat.format(CODE_FORMAT,
                        code + num,
                        mathType.getCode(), num);
            }
            case MULTIPLY -> {
                if (code % num > 0) {
                    num = code;
                }
                return MessageFormat.format(CODE_FORMAT,
                        code / num,
                        mathType.getCode(), num);
            }
            case DIVIDE -> {
                if (code * num > MAX_NUM) {
                    // 数字太大的话，不好口算，用户体验会很差
                    num = 1;
                }
                return MessageFormat.format(CODE_FORMAT,
                        code * num,
                        mathType.getCode(), num);
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public String createCode() {
        return String.valueOf(RandomUtils.nextInt(minCode, maxCode));
    }

}
