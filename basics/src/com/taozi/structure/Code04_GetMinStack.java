package com.taozi.structure;

import java.util.Stack;

/**
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * pop、push、getMin操作的时间复杂度都是 O(1)
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 21:31
 */
public class Code04_GetMinStack {

    /**
     * 使用一个保存最小值栈辅助，保持两边数据量一致
     */
    public static class MinStack {
        // 正常的数据栈
        public Stack<Integer> stack = new Stack<>();
        // 放最小值的栈
        public Stack<Integer> minStack = new Stack<>();

        /**
         * 当push的数小于最小值栈栈顶的时候才会压入这个数据，否则压入最小值栈栈顶数
         *
         * @param value 插入数据
         */
        public void push(Integer value) {
            stack.push(value);
            if (minStack.isEmpty()) {
                minStack.push(value);
            } else if (value < getMin()) {
                minStack.push(value);
            } else {
                minStack.push(getMin());
            }
        }

        /**
         * 两个栈数据量同步，一起pop
         *
         * @return 取出的数据
         */
        public Integer pop() {
            if (stack.isEmpty())
                throw new RuntimeException("栈为空");
            minStack.pop();
            return stack.pop();
        }

        /**
         * 栈的最小值就是当前最小值栈的栈顶
         *
         * @return 当前栈最小值
         */
        public Integer getMin() {
            if (minStack.isEmpty())
                throw new RuntimeException("栈为空");
            return minStack.peek();
        }
    }

    /**
     * 使用一个保存最小值栈辅助，最小值栈只有当push的值小于等于最小值栈顶时
     * 才压入最小值栈，这样最小值栈不会和数据栈体量一样大
     */
    public static class MinStack1 {
        // 正常的数据栈
        public Stack<Integer> stack = new Stack<>();
        // 放最小值的栈
        public Stack<Integer> minStack = new Stack<>();

        /**
         * 当push的数小于等于最小值栈栈顶的时候才会压入这个数据
         *
         * @param value 插入数据
         */
        public void push(Integer value) {
            stack.push(value);
            if (minStack.isEmpty()) {
                minStack.push(value);
            } else if (value <= getMin()) {
                minStack.push(value);
            }
        }

        /**
         * 数据栈正常pop，只有pop出来的值等于最小值栈栈顶时，最小值栈才pop
         *
         * @return 取出的数据
         */
        public Integer pop() {
            if (stack.isEmpty())
                throw new RuntimeException("栈为空");
            Integer value = stack.pop();
            if (value.equals(getMin())) {
                minStack.pop();
            }
            return value;
        }

        /**
         * 栈的最小值就是当前最小值栈的栈顶
         *
         * @return 当前栈最小值
         */
        public Integer getMin() {
            if (minStack.isEmpty())
                throw new RuntimeException("栈为空");
            return minStack.peek();
        }
    }
}
