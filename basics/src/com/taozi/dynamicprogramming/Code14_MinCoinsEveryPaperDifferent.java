package com.taozi.dynamicprogramming;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 返回组成aim的最少货币数
 * 注意：
 * 因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 *
 * @author taosheng.zhao
 * @since 2021/1/18 12:29
 */
public class Code14_MinCoinsEveryPaperDifferent {

    /**
     * @param arr 货币数组
     * @param aim 组成金额
     * @return 最少的货币数，Integer.MAX_VALUE表示无解
     */
    public int minCoinsEveryPaperDifferent(int[] arr, int aim) {
        return process(0, aim, arr);
    }

    /**
     * 在[cur,arr.length)访问上组成remain金额的最少货币数
     *
     * @param cur    当前要考虑的货币
     * @param remain 还有多少金额没处理
     * @param arr    货币数组
     * @return 最少的货币数，Integer.MAX_VALUE表示无解
     */
    private int process(int cur, int remain, int[] arr) {
        if (remain == 0) return 0;
        if (cur == arr.length) return Integer.MAX_VALUE;
        // 不使用当前货币的最好结果
        int p1 = process(cur + 1, remain, arr);
        // 使用当前货币的最好结果，假设没有结果
        int p2 = Integer.MAX_VALUE;
        // 如果还有剩余的金额没处理，则继续往下，如果remain为负，则表示无解
        if (remain - arr[cur] >= 0) {
            p2 = process(cur + 1, remain - arr[cur], arr);
            // 如果剩下的金额有解，则加上当前货币，如果剩下的金额搞不定，那当前方案也是搞不定
            if (p2 != Integer.MAX_VALUE) {
                p2 += 1;
            }
        }
        return Math.min(p1, p2);
    }

    private int dp(int[] arr, int aim) {
        int size = arr.length;
        int[][] dp = new int[size + 1][aim + 1];
        for (int remain = 1; remain < aim + 1; remain++) {
            dp[size][remain] = Integer.MAX_VALUE;
        }
        for (int cur = size - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                int p1 = dp[cur + 1][remain];
                int p2 = Integer.MAX_VALUE;
                if (remain - arr[cur] >= 0) {
                    p2 = dp[cur + 1][remain - arr[cur]];
                    if (p2 != Integer.MAX_VALUE) {
                        p2 += 1;
                    }
                }
                dp[cur][remain] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }
}
