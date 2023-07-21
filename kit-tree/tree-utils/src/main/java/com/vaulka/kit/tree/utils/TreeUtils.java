package com.vaulka.kit.tree.utils;


import com.vaulka.kit.tree.model.TreeNode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 树形结构工具
 *
 * @author Vaulka
 */
public class TreeUtils<T extends TreeNode<T, R>, R> {

    public List<T> buildNode(List<T> data, R rootId) {
        return this.buildNode(data, rootId, null);
    }

    /**
     * 构建树形结构
     *
     * @param data       数据列表
     * @param rootId     顶层节点 ID
     * @param comparator 排序规则
     * @return 树形结构数据
     */
    public List<T> buildNode(List<T> data, R rootId, Comparator<? super T> comparator) {
        if (data.size() == 0) {
            return Collections.emptyList();
        }
        Map<R, List<T>> nodeGroup = data.stream()
                .collect(Collectors.groupingBy(TreeNode::getParentId));
        // 取出顶层节点
        List<T> results = Optional.ofNullable(nodeGroup.remove(rootId)).orElse(Collections.emptyList());
        // 排序
        Optional.ofNullable(comparator).ifPresent(results::sort);
        // 递归构建子节点
        this.buildChildNode(results, nodeGroup, comparator);
        return results;
    }

    /**
     * 构建子节点树形结构
     *
     * @param results    顶层树形结构数据
     * @param nodeGroup  树形结构结构
     * @param comparator 排序规则
     */
    private void buildChildNode(List<T> results, Map<R, List<T>> nodeGroup, Comparator<? super T> comparator) {
        for (TreeNode<T, R> result : results) {
            // 获取子节点列表
            List<T> children = Optional.ofNullable(nodeGroup.remove(result.getId())).orElse(Collections.emptyList());
            result.setChildren(children);
            // 递归构建子孙节点树形结构
            this.buildChildNode(children, nodeGroup, comparator);
            // 排序
            Optional.ofNullable(comparator).ifPresent(children::sort);
        }
    }

}
