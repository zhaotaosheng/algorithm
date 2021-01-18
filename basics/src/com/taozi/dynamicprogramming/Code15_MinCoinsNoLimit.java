package com.taozi.dynamicprogramming;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 *
 * @author taosheng.zhao
 * @since 2021/1/18 12:29
 */
public class Code15_MinCoinsNoLimit {

    public int minCoinsNoLimit(int[] arr, int aim) {
        return process(0, aim, arr);
    }

    /**
     * 在[cur,arr.length)范围上组成remain金额的最少货币数
     *
     * @param cur    当前要考虑的货币
     * @param remain 还有多少金额没处理
     * @param arr    货币数组
     * @return 最少的货币数，Integer.MAX_VALUE表示无解
     */
    private int process(int cur, int remain, int[] arr) {
        if (remain == 0) return 0;
        if (cur == arr.length) return Integer.MAX_VALUE;
        // 假设从cur货币开始要解决remain金额是无解的
        int ans = Integer.MAX_VALUE;
        // 当前面值的货币使用0张时的最少数量，用1张时，用2张时......
        for (int num = 0; arr[cur] * num <= remain; num++) {
            int nextAns = process(cur + 1, remain - arr[cur] * num, arr);
            // 如果剩下的金额，后续处理不了，那么就不考虑这种情况了
            if (nextAns != Integer.MAX_VALUE) {
                ans = Math.min(ans, nextAns + num);
            }
        }
        return ans;
    }

    public int dp1(int[] arr, int aim) {
        int size = arr.length;
        int[][] dp = new int[size + 1][aim + 1];
        for (int remain = 1; remain < aim + 1; remain++) {
            dp[size][remain] = Integer.MAX_VALUE;
        }
        for (int cur = size - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                int ans = Integer.MAX_VALUE;
                for (int num = 0; arr[cur] * num <= remain; num++) {
                    int nextAns = dp[cur + 1][remain - arr[cur] * num];
                    if (nextAns != Integer.MAX_VALUE) {
                        ans = Math.min(ans, nextAns + num);
                    }
                }
                dp[cur][remain] = ans;
            }
        }
        return dp[0][aim];
    }

    public int dp2(int[] arr, int aim) {
        int size = arr.length;
        int[][] dp = new int[size + 1][aim + 1];
        for (int remain = 1; remain < aim + 1; remain++) {
            dp[size][remain] = Integer.MAX_VALUE;
        }
        for (int cur = size - 1; cur >= 0; cur--) {
            for (int remain = 1; remain < aim + 1; remain++) {
                dp[cur][remain] = dp[cur + 1][remain];
                if (remain - arr[cur] >= 0 && dp[cur][remain - arr[cur]] != Integer.MAX_VALUE) {
                    dp[cur][remain] = Math.min(dp[cur][remain], dp[cur][remain - arr[cur]] + 1);
                }
            }
        }
        return dp[0][aim];
    }
}
