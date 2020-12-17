package com.taozi.binarytree;

/**
 * 给定一棵二叉树的头节点head，和另外两个节点a和b。
 * 返回a和b的最低公共祖先
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/17 12:35
 */
public class Code15_LowestAncestor {
    // 二叉树节点
    private static class Node {
        public int value;
        public Node left;
        public Node right;
    }

    /**
     * 通过二叉树递归来获取最低公共祖先
     *
     * @param root 二叉树根节点
     * @param a    节点A
     * @param b    节点B
     * @return 最低公共祖先
     */
    public static Node getLowestAncestor(Node root, Node a, Node b) {
        return process(root, a, b).lowestAncestor;
    }

    // 需要子树的信息
    private static class Info {
        public boolean hasA;
        public boolean hasB;
        public Node lowestAncestor;

        public Info(boolean hasA, boolean hasB, Node lowestAncestor) {
            this.hasA = hasA;
            this.hasB = hasB;
            this.lowestAncestor = lowestAncestor;
        }
    }

    /**
     * 最低公共祖先存在的三种情况：
     * 1.在左子树有最低公共祖先，那就不用在找了
     * 2.在右子树有最低公共祖先，那就不用在找了
     * 3.左右子树都没有且在最优子树中存在A和B，那最低公共祖先就是当前节点
     *
     * @param head 当前树头节点
     * @param a    节点A
     * @param b    节点B
     * @return 当前树信息
     */
    public static Info process(Node head, Node a, Node b) {
        if (head == null) return new Info(false, false, null);
        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);
        // 当前树是否有A节点
        boolean findA = head == a || leftInfo.hasA || rightInfo.hasA;
        // 当前树是否有B节点
        boolean findB = head == b || leftInfo.hasB || rightInfo.hasB;
        // 默认没有找到最低公共祖先
        Node lowestAncestor = null;
        // 左子树有最低公共祖先，那就用它
        if (leftInfo.lowestAncestor != null) {
            lowestAncestor = leftInfo.lowestAncestor;
        }
        // 右子树有最低公共祖先，那就用它
        else if (rightInfo.lowestAncestor != null) {
            lowestAncestor = rightInfo.lowestAncestor;
        }
        // 两个子树都没有最低公共祖先且当前树包含A和B，那当前节点就是最低公共祖先
        else if (findA && findB) {
            lowestAncestor = head;
        }
        // 返回父树的信息
        return new Info(findA, findB, lowestAncestor);
    }
}
