package com.taozi.binary;

public class Code01_BinarySearchExist {

    public static boolean exist(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        // 二分搜索左边界，起始为 0
        int left = 0;
        // 二分搜索右边界，起始为 N-1
        int right = arr.length - 1;
        // 中点下标，中点数值
        int middleIndex = 0, middleNum = 0;
        // 每次循环取上次的一半长度
        while (left <= right) {
            // 防止溢出，middleIndex = (left + right) / 2
            middleIndex = left + ((right - left) >> 1);
            middleNum = arr[middleIndex];
            // 如果中点数值是目标值直接返回
            if (target == middleNum) {
                return true;
            }
            // 如果目标值大于中点数值则更新左边界为中点下标 +1
            else if (target > middleNum) {
                left = middleIndex + 1;
            }
            // 如果目标值小于中点数值则更新右边界为中点下标 -1
            else if (target < middleNum) {
                right = middleIndex - 1;
            }
        }
        return false;
    }


    public static boolean existComparator(int[] arr, int target) {
        for (int i : arr) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }
}
