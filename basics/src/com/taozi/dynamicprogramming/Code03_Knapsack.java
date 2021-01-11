package com.taozi.dynamicprogramming;

/**
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/11 15:17
 */
public class Code03_Knapsack {

    /**
     * 计算背包在的最大价值
     *
     * @param weights 物品重量
     * @param values  物品价值
     * @param bag     背包重量
     * @return 最大价值
     */
    public int knapsack(int[] weights, int[] values, int bag) {
        return process(0, bag, weights, values);
    }

    /**
     * 计算背包在cur ~ 最后物品中选择且在剩余容量只能的最大价值
     *
     * @param cur     当前做选择的物品序号
     * @param remain  背包剩余重量
     * @param weights 物品重量
     * @param values  物品价值
     * @return 最大价值
     */
    private int process(int cur, int remain, int[] weights, int[] values) {
        // 如果背包超载返回-1
        if (remain < 0) return -1;
        // 背包全装完还有剩余返回0
        if (cur == weights.length) return 0;
        // 不要当前物品的最好价值
        int p1 = process(cur + 1, remain, weights, values);
        // 要当前物品的最好价值
        int p2 = 0;
        int next = process(cur + 1, remain - weights[cur], weights, values);
        // 装下当前物品不超重的情况下才能算上该物品的价值
        if (next != -1) {
            p2 = values[cur] + next;
        }
        return Math.max(p1, p2);
    }

    /**
     * 计算背包在的最大价值
     *
     * @param weights 物品重量
     * @param values  物品价值
     * @param bag     背包重量
     * @return 最大价值
     */
    public int knapsack1(int[] weights, int[] values, int bag) {
        int num = weights.length;
        int[][] dp = new int[num + 1][bag + 1];
        for (int cur = num - 1; cur >= 0; cur--) {
            for (int remain = 0; remain <= bag; remain++) {
                // 不要当前物品的最好价值
                int p1 = dp[cur + 1][remain];
                // 要当前物品的最好价值
                int p2 = 0;
                int next = remain - weights[cur] < 0 ? -1 : dp[cur + 1][remain - weights[cur]];
                // 装下当前物品不超重的情况下才能算上该物品的价值
                if (next != -1) {
                    p2 = values[cur] + next;
                }
                dp[cur][remain] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }
}
