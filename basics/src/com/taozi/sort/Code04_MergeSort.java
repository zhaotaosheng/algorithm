package com.taozi.sort;

/**
 * 归并排序，最坏、最优、平均时间复杂度N*logN，空间复杂度N，稳定排序
 * 使用master计算：
 * T(N) = 2 * T(N/2) + O(N)
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/22 13:45
 */
public class Code04_MergeSort implements Code00_Sort {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 在数组的 0 ~ N-1的位置上排序
        process(arr, 0, arr.length - 1);
    }

    /**
     * 将数组arr从[left....right]排成有序，逐步分解
     * 将左半侧排序，在将右半侧排序，最终将两侧结果合并
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 有边界
     */
    private void process(int[] arr, int left, int right) {
        // base case, 当left、right指针缩小到同一个位置的时候，说明不用在继续往下分
        if (left == right) return;
        // 中点的位置
        int middle = left + ((right - left) >> 1);
        // 在left到middle的范围做归并
        process(arr, left, middle);
        // 在middle + 1到right的范围做归并
        process(arr, middle + 1, right);
        // arr在left到middle已经有序，在middle + 1到right也有序，将两侧合并
        merge(arr, left, middle, right);
    }

    /**
     * 使用一个辅助数组和两个指向两侧数组开头的指针，来进行合并
     * 在合并的过程中，哪侧数据小哪侧的数据进入辅助数组，并移动指针
     *
     * @param arr    数组
     * @param left   左侧数组的起始位置
     * @param middle 左侧数组的结束位置，middle+1为右侧数组的起始位置
     * @param right  右侧数组的结束位置
     */
    private void merge(int[] arr, int left, int middle, int right) {
        // 辅助用数组，用来将左右两侧已经有序的数组合并到一起
        int[] help = new int[right - left + 1];
        // 辅助数组用的指针
        int index = 0;
        // 左侧数组用指针，默认左侧数组起始位置
        int p1 = left;
        // 右侧数组用指针，默认右侧数组起始位置
        int p2 = middle + 1;
        // 当左右数组指针没有超出各自范围时，比较两侧指针当前所对应的值，
        // 谁小谁落到辅助数组中，并将自己的指针和辅助数组的指针都要++，指向下一位
        while (p1 <= middle && p2 <= right) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 当有一侧超出范围时，另一侧的剩余的所有数组直接落进辅助数组
        while (p1 <= middle) {
            help[index++] = arr[p1++];
        }
        while (p2 <= right) {
            help[index++] = arr[p2++];
        }
        // 将辅助数组的数据拷贝回原数组
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }

    /**
     * 使用迭代的方式实现归并排序
     */
    private class Code04_MergeSort_Iterator implements Code00_Sort {

        @Override
        public void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            int length = arr.length;
            // 每次归并时左右数组元素的个数，起始为1，呈2倍递增
            int step = 1;
            while (step < length) {
                // 每次都从第一个元素开始
                int left = 0;
                while (left < length) {
                    // 根据步长找到middle位置
                    int middle = left + step - 1;
                    // 当最后一组的数据只有左侧没有右侧时会符合此条件，那么就不用排序了
                    if (middle >= length) break;
                    // 最后一组的右侧数组数据可能不够，所以取一个合理的值
                    int right = Math.min(middle + step, length - 1);
                    // 归并排序
                    merge(arr, left, middle, right);
                    // 下一组开始
                    left = right + 1;
                }
                // 防止溢出，当step大于Integer.MAX后变为负数了
                if (step > length / 2) break;
                step = step << 1;
            }
        }
    }
}
