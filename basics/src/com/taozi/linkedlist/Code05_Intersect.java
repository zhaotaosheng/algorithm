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
        Node point1 = inRingedPoint(head1);
        Node point2 = inRingedPoint(head2);
        if (point1 == null && point2 == null) {
            return noRinged(head1, head2);
        }
        if (point1 != null && point2 != null) {
            return bothRinged(head1, head2, point1, point2);
        }
        // 一个有环一个无环则肯定没有交点
        return null;
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
        // 用来记录两个链表长度差值
        int diffLength = 0;
        // 记录节点个数，所以终止条件应该是 cur.next != null
        while (cur1.next != null) {
            diffLength++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            diffLength--;
            cur2 = cur2.next;
        }
        // 如果两个链表最后一个节点不同说明不相交
        if (cur1 != cur2) return null;
        // cur1指向长的链表头
        cur1 = diffLength > 0 ? head1 : head2;
        // cur2指向短的链表头
        cur2 = cur1 == head1 ? head2 : head1;
        diffLength = Math.abs(diffLength);
        // 将长链表先走到与短链表长度相同的节点
        while (diffLength != 0) {
            cur1 = cur1.next;
            diffLength--;
        }
        // 两个链表同步前进，循环结束条件要么是相交点，要么是走到了null
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 两个链表都有环的情况下
     * 1.如果相交，一种可能是在入环点之前相交，另外一种可能是在环上相交
     * 第一种情况，说明两链表入环点相等，把链表从入环点切开，则前半段与两个链表都无环的查找方法一样
     * 第二种情况，说明两链表入环点不相等，找到两个链表的入环点，其中一个绕圈，则两个入环点必定相遇
     * 2.如果不相交，两个链表的入环点一定不一样而且遍历环的时候也一定不相遇
     *
     * @param head1  第一个链表头节点
     * @param head2  第二个链表头节点
     * @param point1 第二个链表入环点
     * @param point2 第二个链表入环点
     * @return 相交点
     */
    private Node bothRinged(Node head1, Node head2, Node point1, Node point2) {
        // 如果入环点一样，则以入环点为尾，按照无环方式查找
        if (point1 == point2) {
            if (head1 == null || head2 == null) return null;
            Node cur1 = head1;
            Node cur2 = head2;
            int diffLength = 0;
            while (cur1.next != point1) {
                diffLength++;
                cur1 = cur1.next;
            }
            while (cur2.next != point2) {
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
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }
        // 如果入环点不一样，则遍历链表一的环，看是否有节点等于链表二的入环点
        else {
            Node cur = point1.next;
            while (cur != point1) {
                if (cur == point2) return cur;
                cur = cur.next;
            }
        }
        return null;
    }
}
