package com.taozi.other;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
     *
     * @param arr 数组
     * @return 奇数次的数
     */
    private int printOneKindOdd(int[] arr) {
        int num = 0;
        // 偶数次的数自身异或结果为0
        for (int i : arr) {
            num ^= i;
        }
        return num;
    }

    /**
     * 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
     *
     * @param arr 数组
     * @return 两种奇数次的数
     */
    private int[] printTwoKindOdd(int[] arr) {
        int num = 0;
        // 偶数次的数自身异或结果为0
        for (int i : arr) {
            num ^= i;
        }
        // 此时num为两种奇数的异或结果
        // 提取num最右侧的1，说明这个两个奇数的这个位置一个为1一个为0
        int rightmost = num & (~num + 1);//num & (~num + 1) = num & (-num)
        int ans1 = 0;
        int ans2 = 0;
        // 按照最右侧1的位置，将所有数划分为两种，一种该位置为1一种该位置为0
        for (int i : arr) {
            if ((rightmost & i) == 0) {
                ans1 ^= i;
            } else {
                ans2 ^= i;
            }
        }
        return new int[]{ans1, ans2};
    }

    /**
     * 一个数组中有一种数出现K次，其他数都出现了M次，M > 1, K < M找到，出现了K次的数
     * 要求，额外空间复杂度O(1)，时间复杂度O(N)
     *
     * @param arr 数组
     * @param k   k次
     * @param m   m次
     * @return k次的数
     */
    private int printKTimes(int[] arr, int k, int m) {
        // 维护一个map，key为 2^i 次方，value为i，既i位置数为1其余为0
        Map<Integer, Integer> map = new HashMap<>();
        int help = 1;
        for (int i = 0; i < 32; i++) {
            map.put(help << i, i);
        }
        // 用来保存各个位置为1的数量
        int[] collection = new int[32];
        for (int ele : arr) {
            while (ele != 0) {
                // 去到ele最右侧为1的数字
                int rightmost = ele & (-ele);
                // 获取最右侧1数字的位置，并相应的在collection数组位置加1
                collection[map.get(rightmost)]++;
                // 准备获取下一个最右侧1的
                ele ^= rightmost;
            }
        }
        int ans = 0;
        for (int i = 0; i < collection.length; i++) {
            // 如果此位置的个数不能整除m并且能整除k的话，那k次数的该位置一定为1
            if ((collection[i] % m) != 0) {
                if ((collection[i] % k) == 0) {
                    ans |= 1 << i;
                }
                // 这是数据给的错误的情况，如果符合题意一定会满足上面条件
                else {
                    return -1;
                }
            }
        }
        // 如果k次数就是0的话，需要校验一下
        if (ans == 0) {
            int count = 0;
            for (int i = 0; i < arr.length && arr[i] == 0; i++) {
                count++;
            }
            if (count != k) {
                return -1;
            }
        }
        return ans;
    }
}
