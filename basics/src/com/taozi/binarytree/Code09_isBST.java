package com.taozi.binarytree;

/**
 * 判断二叉树是否是搜索二叉树
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/15 22:04
 */
public class Code09_isBST {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来判断，还有一种方法是根据搜索二叉树的中序遍历有序
     *
     * @param root 二叉树根节点
     * @return 是否为搜索二叉树
     */
    public boolean isBST(Node root) {
        if (root == null) return true;
        return process(root).isBST;
    }

    // 需要子树的信息
    private static class Info {
        public boolean isBST;
        public int min;
        public int max;

        public Info(boolean isBST, int min, int max) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }

    /**
     * 当前树是搜索二叉树的条件：
     * 1.左子树和右子树都是搜索二叉树
     * 2.左子树最大值 < 当前值 < 右子树最小值
     * <p>
     * 所以我需要知道我左子树和右子树的信息，将子节点返回信息组合为自己的信息在返回给父节点
     *
     * @param head 当前树头节点
     * @return 当前树信息
     */
    public Info process(Node head) {
        // 如果当前树为空，则没有信息返回
        if (head == null) return null;
        // 获取左子树信息
        Info leftInfo = process(head.left);
        // 获取右子树信息
        Info rightInfo = process(head.right);
        // 找到当前树的最小值和最大值
        // 该值在当前、左子树、右子树中寻找
        int min = head.value;
        int max = head.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
        }
        // 判断当前树是否为平衡二叉树
        boolean isBST = true;
        // 如果左子树或者右子树不为平衡二叉树，那当前树不为平衡二叉树
        if ((leftInfo != null && !leftInfo.isBST)
                || (rightInfo != null && !rightInfo.isBST)) {
            isBST = false;
        }
        // 如果当前值 <= 左子树最大值 或者 右子树最小值 <= 当前值，那当前树不为平衡二叉树
        if ((leftInfo != null && leftInfo.max >= head.value)
                || (rightInfo != null && rightInfo.min <= head.value)) {
            isBST = false;
        }
        // 将自己的信息返回给父节点
        return new Info(isBST, min, max);
    }
}
