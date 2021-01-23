package com.taozi.dynamicprogramming;

/**
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/23 19:27
 */
public class Code19_SplitSumClosed {

    public static int sum(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return process(arr, 0, sum >> 1);
    }

    /**
     * 从[cur,arr.length)范围上随意挑选，构成的累加和尽量靠近remain
     *
     * @param arr    数组
     * @param cur    当前考虑的位置
     * @param remain 需要尽量靠近的数
     * @return 靠近remain的累加和
     */
    private static int process(int[] arr, int cur, int remain) {
        if (cur == arr.length) return 0;
        // 不考虑当前位置的数，直接取下一位置的结果
        int p1 = process(arr, cur + 1, remain);
        // 考虑当前位置的数，如果当前位置数大于remain的肯定不需要考虑了
        int p2 = 0;
        if (arr[cur] <= remain) {
            p2 = arr[cur] + process(arr, cur + 1, remain - arr[cur]);
        }
        return Math.max(p1, p2);
    }

    public static int dp(int[] arr) {
        int n;
        if (arr == null || (n = arr.length) < 2) return 0;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        int aim = sum >> 1;
        int[][] dp = new int[n + 1][aim + 1];
        for (int cur = n - 1; cur >= 0; cur--) {
            for (int remain = 0; remain <= aim; remain++) {
                int p1 = dp[cur + 1][remain];
                int p2 = 0;
                if (arr[cur] <= remain) {
                    p2 = arr[cur] + dp[cur + 1][remain - arr[cur]];
                }
                dp[cur][remain] = Math.max(p1, p2);
            }
        }
        return dp[0][aim];
    }
}
