package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的大小
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/16 20:34
 */
public class Code12_MaxSubBSTSize {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来获取最大搜索二叉子树
     *
     * @param root 二叉树根节点
     * @return 最大搜索二叉子树大小
     */
    public int getMaxSubBSTSize(Node root) {
        if (root == null) return 0;
        return process(root).subBSTSize;
    }

    // 需要子树的信息
    private static class Info {
        public int subBSTSize;
        public int nodes;
        public int min;
        public int max;

        public Info(int subBSTSize, int nodes, int min, int max) {
            this.subBSTSize = subBSTSize;
            this.nodes = nodes;
            this.min = min;
            this.max = max;
        }
    }

    /**
     * 最大搜索二叉子树的形成情况
     * 1.左右子树不同时为搜索二叉树，取子树的最大搜索二叉子树
     * 2.左右子树同时为搜索二叉树且符合左子树最大 < 当前值 < 右子树最小，则说明当前整棵树为搜索二叉树
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        if (head == null) return null;
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 当前数所有节点中的最小值
        int min = head.value;
        // 当前数所有节点中的最大值
        int max = head.value;
        // 当前树一共的节点数，左子树节点数 + 右子树节点数 + 1
        int nodes = 1;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            nodes += leftInfo.nodes;
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            nodes += rightInfo.nodes;
        }
        // 如果当前整个树不是搜索二叉树的话，那么记录左子树的最大搜索二叉子树的大小
        int leftSubSize = -1;
        if (leftInfo != null) {
            leftSubSize = leftInfo.subBSTSize;
        }
        // 如果当前整个树不是搜索二叉树的话，那么记录右子树的最大搜索二叉子树的大小
        int rightSubSize = -1;
        if (rightInfo != null) {
            rightSubSize = rightInfo.subBSTSize;
        }
        // 如果当前整个树为搜索二叉树的话，记录整个树的节点，需要满足两个if条件
        int curSubSize = -1;
        boolean leftIsBST = leftInfo == null || (leftInfo.subBSTSize == leftInfo.nodes);
        boolean rightIsBST = rightInfo == null || (rightInfo.subBSTSize == rightInfo.nodes);
        // 左右子树都为搜索二叉树
        if (leftIsBST && rightIsBST) {
            boolean leftMaxLessCur = leftInfo == null || leftInfo.max < head.value;
            boolean rightMinMoreCur = rightInfo == null || rightInfo.min > head.value;
            // 左子树最大 < 当前值 < 右子树最小
            if (leftMaxLessCur && rightMinMoreCur) {
                int leftNodes = leftInfo == null ? 0 : leftInfo.nodes;
                int rightNodes = rightInfo == null ? 0 : rightInfo.nodes;
                curSubSize = leftNodes + rightNodes + 1;
            }
        }
        // 返回给父树的信息
        return new Info(Math.max(Math.max(leftSubSize, rightSubSize), curSubSize), nodes, min, max);
    }
}
