package com.taozi;

public class Code02_BubbleSort implements Code00_Sort {

    @Override
    public void sort(int[] arr) {
        int length;
        if (arr == null || (length = arr.length) < 2) {
            return;
        }
        // 0 ~ N-1 冒泡出最大值，放到 N-1 位置
        // 0 ~ N-2 冒泡出最大值，放到 N-2 位置
        // 0 ~ N-3 冒泡出最大值，放到 N-3 位置
        for (int i = length - 1; i > 0; i--) {
            // 小优化：如果没有进行过交换，代表数组已经有序
            boolean sorted = true;
            // 在 0 ~ i 区间冒泡
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    sorted = false;
                }
            }
            // 数组已经有序
            if (sorted) return;
        }
    }
}
