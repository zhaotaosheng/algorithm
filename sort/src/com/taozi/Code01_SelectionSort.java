package com.taozi;

public class Code01_SelectionSort implements Code00_Sort {

    public void sort(int[] arr) {
        int length;
        if (arr == null || (length = arr.length) < 2) {
            return;
        }
        // 0 ~ N-1 找到最小值，放到 0 位置
        // 1 ~ N-1 找到最小值，放到 1 位置
        // 2 ~ N-1 找到最小值，放到 2 位置
        for (int i = 0; i < length - 1; i++) {
            // 最小值下标，假设当前 i 下标元素为最小值
            int minIndex = i;
            // 在 i ~ N-1 中找到最小值
            for (int j = i + 1; j < length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            // 把当前最小值放在 i 位置
            swap(arr, i, minIndex);
        }
    }
}
