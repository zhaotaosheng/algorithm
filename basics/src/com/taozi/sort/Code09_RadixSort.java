package com.taozi.sort;

/**
 * 基数排序，平均、最优、最坏时间复杂度N，空间复杂度N，稳定排序
 * 一般来讲，基数排序要求，样本是10进制的正整数
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/6 14:59
 */
public class Code09_RadixSort implements Code00_Sort {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 基数排序
     *
     * @param arr     数组
     * @param left    数组左边界
     * @param right   数组右边界
     * @param maxBits 数组中最大数的位数
     */
    private void radixSort(int[] arr, int left, int right, int maxBits) {
        int[] help = new int[right - left + 1];
        for (int i = 1; i <= maxBits; i++) {
            // i表示i位置，i=1表示个位数，2表示十位数，3表示百位数
            int[] count = new int[10];
            for (int j = left; j <= right; j++) {
                // 此时count以数字i位数按0~9分组，比如count[3]表示i位数为3的数字个数
                count[getDigit(arr[j], i)]++;
            }
            for (int j = 1; j < count.length; j++) {
                // 此时count表示各项前缀和，比如count[3]表示i位数小于等于3的数字个数
                count[j] = count[j - 1] + count[j];
            }
            // 数组从右往左看，落在help数组的时候也是从右往左
            for (int j = right; j >= left; j--) {
                // 找到当前数所属的help位置，比如count[3]表示i位数小于等于3的数字个数
                // help[count[3]]表示i位数小于等于3的数字应落在help数组下标0~count[3]-1的位置上
                // 每次固定一个数字后需要把count相应位置的数字减1，比如count[3]=5表示i位数小于等于3的数字有5个
                // 那么当i位数数字为3的数字第一次来落在help[4]位置，那么下一个i位数数字为3的数字应该落在help[3]位置
                help[--count[getDigit(arr[j], i)]] = arr[j];
            }
            for (int j = 0; j <= right - left; j++) {
                // 将help数组复制回arr相应的位置上
                arr[left + j] = help[j];
            }
        }
    }

    /**
     * 取数字x的d位置数字
     *
     * @param x 要分析的数字
     * @param i 要提取的位置
     * @return 该位置的数
     */
    private int getDigit(int x, int i) {
        return ((x / ((int) Math.pow(10, i - 1))) % 10);
    }

    /**
     * 找到数组中最大数的位数
     *
     * @param arr 数组
     * @return 最大位数
     */
    private int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int maxBits = 0;
        while (max != 0) {
            maxBits++;
            max /= 10;
        }
        return maxBits;
    }
}
