package com.yankee.lineage.utils;

import com.yankee.lineage.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构相关操作工具类
 *
 * @author Yankee
 * @program yankee-lineage
 * @description
 * @since 2021/9/3
 */
public class TreeNodeUtil {
    private TreeNodeUtil() {
    }

    /**
     * 返回所有叶子节点
     *
     * @param root Tree
     * @param <T>  节点类型
     * @return List<T>
     */
    public static <T> List<T> searchTreeLeafNodeList(TreeNode<T> root) {
        List<T> list = new ArrayList<>();
        traverseNodeLineageTree(root, list);
        return list;
    }

    /**
     * 返回叶子列表
     *
     * @param root           TreeNode<TableNode>
     * @param sourceNodeList List<TableNode>
     */
    private static <T> void traverseNodeLineageTree(TreeNode<T> root, List<T> sourceNodeList) {
        if (root.isLeaf()) {
            sourceNodeList.add(root.getValue());
        } else {
            root.getChilds().forEach(node -> traverseNodeLineageTree(node, sourceNodeList));
        }
    }
}
