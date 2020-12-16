package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的头节点
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/16 21:13
 */
public class Code12_MaxSubBSTHead {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来获取最大搜索二叉子树头节点
     *
     * @param root 二叉树根节点
     * @return 最大搜索二叉子树头节点
     */
    public Node getMaxSubBSTSize(Node root) {
        if (root == null) return null;
        return process(root).subBSTHead;
    }

    // 需要子树的信息
    private static class Info {
        public Node subBSTHead;
        public int subBSTSize;
        public int min;
        public int max;

        public Info(Node subBSTHead, int subBSTSize, int min, int max) {
            this.subBSTHead = subBSTHead;
            this.subBSTSize = subBSTSize;
            this.min = min;
            this.max = max;
        }
    }

    private Info process(Node head) {
        if (head == null) return null;
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 当前树所有节点中的最小值
        int min = head.value;
        // 当前树所有节点中的最大值
        int max = head.value;
        // 当前树最大搜索二叉树的大小
        int subBSTSize = -1;
        // 当前树最大搜索二叉树的头节点
        Node subBSTHead = null;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            // 先将最大搜索二叉树的大小和头指向左子树
            subBSTSize = leftInfo.subBSTSize;
            subBSTHead = leftInfo.subBSTHead;
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            // 如果右子树的搜索二叉树的大小大于左子树，在变为指向右子树
            if (rightInfo.subBSTSize > subBSTSize) {
                subBSTSize = rightInfo.subBSTSize;
                subBSTHead = rightInfo.subBSTHead;
            }
        }
        // 如果当前整个树为搜索二叉树的话，记录整个树的节点大小和头，需要满足两个if条件
        boolean leftIsBST = leftInfo == null || (leftInfo.subBSTHead == head.left);
        boolean rightIsBST = rightInfo == null || (rightInfo.subBSTHead == head.right);
        // 左右子树都为搜索二叉树
        if (leftIsBST && rightIsBST) {
            boolean leftMaxLessCur = leftInfo == null || leftInfo.max < head.value;
            boolean rightMinMoreCur = rightInfo == null || rightInfo.min > head.value;
            // 左子树最大 < 当前值 < 右子树最小
            if (leftMaxLessCur && rightMinMoreCur) {
                int leftNodes = leftInfo == null ? 0 : leftInfo.subBSTSize;
                int rightNodes = rightInfo == null ? 0 : rightInfo.subBSTSize;
                subBSTSize = leftNodes + rightNodes + 1;
                subBSTHead = head;
            }
        }
        // 返回给父树的信息
        return new Info(subBSTHead, subBSTSize, min, max);
    }
}
