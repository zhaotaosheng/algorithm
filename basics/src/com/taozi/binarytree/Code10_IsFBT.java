package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，返回这颗二叉树是不是满二叉树
 *
 * @author taosheng.zhao
 * @since 2020/12/16 12:40
 */
public class Code10_IsFBT {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来判断
     *
     * @param root 二叉树根节点
     * @return 是否为满二叉树
     */
    public boolean isFull(Node root) {
        return process(root).isFull;
    }

    // 需要子树的信息
    private static class Info {
        public boolean isFull;
        public int height;

        public Info(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }

    /**
     * 当前树是满二叉树条件：
     * 1.左子树和右子树都是满二叉树
     * 2.左子树高度与右子树高度一致
     * <p>
     * 所以我需要知道我左子树和右子树的信息，将子节点返回信息组合为自己的信息在返回给父节点
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        // 默认空树是高度为0的满二叉树
        if (head == null) return new Info(true, 0);
        // 获取左子树信息
        Info leftInfo = process(head.left);
        // 获取右子树信息
        Info rightInfo = process(head.right);
        // 当前树高度为左右子树最大高度加当前树头
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = false;
        // 左子树、右子树都是满二叉树且左子树高等于右子树高时表示当前树为满二叉树
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isFull = true;
        }
        // 将当前树信息最为子树信息返回到父树
        return new Info(isFull, height);
    }


    /**
     * 根据满二叉树的性质判断：节点数 = 层数的平方 - 1
     *
     * @param root 二叉树根节点
     * @return 是否为满二叉树
     */
    public boolean isFull1(Node root) {
        if (root == null) return true;
        Info1 rootInfo = process1(root);
        return rootInfo.nodes == (1 << rootInfo.height) - 1;
    }

    // 需要子树的信息
    private static class Info1 {
        public int height;
        public int nodes;

        public Info1(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    /**
     * 统计当前节点的高度与节点数
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info1 process1(Node head) {
        // 默认空树是高度为0的满二叉树
        if (head == null) return new Info1(0, 0);
        // 获取左子树信息
        Info1 leftInfo = process1(head.left);
        // 获取右子树信息
        Info1 rightInfo = process1(head.right);
        // 当前树高度为左右子树最大高度加当前树头
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 当前数节点数为所有子节点数只和加当前树头
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        // 将当前树信息最为子树信息返回到父树
        return new Info1(height, nodes);
    }
}
