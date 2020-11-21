package com.taozi.recursive;

/**
 * 递归获取数组中最大数
 * Master公式（子问题规模一样）：
 * T(N) = a * T(N/b) + O(N^d)(其中的a、b、d都是常数)
 * 的递归函数，可以直接通过Master公式来确定时间复杂度
 * 如果 log(b,a) < d，复杂度为O(N^d)
 * 如果 log(b,a) > d，复杂度为O(N^log(b,a))
 * 如果 log(b,a) == d，复杂度为O(N^d * logN)
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/21 22:59
 */
public class Code01_GetMax {

    public static int getMax(int[] arr) {
        return getMax(arr, 0, arr.length - 1);
    }

    /**
     * 数组arr在 left~right的范围上取最大值
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     * @return 最大值
     */
    private static int getMax(int[] arr, int left, int right) {
        // base case
        // left~right 范围上只有一个数
        if (left == right) {
            return arr[left];
        }
        int middle = left + ((right - left) >> 1);
        // left~middle 上的最大值
        int maxLeft = getMax(arr, left, middle);
        // middle+1~right 上的最大值
        int maxRight = getMax(arr, middle + 1, right);
        return Math.max(maxLeft, maxRight);
    }
}
