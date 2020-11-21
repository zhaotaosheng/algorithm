package com.taozi.structure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用队列实现栈
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 22:35
 */
public class Code06_QueueImplStack {

    /**
     * 使用两个队列实现栈，queue为大部分时间放置数据的队列，
     * queueHelp为弹出数据时的辅助队列，在弹出时将queue中的数据
     * 导入到queueHelp并保留最后一个，弹出这最后一个然后交换引用
     *
     * @param <T>
     */
    public static class Stack<T> {
        // 常用存储队列
        public Queue<T> queue = new LinkedList<>();
        // 辅助队列
        public Queue<T> queueHelp = new LinkedList<>();

        /**
         * 正常往队列中添加
         *
         * @param value 添加的元素
         */
        public void push(T value) {
            queue.add(value);
        }

        /**
         * 弹出时倒数据，queue中留下最后一个元素，就是返回调用者的数据
         */
        public T pop() {
            while (queue.size() > 1) {
                queueHelp.add(queue.poll());
            }
            T value = queue.poll();
            Queue<T> tmp = queue;
            queue = queueHelp;
            queueHelp = tmp;
            return value;
        }

        /**
         * 倒数据才能看到最后一个进入的元素，和pop一样，只不过最后那个数据还要如help队列中
         */
        public T peek() {
            while (queue.size() > 1) {
                queueHelp.add(queue.poll());
            }
            T value = queue.poll();
            queueHelp.add(value);
            Queue<T> tmp = queue;
            queue = queueHelp;
            queueHelp = tmp;
            return value;
        }
    }
}
