package com.taozi.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断二叉树是否是完全二叉树
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/15 20:41
 */
public class Code08_IsCBT {
    // 二叉树节点
    private static class Node {
        public Integer value;
        public Node left;
        public Node right;
    }

    /**
     * 用宽度遍历所有节点，根据节点孩子情况判断是否为完全二叉树
     * 完全二叉树的特征：每一层的节点都是从左往右排列，当前层不满不会排下一层
     *
     * @param root 树根节点
     * @return 是否为完全二叉树
     */
    public boolean isCBT(Node root) {
        if (root == null) return true;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        // 是否遇到孩子不双全的节点，如果是的话后续的节点都应该是叶子节点
        boolean leaf = false;
        // 左子节点
        Node left = null;
        // 右子节点
        Node right = null;
        // 当前处理节点
        Node cur = root;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            left = cur.left;
            right = cur.right;
            // 如果为叶子节点且有孩子，说明不是完全二叉树
            if (leaf && (left != null || right != null)) return false;
            // 如果有右孩子但没有左孩子，说明不是完全二叉树
            if (left == null && right != null) return false;
            // 如果当前节点左右孩子不双全，说明后续都应该是叶子节点
            if (left == null || right == null) leaf = true;
            if (left != null) queue.add(left);
            if (right != null) queue.add(right);
        }
        return true;
    }

    /**
     * 通过二叉树递归来判断当前树是否为完全二叉树
     *
     * @param root 二叉树根节点
     * @return 是否为完全二叉树
     */
    public boolean isCBT1(Node root) {
        return process(root).isCBT;
    }

    // 需要子树的信息
    private static class Info {
        public boolean isFBT;
        public boolean isCBT;
        public int height;

        public Info(boolean isFBT, boolean isCBT, int height) {
            this.isFBT = isFBT;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    /**
     * 是完全二叉树的条件
     * 1.左右子树都是满二叉树
     * 2.左子树为完全二叉树，右子树为满二叉树，且左子树高度比右子树高度多1
     * 3.左子树为满二叉树，右子树为完全二叉树，且左子树高度与右子树高度相同
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        // 默认空树的情况
        if (head == null) return new Info(true, true, 0);
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 当前数高度为子树最大高度+1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 如果左右子树都是满二叉树且高度相同则该树为满二叉树
        boolean isFBT = leftInfo.isFBT && rightInfo.isFBT && leftInfo.height == rightInfo.height;
        // 如果是满二叉树则也就是完全二叉树
        boolean isCBT = isFBT;
        // 当左子树为完全二叉树，右子树为满二叉树，且左子树高度比右子树高度多1，那么该树为完全二叉树
        if (leftInfo.isCBT && rightInfo.isFBT && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        // 当左子树为满二叉树，右子树为完全二叉树，且左子树高度与右子树高度相同，那么该树为完全二叉树
        else if (leftInfo.isFBT && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        // 返回给父树的信息
        return new Info(isFBT, isCBT, height);
    }
}
