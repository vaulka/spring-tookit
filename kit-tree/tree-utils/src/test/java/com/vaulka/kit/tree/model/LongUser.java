package com.vaulka.kit.tree.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Vaulka
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LongUser extends TreeNode<LongUser, Long> {

    public LongUser(Long id, Long parentId, String name, Integer sex) {
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
