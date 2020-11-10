package com.taozi.binary;

import com.taozi.sort.Code00_Sort;

import java.util.Arrays;

public class Code00_Test {

    public static void main(String[] args) {
        int times = 1;
        int maxSize = 100;
        int maxValue = 1_0000;
        for (int i = 0; i < times; i++) {
            int[] arr = Code00_Sort.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int target = (int) ((maxSize + 1) * Math.random());
            if (Code02_BinarySearchNearLeft.leftSearch(arr, target)
                    != Code02_BinarySearchNearLeft.leftSearchComparator(arr, target)) {
                System.out.println("出现问题");
                return;
            }
        }
        System.out.println("没有问题");
    }
}
