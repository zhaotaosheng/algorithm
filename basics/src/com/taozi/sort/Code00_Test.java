package com.taozi.sort;

import java.util.Arrays;

public class Code00_Test {

    public static void main(String[] args) {
        int times = 10_000;
        int maxSize = 1_000;
        int maxValue = 10_000;
        Code00_Sort sorter1 = new Code03_InsertionSort();
        Code00_Sort sorter2 = new Code01_SelectionSort.Code01_SelectionSort_DoubleIndex();
        for (int i = 0; i < times; i++) {
            int[] arr = Code00_Sort.generateRandomArray(maxSize, maxValue);
            int[] copy1 = Arrays.copyOf(arr, arr.length);
            int[] copy2 = Arrays.copyOf(arr, arr.length);
            sorter1.sort(copy1);
//            sorter2.sort(copy2);
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
