package com.taozi.other;

import com.taozi.sort.Code00_Sort;

import java.util.Arrays;

/**
 * 有序数组中，二分法查找目标值是否存在
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/14 20:02
 */
public class Code01_BinarySearch {

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
     * 在有序数组arr中找到 <= target的最右位置
     *
     * @param arr    查找数组
     * @param target 目标值
     * @return 位置下标，没有返回 -1
     */
    public static int rightSearch(int[] arr, int target) {
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
            // 如果目标值大于等于中点数值，说明需要继续向右二分查找
            // 更新左边界为中点下标 +1，并记录该中点下标，更新index数值
            if (target >= middleNum) {
                left = middleIndex + 1;
                index = middleIndex;
            }
            // 如果目标值小于中点数值，说明需要向左二分查找
            // 更新右边界为中点下标 -1，此中点数值不符合题意，所以不用更新index
            else {
                right = middleIndex - 1;
            }
        }
        return index;
    }


    /**
     * 无序数组中，相邻两个数字不相等，找到局部最小
     *
     * @param arr 查找数组
     * @return 位置下标，没有返回 -1
     */
    public static int localMinimum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // 如果数组只有1位或0位置小于1位置时，0位置为局部最小
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        // 如果数组 N-1 位置小于 N-2 位置时，N-1 位置为局部最小
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        // 以上两个条件没满足则说明0位置到1位置是单调下降，N-1 位置到 N-2 位置单调上升
        // 所以在中间必定会存在一个拐点，其值小于左右两侧的值
        // 由于对左右两侧最边界做过判断了所以在 2~N-2范围开始搜索
        int left = 1;
        int right = arr.length - 2;
        int middle;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            // 如果中间值大于左侧值，则形成 middle-1 到 middle 的单调上升，
            // 则只需要在middle的左侧继续搜索
            if (arr[middle - 1] < arr[middle]) {
                right = middle - 1;
            }
            // 如果中间值大于右侧值，则形成 middle 到 middle-1 的单调下降，
            // 则只需要在middle的右侧继续搜索
            else if (arr[middle + 1] < arr[middle]) {
                left = middle + 1;
            }
            // 进到这里说明中间值小于两侧，则它就是局部最小
            else {
                return middle;
            }
        }
        return -1;
    }
}
