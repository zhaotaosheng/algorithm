package com.taozi.D06_BFPRT;

import java.util.PriorityQueue;

/**
 * 在无序数组中求第K小的数
 *
 * @author ZhaoTaoSheng
 * @since 2021/2/24 20:03
 */
public class Code01_FindMinKth {

    // 将前k个数加入大根堆中，遍历数组，后续的数如果小于堆顶，则将堆顶弹出，将该数加入到堆中。O(N*logk)
    public static int findMinKthHeap(int[] arr, int k) throws Exception {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 以随机数pivot来做partition，如果k命中在range中，说明第k小的数就是pivot。O(N)
    public static int findMinKthPartition(int[] arr, int k) throws Exception {
        if (k > arr.length || k < 1) throw new Exception("k参数非法");
        return process(arr, 0, arr.length - 1, k - 1);
    }

    private static int process(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return pivot;
        } else if (index < range[0]) {
            return process(arr, L, range[0] - 1, index);
        } else /*if (index > range[1])*/ {
            return process(arr, range[1] + 1, R, index);
        }
    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int p1 = L - 1;
        int p2 = R + 1;
        int index = L;
        while (index < p2) {
            if (arr[index] < pivot) {
                swap(arr, index, p1 + 1);
                p1++;
                index++;
            } else if (arr[index] > pivot) {
                swap(arr, index, p2 - 1);
                p2--;
            } else {
                index++;
            }
        }
        return new int[]{p1 + 1, p2 - 1};
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 以BFPRT求出的数来做partition，如果k命中在range中，说明第k小的数就是BFPRT求出的数。O(N)
    public static int findMinKthBFPRT(int[] arr, int k) throws Exception {
        if (k > arr.length || k < 1) throw new Exception("k参数非法");
        return processBFPRT(arr, 0, arr.length - 1, k - 1);
    }

    private static int processBFPRT(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = bfprt(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return pivot;
        } else if (index < range[0]) {
            return processBFPRT(arr, L, range[0] - 1, index);
        } else /*if (index > range[1])*/ {
            return processBFPRT(arr, range[1] + 1, R, index);
        }
    }

    // 以5个数为一组，找到各组的中位数后生成一个新数组，在返回新数组的中位数
    private static int bfprt(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        // 每个team就是一组
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        // 此时mArr并不是有序的，通过processBFPRT找到中位数
        return processBFPRT(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    // 对每组5个数进行排序，返回中位数
    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    // 5个数就采用插入排序就可以
    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) throws Exception {
        int testTime = 1000000;
        int maxSize = 1000;
        int maxValue = 1000;

        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = findMinKthHeap(arr, k);
            int ans2 = findMinKthPartition(arr, k);
            int ans3 = findMinKthBFPRT(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
