package com.taozi.D02_MonotonousStack;

import java.util.Stack;

/**
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的子矩形数量
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/29 20:39
 */
public class Code05_CountSubRectangleInArea {

    public static int count(int[][] matrix) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) return 0;
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        int[] histogram = new int[matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < histogram.length; col++) {
                histogram[col] = matrix[row][col] == 0 ? 0 : histogram[col] + 1;
            }
            for (int col = 0; col < histogram.length; col++) {
                while (!stack.isEmpty() && histogram[stack.peek()] >= histogram[col]) {
                    // 如果当前数等于栈顶的话，就替换掉栈顶就行
                    Integer cur = stack.pop();
                    // 如果当前数大于栈顶的话，就结算栈顶的结果
                    if (histogram[cur] > histogram[col]) {
                        // 找到左边小于当前数最近的位置
                        int left = stack.isEmpty() ? -1 : stack.peek();
                        // 只结算栈顶到两侧较大高度的范围，比如当前高度5，左边是1，右边是2，那就要结算高度为3，4，5的矩形
                        int down = Math.max(left == -1 ? 0 : histogram[left], histogram[col]);
                        // 当前结算囊括的列
                        int num = col - left - 1;
                        // 每个高度的子矩形个数是相同的，总数符合 n * (n + 1) / 2
                        // 比如结算下标2-4的子矩形个数，那就有[2,2][2,3][2,4][3,3][3,4][4,4]
                        res += (histogram[cur] - down) * ((num * (num + 1)) >> 1);
                    }
                }
                stack.push(col);
            }
            while (!stack.isEmpty()) {
                Integer cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                int num = histogram.length - left - 1;
                int down = left == -1 ? 0 : histogram[left];
                res += (histogram[cur] - down) * ((num * (num + 1)) >> 1);
            }
        }
        return res;
    }
}
