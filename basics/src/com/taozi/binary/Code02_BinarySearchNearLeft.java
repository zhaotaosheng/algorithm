package com.taozi.binary;


import com.taozi.sort.Code00_Sort;

import java.util.Arrays;

/**
 * 有序数组中，找到大于等于目标值的最小下标
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/14 20:02
 */
public class Code02_BinarySearchNearLeft {

    /**
     * 在有序数组arr中找到 >= target的最左位置
     *
     * @param arr    查找数组
     * @param target 目标值
     * @return 位置下标，没有返回 -1
     */
    public static int leftSearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // 二分搜索左边界，起始为 0
        int left = 0;
        // 二分搜索右边界，起始为 N-1
        int right = arr.length - 1;
        // 中点下标，中点数值
        int middleIndex = 0, middleNum = 0;
        // 返回值下标
        int index = -1;
        // 每次循环取上次的一半长度
        while (left <= right) {
            // 防止溢出，middleIndex = (left + right) / 2
            middleIndex = left + ((right - left) >> 1);
            middleNum = arr[middleIndex];
            // 如果目标值小于等于中点数值，说明需要继续向左二分查找
            // 更新右边界为中点下标 -1，并记录该中点下标，更新index数值
            if (target <= middleNum) {
                right = middleIndex - 1;
                index = middleIndex;
            }
            // 如果目标值大于中点数值，说明需要向右二分查找
            // 更新左边界为中点下标 +1，此中点数值不符合题意，所以不用更新index
            else {
                left = middleIndex + 1;
            }
        }
        return index;
    }

    /**
     * 用来对比的方法
     *
     * @param arr    查找数组
     * @param target 目标值
     * @return 位置下标，没有返回 -1
     */
    public static int leftSearchComparator(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int times = 1000;
        int maxSize = 1000;
        int maxValue = 1000;
        for (int i = 0; i < times; i++) {
            int[] arr = Code00_Sort.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int target = (int) ((maxSize + 1) * Math.random());
            if (leftSearch(arr, target) != leftSearchComparator(arr, target)) {
                System.out.println("出现问题");
                return;
            }
        }
        System.out.println("没有问题");
    }
}
