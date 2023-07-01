package com.vaulka.kit.tree.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Vaulka
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StringUser extends TreeNode<StringUser, String> {

    @Serial
    private static final long serialVersionUID = 6575309502023938960L;

    public StringUser(String id, String parentId, String name, Integer sex) {
        super(id, parentId);
        this.name = name;
        this.sex = sex;
    }

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

}
