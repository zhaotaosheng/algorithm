package com.taozi.sort;

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

    static class Code01_SelectionSort_DoubleIndex implements Code00_Sort {

        @Override
        public void sort(int[] arr) {
            int length;
            if (arr == null || (length = arr.length) < 2) {
                return;
            }
            int middleIndex = length / 2;
            // 0 ~ N-1-0 找到最小值，放到 0 位置，找到最大值，放到 N-1-0 位置
            // 1 ~ N-1-1 找到最小值，放到 1 位置，找到最大值，放到 N-1-1 位置
            // 2 ~ N-1-2 找到最小值，放到 2 位置，找到最大值，放到 N-1-2 位置
            for (int i = 0; i < middleIndex; i++) {
                // 最小值下标，假设当前 i 下标元素为最小值
                int minIndex = i;
                // 最大值下标，假设当前 N-1-i 下标元素为最大值
                int maxIndex = length - 1 - i;
                // 在 i ~ N-i 中找到最小值，和正常选择排序的区别是要包含边界
                for (int j = i; j < length - i; j++) {
                    minIndex = arr[j] < arr[minIndex] ? j : minIndex;
                    maxIndex = arr[j] > arr[maxIndex] ? j : maxIndex;
                }
                // 把当前最小值放在 i 位置
                swap(arr, i, minIndex);
                // 关键步骤：如果maxIndex恰好等于i，
                // 则上一步指令把原本的i和minIndex数据交换，此时maxIndex应等于minIndex
                maxIndex = maxIndex == i ? minIndex : maxIndex;
                // 把当前最大值放在 N-1-i 位置
                swap(arr, length - 1 - i, maxIndex);
            }
        }
    }
}
