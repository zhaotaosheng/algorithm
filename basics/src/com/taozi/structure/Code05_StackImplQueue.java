package com.taozi.structure;

import java.util.Stack;

/**
 * 使用栈实现队列
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 22:21
 */
public class Code05_StackImplQueue {

    /**
     * 使用两个栈来实现队列，push栈放入数据，pop栈弹出数据
     * 数据由push栈导入到pop栈中，正好顺序与压栈顺序想法
     * 导入的条件：
     * 1）push栈要一次性导完
     * 2）等pop栈为空可以倒
     *
     * @param <T>
     */
    public static class Queue<T> {
        // push栈用来放数据
        public Stack<T> pushStack = new Stack<>();
        // pop栈用来取数据
        public Stack<T> popStack = new Stack<>();

        /**
         * 每次put数据后都检查一下是否可以倒数据
         *
         * @param value 加入的数据
         */
        public void add(T value) {
            pushStack.push(value);
            importData();
        }

        /**
         * 先检查是否可以倒数据，在弹出popzhanding
         *
         * @return pop栈顶
         */
        public T poll() {
            if (popStack.isEmpty() && pushStack.isEmpty())
                throw new RuntimeException("没有数据");
            importData();
            return popStack.pop();
        }

        /**
         * 倒数据，在符合条件的前提下将push栈数据一次性导入到pop栈
         */
        private void importData() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
        }
    }
}
