package com.taozi.sort;

import java.util.PriorityQueue;

/**
 * 堆排序，最坏、最优、平均时间复杂度N*logN，空间复杂度1，不稳定排序
 * 数组可以看作是一个完全二叉树，按照下标从上到下，从左到右排列
 * [1,2,3,4,5]数组可以看作是以下树形结构
 * -----1
 * ----/-\
 * ---2---3
 * --/-\-/-\
 * -4--5....
 * 大根堆：任一一棵子树的头节点是当前子树最大节点
 * -----5
 * ----/-\
 * ---4---2
 * --/-\-/-\
 * -3--1....
 * 小根堆：任一一棵子树的头节点是当前子树最小节点
 * -----1
 * ----/-\
 * ---2---4
 * --/-\-/-\
 * -3--5....
 * <p>
 * i位置的父节点是 (i-1)/2 ，它的左子节点是 2*i+1，右子节点是 2*i+2
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/27 19:17
 */
public class Code06_HeapSort implements Code00_Sort {

    /**
     * 在数组index位置加入一个节点，该节点如果大于父节点需要上浮
     *
     * @param arr   数组
     * @param index 新节点加入位置
     */
    private void heapInsert(int[] arr, int index) {
        // 循环结束有两种条件：1)不大于父节点 2)上浮到root节点，既index=0
        while (arr[index] > arr[(index - 1) / 2]) {
            // 交换当前节点和父节点
            swap(arr, index, (index - 1) / 2);
            // 指针上浮，继续向上搜索
            index = (index - 1) / 2;
        }
    }

    /**
     * 在数组index位置加入一个节点，该节点如果小于较大子节点需要下沉
     *
     * @param arr      数组
     * @param index    新节点加入位置
     * @param heapSize 当前堆的大小
     */
    private void heapify(int[] arr, int index, int heapSize) {
        int left = 2 * index + 1;
        // 循环结束条件：没有左子节点，那自然就没有右子节点，也就不用下沉
        while (left < heapSize) {
            // 找到较大的子节点下标，注：有左子节点不代表一定有右子节点
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left]
                    ? left + 1 : left;
            // 循环结束条件：最大子节点不大于当前节点的话就不用下沉了
            if (arr[index] >= arr[largest]) break;
            // 交换当前节点和最大子节点
            swap(arr, index, largest);
            // 指针下沉，继续向下搜索
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 构建一个大根堆的数据结构
     */
    private class BigRootHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        BigRootHeap(int size) {
            heap = new int[size];
            limit = size;
            heapSize = 0;
        }

        public void push(int value) {
            if (heapSize == limit) throw new RuntimeException("堆已满");
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public int pop() {
            if (heapSize == 0) throw new RuntimeException("堆为空");
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return heap[heapSize];
        }
    }

    /**
     * 构建一个小根堆的数据结构
     */
    private class SmallRooTHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        SmallRooTHeap(int size) {
            heap = new int[size];
            limit = size;
            heapSize = 0;
        }

        public void push(int value) {
            if (heapSize == limit) throw new RuntimeException("堆已满");
            heapInsert(heap, value);
            heapSize++;
        }

        public int pop() {
            if (heapSize == 0) throw new RuntimeException("堆为空");
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return heap[heapSize];
        }

        private void heapInsert(int[] arr, int index) {
            while (arr[index] < arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int[] arr, int index, int heapSize) {
            int left = 2 * index + 1;
            while (left < heapSize) {
                int lesser = left + 1 < heapSize && arr[left + 1] < arr[left]
                        ? left + 1 : left;
                // 循环结束条件：最大子节点不大于当前节点的话就不用下沉了
                if (arr[index] <= arr[lesser]) break;
                // 交换当前节点和最大子节点
                swap(arr, index, lesser);
                // 指针下沉，继续向下搜索
                index = lesser;
                left = 2 * index + 1;
            }
        }
    }

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        // 将整个数组调整为大根堆
        for (int i = 0; i < arr.length; i++) {
            // 循环的复杂度是N，insert复杂度为树高logN，所以整体复杂度N*logN
            heapInsert(arr, i);
            // 建堆的过程如果用下沉的方式则复杂度为N
            // heapify(arr, arr.length - 1 - i, arr.length);
        }
        // 起始将整个数组看作堆
        int heapSize = arr.length;
        // 每次选出剩余部分的最大数
        for (int i = 0; i < arr.length; i++) {
            // 每次循环将堆顶的数与最后一个数交换，那么最后一个数就是当前堆中最大数
            // 然后将heapSize减小，也就是数组看成堆的位置减小，将堆与交换过的数分开
            swap(arr, 0, --heapSize);
            // 循环的复杂度是N，heapify复杂度为树高logN，所以整体复杂度N*logN
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，
     * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
     * <p>
     * 请选择一个合适的排序策略，对这个数组进行排序。
     *
     * @param arr 要排序的数组
     * @param k   移动距离
     */
    private void sortDistanceLessK(int[] arr, int k) {
        if (arr == null || arr.length < 2 || k < 1) return;
        // 将[i，i+k]的k+1个数压入小根堆，每次弹出堆顶，然后在压入i+k+1位置，循环往复
        PriorityQueue<Integer> smallRootHeap = new PriorityQueue<>(k + 1);
        for (int i = 0; i < arr.length && i < k + 1; i++) {
            smallRootHeap.add(arr[i]);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = smallRootHeap.poll();
            if (i + k + 1 < arr.length) {
                smallRootHeap.add(arr[i + k + 1]);
            }
        }
    }
}
