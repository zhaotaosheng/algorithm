package com.taozi.linkedlist;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
 * 【要求】
 * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/10 19:06
 */
public class Code05_Intersect {
    // 单链表节点
    private static class Node {
        public int value;
        public Node next;
    }

    /**
     * 如果链表无环返回空，有环则返回入环点
     *
     * @param head 头节点
     * @return 入环点
     */
    private Node inRingedPoint(Node head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        Node slow = head.next;
        Node fast = head.next.next;
        // 找到快慢指针的相遇点
        while (slow != fast) {
            // 如果快指针走到了null说明无环
            if (fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 将快指针重置
        fast = head;
        // 调整快指针步幅，再相遇点则为入环点
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 找到两个链表的第一个相交点
     *
     * @param head1 第一个链表头节点
     * @param head2 第二个链表头节点
     * @return 相交点
     */
    private Node intersect(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        if (inRingedPoint(head1) == null && inRingedPoint(head2) == null) {
            return noRinged(head1, head2);
        }
        if (inRingedPoint(head1) != null && inRingedPoint(head2) != null) {
            return bothRinged(head1, head2);
        }
    }

    /**
     * 两个链表都没有环的情况下
     * 1.相交的话，说明从相交点到链表未是重合的，当两条链表长度一致时，按顺序遍历两链表节点
     * 则一定会找到相交点，如果长度不一致，则让长的链表走到和短的链表长度一致
     * 2.不相交的话，遍历也不会找到相等的节点
     *
     * @param head1 第一个链表头节点
     * @param head2 第二个链表头节点
     * @return 相交点
     */
    private Node noRinged(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node cur1 = head1;
        Node cur2 = head2;
        int diffLength = 0;
        while (cur1.next != null) {
            diffLength++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            diffLength--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) return null;
        cur1 = diffLength > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        diffLength = Math.abs(diffLength);
        while (diffLength != 0) {
            cur1 = cur1.next;
            diffLength--;
        }
        while (cur1 != null) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }
}
