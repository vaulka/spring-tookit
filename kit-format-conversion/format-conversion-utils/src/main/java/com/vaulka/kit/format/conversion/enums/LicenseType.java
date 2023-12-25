package com.vaulka.kit.format.conversion.enums;

import com.aspose.cells.License;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

/**
 * 许可证类型
 *
 * @author Vaulka
 */
@Getter
@AllArgsConstructor
public enum LicenseType {

    CELLS("cells"),

    IMAGING("imaging"),

    PDF("pdf"),

    SLIDES("slides"),

    WORDS("words"),

    ;

    /**
     * 许可证描述
     */
    private final String remark;

    /**
     * 获取许可证（去除水印）
     *
     * @return 获取许可证（去除水印）
     */
    public static boolean getLicense(LicenseType type) {
        try {
            License license = new License();
            String path = "\\aspose\\license\\license-" + type.getRemark() + ".xml";
            InputStream is = LicenseType.class.getClassLoader().getResourceAsStream(path);
            license.setLicense(is);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
        return true;
    }

}
