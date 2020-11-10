package com.taozi;

@FunctionalInterface
public interface Code00_Sort {

    /**
     * 数组排序
     *
     * @param arr 想要排序的数组
     */
    void sort(int[] arr);

    /**
     * 交换数组任意两下标位置的数
     *
     * @param arr 要交换的数组
     * @param i   下标1
     * @param j   下标2
     */
    default void swap(int[] arr, int i, int j) {
        if (i == j) return;
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * 通过给定的参数生成随机数组
     *
     * @param maxSize  最大数组的元素数量
     * @param maxValue 数组元素的最大值 -maxValue ~ maxValue
     * @return 随机数组
     */
    static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() 取值范围 小数 [0,1)
        // N * Math.random() 取值范围 小数 [0,N)
        // (int) (N * Math.random()) 取值范围 整数 [0,N-1]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            // [0,maxValue] - [0,maxValue] 范围 [-maxValue,maxValue]
            arr[i] = (int) ((maxValue + 1) * Math.random())
                    - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    /**
     * 比较两个数组是否相等
     *
     * @param arr1 数组1
     * @param arr2 数组2
     * @return true为相等，false为不等
     */
    static boolean isEqual(int[] arr1, int[] arr2) {
        boolean arr1Null = arr1 == null;
        boolean arr2Null = arr2 == null;
        // 两个全为null返回true
        if (arr1Null && arr2Null) {
            return true;
        }
        // 一个为null一个不为null返回false
        if (arr1Null ^ arr2Null) {
            return false;
        }
        // 程序走到这代表arr1与arr2都不为null，所以不用NullCheck
        int arr1Length = arr1.length;
        int arr2Length = arr2.length;
        // 数组长度不等返回false
        if (arr1Length != arr2Length) {
            return false;
        }
        // 任意位置数字不等返回false
        for (int i = 0; i < arr1Length - 1; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印数组元素
     *
     * @param arr 要打印的数组
     */
    static void print(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
