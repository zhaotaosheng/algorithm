package com.taozi.recursive;

import java.util.Stack;

/**
 * @author taosheng.zhao
 * @since 2021/1/2 18:57
 */
public class Code04_ReverseStack<T> {

    public void reverseStack(Stack<T> stack) {
        if (stack.isEmpty()) {
            return;
        }
        // 将栈底元素弹出
        T val = process(stack);
        // 将栈剩余元素依次按栈底弹出，压栈
        reverseStack(stack);
        // 将弹出元素压栈
        stack.push(val);
    }

    /**
     * 将当前栈底元素弹出，其余元素顺序不变
     *
     * @param stack 栈
     * @return 弹出栈底的栈
     */
    private T process(Stack<T> stack) {
        T cur = stack.pop();
        if (stack.isEmpty()) {
            return cur;
        }
        // 如果当前栈还有元素则递归找到栈底后返回，将之前的元素依次压栈
        T last = process(stack);
        stack.push(cur);
        return last;
    }
}
