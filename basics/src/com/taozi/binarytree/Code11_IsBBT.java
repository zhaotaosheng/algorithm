package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，返回这颗二叉树是不是平衡二叉树
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/16 19:59
 */
public class Code11_IsBBT {
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
     * @return 是否为平衡二叉树
     */
    public boolean isBBT(Node root) {
        return process(root).isBBT;
    }

    // 需要子树的信息
    private static class Info {
        public boolean isBBT;
        public int height;

        public Info(boolean isBBT, int height) {
            this.isBBT = isBBT;
            this.height = height;
        }
    }

    /**
     * 当前树是平衡二叉树条件：
     * 1.左子树和右子树都是平衡二叉树
     * 2.左子树高度与右子树高度差不大于1
     * <p>
     * 所以我需要知道我左子树和右子树的信息，将子节点返回信息组合为自己的信息在返回给父节点
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        if (head == null) return new Info(true, 0);
        // 获取左子树信息
        Info leftInfo = process(head.left);
        // 获取右子树信息
        Info rightInfo = process(head.right);
        // 当前树高度为子树最大高度加1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 默认为非平衡二叉树
        boolean isBBT = false;
        // 满足左子树和右子树都是平衡二叉树且左子树高度与右子树高度差不大于1
        // 则当前树为平衡二叉树
        if (leftInfo.isBBT && rightInfo.isBBT
                && Math.abs(leftInfo.height - rightInfo.height) <= 1) {
            isBBT = true;
        }
        // 将当前树信息返回到父树当中
        return new Info(isBBT, height);
    }
}
