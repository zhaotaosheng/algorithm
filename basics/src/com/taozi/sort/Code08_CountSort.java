package com.taozi.sort;

/**
 * 计数排序，平均、最优、最坏时间复杂度N，空间复杂度M(样本区分度)，稳定排序
 * 一般来讲，计数排序要求，样本是整数，且范围比较窄
 *
 * @author ZhaoTaoSheng
 * @since 2020/12/6 13:51
 */
public class Code08_CountSort implements Code00_Sort {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        // 假设样本范围 0 ~ 120
        int[] bucket = new int[120 + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        // i表示桶的下标，j表示排序数组下标
        for (int i = 0, j = 0; i < bucket.length; i++) {
            // 每个桶位置数是几，就代表要在排序数组中加几个数
            while (bucket[i]-- > 0) {
                arr[j++] = i;
            }
        }
    }
}
