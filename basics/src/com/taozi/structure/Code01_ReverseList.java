package com.taozi.structure;

/**
 * 链表反转
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 17:08
 */
public class Code01_ReverseList {

    /**
     * 单链表节点
     *
     * @param <T>
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
     * 单链表反转，使用prev,next双指针，逐步向后移动header
     * header
     * A -> B -> C -> D -> null
     * D -> C -> B -> A -> null
     *
     * @param header 原链表头节点
     * @param <T>    节点值类型
     * @return 反转后头节点
     */
    public static <T> Node<T> reverseList(Node<T> header) {
        Node<T> prev = null;
        Node<T> next = null;
        while (header != null) {
            // next指针用于保存下一个遍历的header
            next = header.next;
            // 将header的next指针反转
            header.next = prev;
            // prev指针用于保存下一个节点的next指针指向
            prev = header;
            // 移动header到下一个节点
            header = next;
        }
        // 当header = null时说明它的上一个节点就是最后一个节点，也是新链表的header
        return prev;
    }

    /**
     * 双链表节点
     *
     * @param <T>
     */
    private static class DoubleNode<T> {
        public T value;
        public DoubleNode<T> prev;
        public DoubleNode<T> next;

        public DoubleNode(T value, DoubleNode<T> next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            if (prev == null) {
                return "null" + " <- " + value + " <--> " + next;
            }
            return value + " <--> " + next;
        }
    }

    /**
     * 双链表反转，使用prev,next双指针，逐步向后移动header
     * ------header
     * null <- A <--> B <--> C <--> D -> null
     * null <- D <--> C <--> B <--> A -> null
     *
     * @param header 原链表头节点
     * @param <T>    节点值类型
     * @return 反转后头节点
     */
    public static <T> DoubleNode<T> reverseDoubleList(DoubleNode<T> header) {
        DoubleNode<T> prev = null;
        DoubleNode<T> next = null;
        while (header != null) {
            // next指针用于保存下一个遍历的header
            next = header.next;
            // 将header的next与prev互换
            header.next = header.prev;
            header.prev = next;
            // prev指针用于保存下一个节点的next指针指向
            prev = header;
            // 移动header到下一个节点
            header = next;
        }
        // 当header = null时说明它的上一个节点就是最后一个节点，也是新链表的header
        return prev;
    }
}
