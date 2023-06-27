package com.vaulka.kit.tree.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 树形结构基础信息
 *
 * @author Vaulka
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreeNode<T, R> {

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
