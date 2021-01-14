package com.taozi.dynamicprogramming;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 *
 * @author taosheng.zhao
 * @since 2021/1/14 12:42
 */
public class Code11_CoinsWaysEveryPaperDifferent {

    /**
     * @param arr 货币数组
     * @param aim 凑的钱数
     * @return 方法数
     */
    public static int coinsWaysEveryPaperDifferent(int[] arr, int aim) {
        return process(0, aim, arr);
    }

    /**
     * 在[cue，arr.length)范围上凑够remain钱数的方法
     *
     * @param cur    当前判断的货币
     * @param remain 剩余的钱数
     * @param arr    货币数组
     * @return 方法数
     */
    public static int process(int cur, int remain, int[] arr) {
        // 货币数组的钱都用光了
        if (cur == arr.length) return remain == 0 ? 1 : 0;
        // 货币数组的钱没用光了，但是已经凑够了
        if (remain == 0) return 1;
        // 货币数组的钱没用光了，但是已经凑多了
        if (remain < 0) return 0;
        // 当前货币不用的情况
        int p1 = process(cur + 1, remain, arr);
        // 当前货币用的情况
        int p2 = process(cur + 1, remain - arr[cur], arr);
        return p1 + p2;
    }

    public static int coinsWaysEveryPaperDifferent1(int[] arr, int aim) {
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        for (int row = 0; row < length + 1; row++) {
            dp[row][0] = 1;
        }
        for (int cur = length - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                int p1 = dp[cur + 1][remain];
                int p2 = remain - arr[cur] >= 0 ? dp[cur + 1][remain - arr[cur]] : 0;
                dp[cur][remain] = p1 + p2;
            }
        }
        return dp[0][aim];
    }
}
