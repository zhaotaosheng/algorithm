package com.taozi.binary;


public class Code02_BinarySearchNearLeft {

    /**
     * 在arr数组中找到 >= target的最左位置
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
            // 如果中点数值是目标值则更新右边界为中点下标，更新返回值
            if (target <= middleNum) {
                right = middleIndex;
                index = middleIndex;
            }
            // 如果目标值大于中点数值则更新左边界为中点下标 +1
            else if (target > middleNum) {
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
}
