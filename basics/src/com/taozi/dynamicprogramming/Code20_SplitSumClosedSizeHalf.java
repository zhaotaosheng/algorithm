package com.taozi.dynamicprogramming;

/**
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 *
 * @author ZhaoTaoSheng
 * @since 2021/1/23 19:55
 */
public class Code20_SplitSumClosedSizeHalf {

    public static int sum(int[] arr) {
        int n;
        if (arr == null || (n = arr.length) < 2) return 0;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        if ((n & 1) == 0) {
            return process(arr, 0, n >> 1, sum >> 1);
        } else {
            return Math.max(process(arr, 0, n >> 1, sum >> 1),
                    process(arr, 0, (n >> 1) + 1, sum >> 1));
        }
    }

    /**
     * 从[cur,arr.length)范围上随意挑选，需要使用rest个数，构成的累加和尽量靠近remain
     *
     * @param arr    数组
     * @param cur    当前考虑的位置
     * @param rest   需要考虑的数量
     * @param remain 需要尽量靠近的数
     * @return 靠近remain的累加和
     */
    private static int process(int[] arr, int cur, int rest, int remain) {
        // 当没有数需要考虑的时候，只有rest也恰好为0才是有效解
        if (cur == arr.length) return rest == 0 ? 0 : -1;
        // 不考虑当前位置的数，直接取下一位置的结果
        int p1 = process(arr, cur + 1, rest, remain);
        // 考虑当前位置的数，默认表示无效解
        int p2 = -1;
        // 如果当前位置数大于remain的肯定不需要考虑了，表示搞不定
        if (arr[cur] <= remain) {
            int next = process(arr, cur + 1, rest - 1, remain - arr[cur]);
            // 如果下游没搞定那么当前方案也一样搞不定
            if (next != -1) {
                p2 = arr[cur] + next;
            }
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
        int size = (n + 1) >> 1;
        int aim = sum >> 1;
        int[][][] dp = new int[n + 1][size + 1][aim + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= size; j++) {
                for (int k = 0; k <= aim; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int k = 0; k <= aim; k++) {
            dp[n][0][k] = 0;
        }
        for (int cur = n - 1; cur >= 0; cur--) {
            for (int rest = 0; rest <= size; rest++) {
                for (int remain = 0; remain <= aim; remain++) {
                    int p1 = dp[cur + 1][rest][remain];
                    int p2 = -1;
                    if (arr[cur] <= remain && rest - 1 >= 0) {
                        int next = dp[cur + 1][rest - 1][remain - arr[cur]];
                        if (next != -1) {
                            p2 = arr[cur] + next;
                        }
                    }
                    dp[cur][rest][remain] = Math.max(p1, p2);
                }
            }
        }
        if ((n & 1) == 0) {
            return dp[0][size][aim];
        } else {
            return Math.max(dp[0][size][aim], dp[0][size - 1][aim]);
        }
    }
}
