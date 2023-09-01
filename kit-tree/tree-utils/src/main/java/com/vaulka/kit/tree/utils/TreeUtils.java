package com.vaulka.kit.tree.utils;


import com.vaulka.kit.tree.model.TreeNode;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树状结构工具
 *
 * @author Vaulka
 */
public class TreeUtils<T extends TreeNode<T, R>, R> {

    /**
     * 构建树状结构
     *
     * @param data   数据列表
     * @param rootId 顶层节点 ID
     * @return 树状结构数据
     */
    public List<T> buildNode(List<T> data, R rootId) {
        return this.buildNode(data, rootId, null);
    }

    /**
     * 构建树状结构
     *
     * @param data       数据列表
     * @param rootId     顶层节点 ID
     * @param comparator 排序规则
     * @return 树状结构数据
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
     * 构建子节点树状结构
     *
     * @param results    顶层树状结构数据
     * @param nodeGroup  树状结构结构
     * @param comparator 排序规则
     */
    private void buildChildNode(List<T> results, Map<R, List<T>> nodeGroup, Comparator<? super T> comparator) {
        for (TreeNode<T, R> result : results) {
            // 获取子节点列表
            List<T> children = Optional.ofNullable(nodeGroup.remove(result.getId())).orElse(Collections.emptyList());
            result.setChildren(children);
            // 递归构建子孙节点树状结构
            this.buildChildNode(children, nodeGroup, comparator);
            // 排序
            Optional.ofNullable(comparator).ifPresent(children::sort);
        }
    }

    /**
     * 删除无效数据树状节点
     *
     * @param treeNodes 树状结构数据
     * @param predicate 节点保留规则
     * @return 节点是否删除
     */
    public Boolean removeInvalidNode(List<T> treeNodes, Predicate<T> predicate) {
        boolean isReserve = false;
        Iterator<T> iterator = treeNodes.iterator();
        while (iterator.hasNext()) {
            T treeNode = iterator.next();
            if (this.removeInvalidNode(treeNode.getChildren(), predicate) || predicate.test(treeNode)) {
                isReserve = true;
                continue;
            }
            iterator.remove();
        }
        return isReserve;
    }

}
