package com.taozi.D02_MonotonousStack;

import java.util.Stack;

/**
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 * 一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 * 那么所有子数组中，这个值最大是多少？
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/28 21:06
 */
public class Code02_AllTimesMinToMax {

    public static int getMaximum(int[] arr) {
        int length = arr.length;
        // 前缀和数组，求[i,j]的累加和 = prefixSum[j] - prefixSum[i-1]
        int[] prefixSum = new int[length];
        prefixSum[0] = arr[0];
        for (int cur = 1; cur < length; cur++) {
            prefixSum[cur] = prefixSum[cur - 1] + arr[cur];
        }
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        for (int cur = 0; cur < length; cur++) {
            // 如果当前位置的数引起栈顶弹出，说明当前位置数是第一个在[栈顶,arr.length)范围上小于等于arr[栈顶]的数
            // 如果是等于导致的弹栈，那么结果就是不对的，因为它本来可以继续往右扩大的，而现在停下来了
            // 但是不影响最终结果，因为当下一个等于的数结算的时候结果是正确，它的右侧可以正常的扩，而左侧就是它压着的那个栈
            // 所以可以得到结论：它可以代表之前所有相等的结算错误的位置，表示这整个位置是联通的
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[cur]) {
                Integer index = stack.pop();
                // 现在来结算以arr[index]为最小值的子数组结果，数组范围[stack.peek()+1，cur-1]
                int subSum = stack.isEmpty() ? prefixSum[cur - 1] : prefixSum[cur - 1] - prefixSum[stack.peek()];
                max = Math.max(max, arr[index] * subSum);
            }
            stack.push(cur);
        }
        // 用相同的办法结算栈中剩余的数
        while (!stack.isEmpty()) {
            Integer index = stack.pop();
            // 现在来结算以arr[index]为最小值的子数组结果，数组范围[stack.peek()+1，length-1]
            int subSum = stack.isEmpty() ? prefixSum[length - 1] : prefixSum[length - 1] - prefixSum[stack.peek()];
            max = Math.max(max, arr[index] * subSum);
        }
        return max;
    }
}
