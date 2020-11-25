package com.taozi.sort;

import java.util.Stack;

/**
 * 快速排序，最坏N^2、最优、平均时间复杂度N*logN，空间复杂度logN，不稳定排序
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/25 19:20
 */
public class Code05_QuickSort implements Code00_Sort {

    /**
     * 给定一个数组arr，和一个整数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边
     */
    private class Code05_QuickSort_TwoPartition {

        /**
         * 数组arr范围[left，right]，以arr[right]为目标数
         * <p>
         * 设置数组左侧区域为小于等于num区域，命名为小于等于区域，使用指针less代表区域右侧，初始值为left - 1
         * 使用index表示当前数位置，范围[left,right - 1]，初始值为left
         * 如果arr[index] <= num，arr[index]和arr[less + 1]交换，less++，index++
         * 如果arr[index] > num，p不变，index++
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param arr   数组
         * @param left  左边界
         * @param right 有边界
         * @return 分区位置
         */
        private int partition(int[] arr, int left, int right) {
            if (left > right) {
                return -1;
            }
            if (left == right) {
                return left;
            }
            int less = left - 1;
            int index = left;
            while (index < right) {
                if (arr[index] <= arr[right]) {
                    swap(arr, index, ++less);
                }
                index++;
            }
            swap(arr, right, ++less);
            return less;
        }
    }

    /**
     * 荷兰国旗问题(netherlands flag)：给定一个数组arr，和一个整数num
     * 请把小于num的数放在数组的左边，等于num的数放在中间，大于num的数放在数组的右边
     */
    private class Code05_QuickSort_ThreePartition {

        /**
         * 数组arr范围[left，right]，以arr[right]为目标数
         * <p>
         * 设置数组左侧区域为小于num区域，命名为小于区域，使用指针less代表区域右侧，初始值为left - 1
         * 设置数组右侧区域为大于num区域，命名为大于区域，使用指针more代表区域左侧，初始值为right
         * 使用index表示当前数位置，范围[left,right - 1]，初始值为left
         * 如果arr[index] < num，arr[index]和arr[less + 1]交换，less++，index++
         * 如果arr[index] = num，less、more不变，index++
         * 如果arr[index] > num，arr[index]和arr[more - 1]交换，more--，index不动
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param arr   数组
         * @param left  左边界
         * @param right 有边界
         * @return 等于目标数区域的两侧下标
         */
        private int[] partition(int[] arr, int left, int right) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right;
            int index = left;
            while (index < more) {
                // 当前数小于目标数的时候，把当前数归拢到小于区域
                if (arr[index] < arr[right]) {
                    swap(arr, index++, ++less);
                }
                // 当前数大于目标数的时候，把当前数归拢到大于区域
                else if (arr[index] > arr[right]) {
                    swap(arr, index, --more);
                    // index不动是因为换过来的数还不知道啥样
                }
                // 当前数等于目标数的时候，直接遍历下一个
                else {
                    index++;
                }
            }
            swap(arr, right, more);
            return new int[]{less + 1, more};
        }
    }

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    /**
     * 以右边界数在[left，right]范围上分区，然后递归到left = right为止
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     */
    private void process(int[] arr, int left, int right) {
        if (left >= right) return;
        // 随机快排，随机性的存在才能使时间复杂度收敛到n * log n，空间复杂度收敛到log n
        // 如果没有这个随机性则考虑最坏时间复杂度n^2，空间复杂度n
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        int[] partition = new Code05_QuickSort_ThreePartition().partition(arr, left, right);
        process(arr, left, partition[0] - 1);
        process(arr, partition[1] + 1, right);
    }

    /**
     * 迭代的方式实现随机快排
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     */
    private void processIterator(int[] arr, int left, int right) {
        // 处理哪个范围
        class Op {
            int l;
            int r;

            public Op(int l, int r) {
                this.l = l;
                this.r = r;
            }
        }
        Code05_QuickSort_ThreePartition threePartition = new Code05_QuickSort_ThreePartition();

        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        int[] partition = threePartition.partition(arr, left, right);
        Stack<Op> stack = new Stack<>();
        // 第一次分区后，将两个子任务压入栈中
        stack.push(new Op(left, partition[0] - 1));
        stack.push(new Op(partition[1] + 1, right));
        // 如果栈不为空，循环处理，直到栈内任务为空
        while (!stack.isEmpty()) {
            // 弹出栈顶子任务
            Op op = stack.pop();
            // 判断是否还能继续划分出子任务
            if (op.l < op.r) {
                swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
                // 继续划分区域
                partition = threePartition.partition(arr, op.l, op.r);
                // 将子任务压栈
                stack.push(new Op(op.l, partition[0] - 1));
                stack.push(new Op(partition[1] + 1, op.r));
            }
        }
    }
}
