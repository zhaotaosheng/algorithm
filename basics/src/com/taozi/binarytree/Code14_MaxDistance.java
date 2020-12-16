package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
 * 返回整棵二叉树的最大距离
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/16 21:49
 */
public class Code14_MaxDistance {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来获取最大距离
     *
     * @param root 二叉树根节点
     * @return 最大距离
     */
    public int getMaxDistance(Node root) {
        return process(root).maxDistance;
    }

    // 需要子树的信息
    private static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    /**
     * 最大距离可能有两种情况
     * 1.最大距离在左子树或在右子树上
     * 2.最大距离为通过当前头节点的左树高+右树高+1
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        if (head == null) return new Info(0, 0);
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 当前树高度为最大子树高度+1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 最大距离要么在子树里，要么经过当前树头
        int maxDistance = Math.max(leftInfo.height + rightInfo.height + 1,
                Math.max(leftInfo.maxDistance, rightInfo.maxDistance));
        // 返回当前树的信息
        return new Info(maxDistance, height);
    }
}
