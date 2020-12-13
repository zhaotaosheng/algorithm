package com.taozi.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现多叉树与二叉树之间的转换
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/13 20:06
 */
public class Code04_EncodeNaryTreeToBinaryTree {
    // 多叉树节点
    private static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // 二叉树节点
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 将多叉树转换为二叉树
     *
     * @param root 多叉树根节点
     * @return 二叉树根节点
     */
    private TreeNode encode(Node root) {
        if (root == null) return null;
        TreeNode treeNode = new TreeNode(root.val);
        // 生成根节点的左子树
        treeNode.left = encode(root.children);
        return treeNode;
    }

    /**
     * 将多叉树的子节点都放在二叉树左子树上
     * 递归将多叉树所有children节点挂在二叉树的左子树的右边界上
     * 每生成一个二叉树节点时都优先生成它对应的左子树
     *
     * @param children 多叉树节点的子节点
     * @return 二叉树子树头节点
     */
    private TreeNode encode(List<Node> children) {
        // 二叉树子节点的头
        TreeNode head = null;
        // 二叉树节点指针，表述当前将要处理的节点
        TreeNode cur = null;
        // 遍历多叉树所有的子节点
        for (Node child : children) {
            TreeNode treeNode = new TreeNode(child.val);
            // 将多叉树子树的第一个节点设置为二叉树子树的头节点
            if (head == null) {
                head = treeNode;
            }
            // 其余多叉树子节点都挂在头节点的右边界
            else {
                cur.right = treeNode;
            }
            cur = treeNode;
            // 生成当前节点的左子树
            cur.left = encode(child.children);
        }
        return head;
    }

    /**
     * 将二叉树转换为多叉树
     *
     * @param root 二叉树根节点
     * @return 多叉树根节点
     */
    private Node decode(TreeNode root) {
        if (root == null) return null;
        return new Node(root.val, de(root.left));
    }

    /**
     * 二叉树的左子树的右边界就是多叉树节点的children节点集合
     *
     * @param head 二叉树头节点
     * @return 多叉树children节点
     */
    private List<Node> de(TreeNode head) {
        List<Node> children = new ArrayList<>();
        while (head != null) {
            // 生成多叉树的children节点
            Node cur = new Node(head.val, de(head.left));
            // 将该节点放到集合中
            children.add(cur);
            // 来到右子节点，所有的右子节点都应属于一个集合
            head = head.right;
        }
        return children;
    }
}
