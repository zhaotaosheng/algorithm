package com.taozi.sort;

public class Code03_InsertionSort implements Code00_Sort {

    @Override
    public void sort(int[] arr) {
        int length;
        if (arr == null || (length = arr.length) < 2) {
            return;
        }
        // 0 ~ 1 上有序
        // 0 ~ 2 上有序
        // 0 ~ 3 上有序
        for (int i = 1; i < length; i++) {
            // 保持 0 ~ i 上有序
            for (int j = i; j > 0; j--) {
                // 如果当前位置比前一个位置小则交换
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j - 1, j);
                }
                // 如果当前位置不比前一个位置小则说明他也不会被前边的小
                else {
                    break;
                }
            }
        }
    }
}
