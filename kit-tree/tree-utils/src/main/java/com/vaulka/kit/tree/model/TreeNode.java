package com.vaulka.kit.tree.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 树形结构基础信息
 *
 * @author Vaulka
 */
@Data
@NoArgsConstructor
public class TreeNode<T extends TreeNode<T, R>, R> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5732775650276600108L;

    public TreeNode(R id, R parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    /**
     * ID
     */
    private R id;

    /**
     * 父节点 ID
     */
    private R parentId;

    /**
     * 子节点列表
     */
    private List<T> children;

}
