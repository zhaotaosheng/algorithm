package com.taozi.sort;

import java.util.*;

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

    /**
     * 最大线段重合问题（用堆的实现）
     * 给定很多线段，每个线段都有两个数[start, end]，
     * 表示线段开始位置和结束位置，左右都是闭区间
     * 规定：
     * 1）线段的开始和结束位置一定都是整数值
     * 2）线段重合区域的长度必须>=1
     * 返回线段最多重合区域中，包含了几条线段
     *
     * @param arr 二维线段数组
     * @return 最大重叠数量
     */
    private int LineOverlapMax(int[][] arr) {
        // 辅助用的线段类
        class Line {
            private int start;
            private int end;

            public Line(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
        // 将二位数组转为一维线段数组
        Line[] lines = new Line[arr.length];
        for (int i = 0; i < arr.length; i++) {
            lines[i] = new Line(arr[i][0], arr[i][1]);
        }
        // 根据线段的开始位置排序
        Arrays.sort(lines, Comparator.comparingInt(line -> line.start));
        // 构建一个小根堆用来存储结束位置
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 最大重叠线段数量
        int res = 0;
        for (int i = 0; i < lines.length; i++) {
            Line line = lines[i];
            // 当堆顶的数(某个线段的结束位置)小于等于当前线段起始位置，说明这两个线段肯定不会重合，将它弹出
            // 直到堆中剩下的都是可以与当前线段重合的线段，既都可以贯穿当前线段起始位置的线段
            while (!heap.isEmpty() && heap.peek() <= line.start) {
                heap.poll();
            }
            // 将当前线段结束位置放入堆中
            heap.add(line.end);
            // 当前堆的大小就是贯穿当前开始位置的线段数量
            res = Math.max(res, heap.size());
        }
        return res;
    }

    /**
     * 加强堆，对于系统提供的堆功能更丰富与强大，系统提供的堆通常只提供弹、入，仅仅能操作堆顶，堆内部都是黑盒
     * 比如我不知道堆内有哪些元素，如何删除堆内某个元素，堆内某个元素的权重变了该怎么调整，通过增加反向索引表来辅助实现
     *
     * @param <T>
     */
    private class HeapGreater<T> {
        private List<T> heap;
        private Map<T, Integer> index;
        private int heapSize;
        private Comparator<? super T> comparator;

        public HeapGreater(Comparator<T> comparator) {
            this.heap = new ArrayList<>();
            this.index = new HashMap<>();
            this.heapSize = 0;
            this.comparator = comparator;
        }

        // 判断堆里是否有此元素
        public boolean contains(T obj) {
            return index.containsKey(obj);
        }

        // 移除某个元素
        public void remove(T obj) {
            T endElement = heap.get(heapSize - 1);
            Integer index = this.index.get(obj);
            this.index.remove(obj);
            this.heap.remove(--heapSize);
            if (obj != endElement) {
                this.heap.set(index, endElement);
                this.index.put(endElement, index);
                adjust(index);
            }
        }

        // 堆中新增一个元素
        public void push(T obj) {
            this.heap.add(obj);
            this.index.put(obj, heapSize);
            heapInsert(heapSize++);
        }

        // 堆中弹出一个元素
        public T pop() {
            T res = this.heap.get(0);
            remove(res);
            return res;
        }

        // 堆内调整
        private void adjust(int index) {
            heapInsert(index);
            heapify(index);
        }

        private void heapInsert(int index) {
            while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize
                        && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
                if (comparator.compare(heap.get(less), heap.get(index)) >= 0) return;
                swap(less, index);
                index = less;
                left = index * 2 + 1;
            }
        }

        // 交换两个位置的元素
        private void swap(int i, int j) {
            T originI = heap.get(i);
            T originJ = heap.get(j);
            heap.set(i, originJ);
            heap.set(j, originI);
            index.put(originJ, i);
            index.put(originI, j);
        }
    }
}
