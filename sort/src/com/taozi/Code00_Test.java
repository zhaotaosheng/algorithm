package com.taozi;

import java.util.Arrays;

public class Code00_Test {

    public static void main(String[] args) {
        int times = 10_000;
        int maxSize = 10_000;
        int maxValue = 10_000;
        Code00_Sort sorter = new Code01_SelectionSort();
        for (int i = 0; i < times; i++) {
            int[] arr = Code00_Sort.generateRandomArray(maxSize, maxValue);
            int[] copy1 = Arrays.copyOf(arr, arr.length);
            int[] copy2 = Arrays.copyOf(arr, arr.length);
            sorter.sort(copy1);
            Arrays.sort(copy2);
            if (!Code00_Sort.isEqual(copy1, copy2)) {
                System.out.println("出现问题");
                print(arr);
                return;
            }
        }
        System.out.println("没有问题");
    }


    /**
     * 打印数组元素
     *
     * @param arr 要打印的数组
     */
    private static void print(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
