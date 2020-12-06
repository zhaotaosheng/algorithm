package com.taozi.linkedlist;

/**
 * 快慢指针找到列表的中点
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/6 20:43
 */
public class Code01_LinkedListMid {
    // 单链表节点
    private class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     *
     * @param head 头节点
     * @return 奇数长度返回中点，偶数长度返回上中点
     */
    private Node mid1(Node head) {
        // 此链表长度小于等于2个时，直接返回头节点
        if (head == null || head.next == null || head.next.next == null) return head;
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     *
     * @param head 头节点
     * @return 奇数长度返回中点，偶数长度返回下中点
     */
    private Node mid2(Node head) {
        // 此链表长度小于等于1个时，直接返回头节点
        if (head == null || head.next == null) return head;
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     *
     * @param head 头节点
     * @return 奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    private Node mid3(Node head) {
        // 此链表长度小于等于2个时，直接返回null
        if (head == null || head.next == null || head.next.next == null) return null;
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     *
     * @param head 头节点
     * @return 奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    private Node mid4(Node head) {
        // 此链表长度小于等于1个时，直接返回null
        if (head == null || head.next == null) return null;
        if (head.next.next == null) return head;
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
