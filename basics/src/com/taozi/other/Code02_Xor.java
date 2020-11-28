package com.taozi.other;

/**
 * 异或运算，就是无进位加
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/28 22:31
 */
public class Code02_Xor {

    /**
     * 如何不用额外变量交换两个数
     *
     * @param arr 数组
     * @param i   下标
     * @param j   下标
     */
    private void swap(int[] arr, int i, int j) {
        // 当 i与j 指向同一位置时，异或会将这两个位置数都刷为0
        if (i == j) return;
        // i = i ^ j
        arr[i] = arr[i] ^ arr[j];
        // j = i ^ j ^ j = i
        arr[j] = arr[i] ^ arr[j];
        // i = i ^ j ^ i = j
        arr[i] = arr[i] ^ arr[j];
    }
}
