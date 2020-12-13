package com.taozi.binarytree;

/**
 * 求二叉树中某个节点的后继节点
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/13 21:26
 */
public class Code06_SuccessorNode {
    // 带有父节点指针的二叉树节点
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;
    }

    /**
     * 获取节点的后继节点，以中序为例，
     * 一个节点的后继节点要么是它右子树左边界的最后一个，
     * 要么是它作为左子树右边界最后一个的祖先节点
     *
     * @param node 给的节点
     * @return 后继节点
     */
    private Node getSuccessorNode(Node node) {
        if (node == null) return null;
        // 有右子树，说明后继一定在右子树左边界上
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        // 没有右子树，则后继在某个祖先上，或者可能没有后继
        else {
            Node parent = node.parent;
            // 直到当前节点是父节点的左子树
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }
}
