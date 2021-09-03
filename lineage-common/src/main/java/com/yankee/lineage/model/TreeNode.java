package com.yankee.lineage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据血缘解析时先把AST转换成此结构-树结构
 *
 * @author Yankee
 * @program yankee-lineage
 * @description
 * @since 2021/9/3
 */
public class TreeNode<T> {
    AtomicLong id = new AtomicLong(0);

    T value;

    TreeNode<T> parent;

    List<TreeNode<T>> childs;

    int height;

    int subtreeSize;

    public TreeNode() {
    }

    TreeNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getRoot() {
        TreeNode<T> current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    public void initChilds() {
        if (childs == null) {
            childs = new ArrayList<>();
        }
    }

    public boolean isLeaf() {
        if (childs == null) {
            return true;
        }
        return childs.size() == 0;
    }

    public boolean isOneChildAndLeaf() {
        return childs != null && childs.size() == 1 && childs.get(0).isLeaf();
    }

    public void addChild(TreeNode<T> childNode) {
        initChilds();
        childNode.parent = this;
        childs.add(childNode);
        childNode.height = Optional.ofNullable(childNode.parent).map(node -> node.height + 1).orElse(0);
        this.subtreeSize++;
        childNode.id = new AtomicLong(Optional.ofNullable(childNode.parent).map(node -> node.id.get() + 1).orElse(0L));
    }

    public List<TreeNode<T>> getChilds() {
        return childs;
    }

    public AtomicLong getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getSubtreeSize() {
        return subtreeSize;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public static <T> TreeNode<T> of(T data) {
        TreeNode<T> treeNode = new TreeNode<T>();
        treeNode.setValue(data);
        return treeNode;
    }
}
