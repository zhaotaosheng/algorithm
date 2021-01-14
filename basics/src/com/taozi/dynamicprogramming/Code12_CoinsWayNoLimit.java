package com.taozi.dynamicprogramming;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 *
 * @author taosheng.zhao
 * @since 2021/1/14 13:21
 */
public class Code12_CoinsWayNoLimit {

    /**
     * @param arr 货币数组
     * @param aim 凑的钱数
     * @return 方法数
     */
    public int coinsWayNoLimit(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
//        return process1(0, aim, arr);
        return process2(0, aim, arr);
    }

    /**
     * 在[cue，arr.length)范围上凑够remain钱数的方法
     *
     * @param cur    当前判断的货币
     * @param remain 剩余的钱数
     * @param arr    面值数组
     * @return 方法数
     */
    private int process1(int cur, int remain, int[] arr) {
        // 货币数组的钱都用光了
        if (cur == arr.length) return remain == 0 ? 1 : 0;
        // 货币数组的钱没用光了，但是已经凑够了
        if (remain == 0) return 1;
        // 货币数组的钱没用光了，但是已经凑多了
        if (remain < 0) return 0;
        // 当前货币不用的情况
        int p1 = process1(cur + 1, remain, arr);
        // 当前货币用的情况，由于可以使用，不用移动到下一位
        int p2 = process1(cur, remain - arr[cur], arr);
        return p1 + p2;
    }

    private int process2(int cur, int remain, int[] arr) {
        if (cur == arr.length) return remain == 0 ? 1 : 0;
        if (remain == 0) return 1;
        // 组合的方法数
        int ways = 0;
        // 当前面值用的张数
        int num = 0;
        for (; arr[cur] * num <= remain; num++) {
            // 当前面值用0张的方法数 + 用1张的方法数 + 用2张的方法数....
            ways += process2(cur + 1, remain - arr[cur] * num, arr);
        }
        return ways;
    }

    /**
     * 对应process1的动态规划版本
     *
     * @param arr 面值数组
     * @param aim 凑的钱数
     * @return 方法数
     */
    public int coinsWayNoLimit1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        for (int row = 0; row < length + 1; row++) {
            dp[row][0] = 1;
        }
        for (int cur = length - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                int p1 = dp[cur + 1][remain];
                int p2 = remain - arr[cur] >= 0 ? dp[cur][remain - arr[cur]] : 0;
                dp[cur][remain] = p1 + p2;
            }
        }
        return dp[0][aim];
    }

    public int coinsWayNoLimit2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) return 0;
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        for (int row = 0; row < length + 1; row++) {
            dp[row][0] = 1;
        }
        for (int cur = length - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                int ways = 0;
                int num = 0;
                for (; arr[cur] * num <= remain; num++) {
                    ways += dp[cur + 1][remain - arr[cur] * num];
                }
                dp[cur][remain] = ways;
            }
        }
        return dp[0][aim];
    }
}