package com.taozi.binary;

import com.taozi.sort.Code00_Sort;

import java.util.Arrays;

/**
 * 有序数组中，二分法查找目标值是否存在
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/14 20:02
 */
public class Code01_BinarySearchExist {

    /**
     * 二分法迭代判断有序数组中是否存在某个目标数，时间复杂度nlogn
     *
     * @param arr    目标数组
     * @param target 目标数值
     * @return true 存在，false 不存在
     */
    public static boolean exist(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        // 二分搜索左边界，起始为 0
        int left = 0;
        // 二分搜索右边界，起始为 N-1
        int right = arr.length - 1;
        // 中点下标，中点数值
        int middleIndex, middleNumber;
        // 每次循环取上次的一半长度
        while (left <= right) {
            // 防止溢出，middleIndex = (left + right) / 2
            middleIndex = left + ((right - left) >> 1);
            middleNumber = arr[middleIndex];
            // 如果目标值大于中点数值则更新左边界为中点下标 +1
            if (target > middleNumber) {
                left = middleIndex + 1;
            }
            // 如果目标值小于中点数值则更新右边界为中点下标 -1
            else if (target < middleNumber) {
                right = middleIndex - 1;
            }
            // 如果中点数值是目标值直接返回
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * 二分法递归的形式
     *
     * @param arr    目标数组
     * @param target 目标数值
     * @param left   搜索左边界
     * @param right  搜索有边界
     * @return true 存在，false 不存在
     */
    public static boolean existRecursive(int[] arr, int target, int left, int right) {
        // 最终结束条件，防止无限递归
        if (left > right) {
            return false;
        }
        // 中点下标
        int middleIndex = left + ((right - left) >> 1);
        // 中点数值
        int middleNumber = arr[middleIndex];
        // 如果目标值大于中点数值，则将左边界改为中点下标 +1
        if (target > middleNumber) {
            return existRecursive(arr, target, middleIndex + 1, right);
        }
        // 如果目标值小于中点数值，则将右边界改为中点下标 -1
        else if (target < middleNumber) {
            return existRecursive(arr, target, left, middleIndex - 1);
        }
        // 剩下就是正好相等，返回true
        else {
            return true;
        }
    }

    /**
     * 二分法判断值是否存在的对数器
     *
     * @param arr    目标数组
     * @param target 目标数值
     * @return true 存在，false 不存在
     */
    public static boolean existComparator(int[] arr, int target) {
        for (int i : arr) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int times = 10000;
        int maxSize = 10000;
        int maxValue = 10000;
        for (int i = 0; i < times; i++) {
            int[] arr = Code00_Sort.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int target = (int) ((maxSize + 1) * Math.random());
            if (existRecursive(arr, target, 0, arr.length - 1) ^ exist(arr, target)) {
                System.out.println("出现问题");
                return;
            }
        }
        System.out.println("没有问题");
    }
}
