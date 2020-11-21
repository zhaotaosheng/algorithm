package com.taozi.structure;

/**
 * 删除链表中给定的值
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 18:09
 */
public class Code02_DeleteGivenNode {

    /**
     * 单链表节点
     */
    private static class Node<T> {
        public T value;
        public Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return value + " -> " + next;
        }
    }

    /**
     * 删除链表中给定值节点，使用cur,prev双指针，逐步向后移动cur
     * header
     * 2 -> 3 -> 3 -> 2 -> 1 -> 2 -> 5 -> null
     * 3 -> 3 -> 1 -> 5 -> null
     *
     * @param header 链表头节点
     * @param value  要删除的节点
     * @return 链表头节点
     */
    public static Node<Integer> deleteNode(Node<Integer> header, int value) {
        // 去除链表头等于给定值的节点
        while (header != null && header.value == value) {
            header = header.next;
        }
        Node<Integer> cur = header;
        Node<Integer> prev = header;
        while (cur != null) {
            // 如果当前节点的值等于给定值，则将前置节点的next指向当前节点next
            if (cur.value == value) {
                prev.next = cur.next;
            }
            // 如果当前节点的值不等于给定值，则更新prev节点为当前节点
            else {
                prev = cur;
            }
            cur = cur.next;
        }
        return header;
    }
}
