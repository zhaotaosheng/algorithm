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

    /**
     * 在一个数组中，一个数左边比它小的数的总和，叫数的小和，
     * 所有数的小和累加起来，叫数组小和。求数组小和。
     */
    private static class Code04_MergeSort_SmallSum {

        public int smallSum(int[] arr) {
            if (arr == null || arr.length < 2) {
                return 0;
            }
            return process(arr, 0, arr.length - 1);
        }

        /**
         * 在每次做merge时产生的最小和累加起来就是数组最小和
         *
         * @param arr   数组
         * @param left  左边界
         * @param right 右边界
         * @return 最小和
         */
        private int process(int[] arr, int left, int right) {
            if (left == right) return 0;
            int middle = left + ((right - left) >> 1);
            // 左侧数组merge时产生的最小和 + 右侧数组merge时产生的最小和 + 当前两侧数组merge产生的最小和
            return process(arr, left, middle)
                    + process(arr, middle + 1, right)
                    + merge(arr, left, middle, right);
        }

        /**
         * 每次merge排序时，落右数组数据时不用记录，说明此时左侧比它小的都记录完了
         *
         * @param arr    数组
         * @param left   左侧数组的左边界
         * @param middle 左侧数组的右边界，middle+1是右侧数组的左边界
         * @param right  右侧数组的的右边界
         * @return 此次merge的最小和
         */
        private int merge(int[] arr, int left, int middle, int right) {
            int[] help = new int[right - left + 1];
            int index = 0;
            int p1 = left;
            int p2 = middle + 1;
            // 用来保存最小和
            int result = 0;
            while (p1 <= middle && p2 <= right) {
                // 当p1位置数小于p2位置数时，因为右侧有序，则此时p2到right的位置数都大于p1位置数
                // 那么这个数arr[p1]就应该乘以数量(right - p2 + 1)加到result中
                result += arr[p1] < arr[p2] ? arr[p1] * (right - p2 + 1) : 0;
                // p1位置数等于p2位置数时，因为要判断p1位置数是否比p2后边的小，所以此时应当落p2
                help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= middle) {
                help[index++] = arr[p1++];
            }
            while (p2 <= right) {
                help[index++] = arr[p2++];
            }
            for (int i = 0; i < help.length; i++) {
                arr[left + i] = help[i];
            }
            return result;
        }
    }

    /**
     * 在一个数组中，任何一个前面的数a，和任何一个后面的数b，
     * 如果(a,b)是降序的，就称为逆序对。返回数组中所有的逆序对个数。
     */
    private static class Code04_MergeSort_ReversePair {

        public int reversePair(int[] arr) {
            if (arr == null || arr.length < 2) {
                return 0;
            }
            return process(arr, 0, arr.length - 1);
        }

        /**
         * 在每次做merge时产生逆序对的个数
         *
         * @param arr   数组
         * @param left  左边界
         * @param right 右边界
         * @return 逆序对个数
         */
        private int process(int[] arr, int left, int right) {
            if (left == right) return 0;
            int middle = left + ((right - left) >> 1);
            // 左侧数组merge时产生的逆序对个数 + 右侧数组merge时产生的逆序对个数 + 当前两侧数组merge产生的逆序对个数
            return process(arr, left, middle)
                    + process(arr, middle + 1, right)
                    + merge(arr, left, middle, right);
        }

        /**
         * 每次merge排序时，落右数组数据时不用记录，说明此时左侧数都比它小或相等
         *
         * @param arr    数组
         * @param left   左侧数组的左边界
         * @param middle 左侧数组的右边界，middle+1是右侧数组的左边界
         * @param right  右侧数组的的右边界
         * @return 此次merge产生的逆序对个数
         */
        private int merge(int[] arr, int left, int middle, int right) {
            int[] help = new int[right - left + 1];
            int index = help.length - 1;
            int p1 = middle;
            int p2 = right;
            // 用来保存逆序对个数
            int result = 0;
            // 两侧数组从右往左移动指针
            while (p1 >= left && p2 >= middle + 1) {
                // 当p1位置数大于p2位置数时，说明p1位置数大于 middle+1~p2 所有的数
                // 所以逆序对的个数应该是(p2 - middle)
                result += arr[p1] > arr[p2] ? (p2 - middle) : 0;
                // p1和p2位置数比较，谁大落谁，从大往小方向落
                // 如果相等应该落右侧数，因为要计算小于左边的数，则左侧不能动
                help[index--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
            }
            while (p1 >= left) {
                help[index--] = arr[p1--];
            }
            while (p2 >= middle + 1) {
                help[index--] = arr[p2--];
            }
            for (int i = 0; i < help.length; i++) {
                arr[left + i] = help[i];
            }
            return result;
        }
    }

    /**
     * 在一个数组中，对于每个数num，求有多少个后面的数 * 2依然<num，求总个数
     */
    private class Code04_MergeSort_BiggerThanRightTwice {

        public int biggerThanRightTwice(int[] arr) {
            if (arr == null || arr.length < 2) {
                return 0;
            }
            return process(arr, 0, arr.length - 1);
        }

        /**
         * 在每次做merge时产生符合结果的个数
         *
         * @param arr   数组
         * @param left  左边界
         * @param right 右边界
         * @return 符合结果的个数
         */
        private int process(int[] arr, int left, int right) {
            if (left == right) return 0;
            int middle = left + ((right - left) >> 1);
            // 左侧数组merge时产生的逆序对个数 + 右侧数组merge时产生的逆序对个数 + 当前两侧数组merge产生的逆序对个数
            return process(arr, left, middle)
                    + process(arr, middle + 1, right)
                    + merge(arr, left, middle, right);
        }

        /**
         * 每次merge排序时，遍历左侧数组同时滑动右侧数组指针，找到符合条件的最远下标
         *
         * @param arr    数组
         * @param left   左侧数组的左边界
         * @param middle 左侧数组的右边界，middle+1是右侧数组的左边界
         * @param right  右侧数组的的右边界
         * @return 此次merge产生的结果个数
         */
        private int merge(int[] arr, int left, int middle, int right) {
            // 用来保存结果个数
            int result = 0;
            // 目前囊括进来的数，是从[M+1,windowR)
            int windowR = middle + 1;
            // 遍历左侧数组元素
            for (int i = left; i <= middle; i++) {
                // 遍历右侧得到滑动最远的下标
                while (windowR <= right && arr[i] > (arr[windowR] << 1)) {
                    // 滑动右侧数组下标
                    windowR++;
                }
                result += windowR - middle + 1;
            }

            Code04_MergeSort.this.merge(arr, left, middle, right);
            return result;
        }
    }

    /**
     * 给定一个数组arr，两个整数lower和upper，返回arr中有多少个子数组的累加和在[lower,upper]范围上
     * 举例：{-14，2，8，-21，25，0，12，-4，4，10}，[-10，10]
     * 暴力方法：
     * (0,0)=-14 (1,1)=2   (2,2)=8  (3,3)=-21
     * ----------(0,1)=-12 (1,2)=10 (3,3)=-13
     * --------------------(0,2)=-4 (3,3)=-9
     * -----------------------------(3,3)=-25......
     * 复杂度：枚举所有子数组(N ~ 1的等差数列和)序列O(n^2)，每个序列遍历求和O(n)，总复杂度O(n^3)
     */
    private static class Code04_MergeSort_CountOfRangeSum {

        /**
         * 优化方法前置知识：
         * 1.数组i ~ j位置的和，等于0 ~ j位置的和减去0 ~ i-1位置的和
         * --例子(3,5)=(0,5)-(0,2)在例题中就是4 = 0 - -4
         * 2.数组0 ~ j位置的和为x，那么以j为结尾的子数组中，如果(0,i)值在[x-upper,x-lower]上，那么(i+1,j)就符合
         * --例子(0,10)=22，如果(0,i)值在[12,32]之间，那么(i+1,10)子数组就会在[-10，10]，比如i = 1，(0,1) = 12
         * 3.前缀和数组，prefixSum[i] = (0,i)，前缀和数组i位置表示原数组0位置到i位置的和
         *
         * @param arr   数组
         * @param lower 累加和下界
         * @param upper 累加和上界
         * @return 在界限内子数组个数
         */
        public static int countRangeSum(int[] arr, int lower, int upper) {
            if (arr == null || arr.length < 1) {
                return 0;
            }
            long[] prefixSum = new long[arr.length];
            prefixSum[0] = arr[0];
            // 构建前缀和数组
            for (int i = 1; i < arr.length; i++) {
                prefixSum[i] = prefixSum[i - 1] + arr[i];
            }
            // 在0 ~ arr.length - 1范围上搜索
            return process(prefixSum, 0, arr.length - 1, lower, upper);
        }

        /**
         * 在[left，right]范围内进行排序与搜索，它可以分为左子问题和右子问题以及左右返回数组合并的过程
         * 根据master公式可知：T(n)=2 * T(n/2) + O(n) 得到复杂度为O(n * log n)
         *
         * @param prefixSum 前缀和数组
         * @param left      搜索范围下界
         * @param right     搜索范围上界
         * @param lower     累加和下界
         * @param upper     累加和上界
         * @return 在界限内子数组个数
         */
        public static int process(long[] prefixSum, int left, int right, int lower, int upper) {
            // base case，归并分割的最小粒度，一元组。
            // 代表prefixSum的[0,0][1,1][2,2][3,3][4,4]....这种子序列，也就是arr的(0,0)(0,1)(0,2)(0,3)(0,4)....
            if (left == right) {
                return prefixSum[left] >= lower && prefixSum[left] <= upper ? 1 : 0;
            }
            int middle = left + ((right - left) >> 1);
            return process(prefixSum, left, middle, lower, upper)
                    + process(prefixSum, middle + 1, right, lower, upper)
                    + merge(prefixSum, left, middle, right, lower, upper);
        }

        /**
         * 在一元组合并到二元组再到四元组过程中，首先每次的右侧数组是不会重复的，并且是全部都会覆盖到的，什么意思呢？一元组是base case，
         * 一元组合并到二元组时，它判断的是prefixSum[0|1][2|3][4|5][6|7]...也就是arr的(1,1)(3,3)(5,5)(7,7)...
         * 二元组合并到四元组时，它判断的是prefixSum[0,1|2,3][4,5|6,7]...也就是arr的(1,2)(2,2)(1,3)(2,3)(5,6)(6,6)(5,7)(6,7)...
         * 实际就是左组所有项到右组每项的累加和(i+1,j)(i+2,j)(i+3,j)...(m+1,j)其中i代表左数组任意位置，j代表右数组任意位置，m代表中点
         * <p>
         * 1.根据前置2的知识我们知道，在merge时右侧的每一个数如果能在左侧找到满足前置2条件的数，那这个左侧数就是一种答案
         * 2.对每一个右侧数来讲他想要找到的[x-upper,x-lower]是固定的，并且merge时左右两侧各自有序，
         * --那么左侧可以维护两个指针来形成一个窗口范围，窗口内的数符合范围，并且两个指针只需要向右滑动即可
         * 3.我们还需要一个指针indexRight记录右侧走到的位置，这个指针只需要向右滑动，滑过的位置代表统计完的，而且右侧是有序的
         * --那么indexRight + 1的[x-upper,x-lower]一定会比indexRight的[x`-upper,x`-lower]上下界都要大，也就是
         * --它的窗口一定在前一个窗口的中后方，肯定不会在前边。所以此时对应的左侧指针不需要向左归位，继续右滑即可
         * <p/>
         * 综上merge的过程复杂度是O(n)
         *
         * @param prefixSum 前缀和数组
         * @param left      搜索范围下界
         * @param middle    搜索范围中点
         * @param right     搜索范围上界
         * @param lower     累加和下界
         * @param upper     累加和上界
         * @return 在merge过程中落在累加和界限内子数组个数
         */
        private static int merge(long[] prefixSum, int left, int middle, int right, int lower, int upper) {
            // 最终要返回的结果
            int result = 0;
            // 左侧的窗口[windowLeft,windowRight)，都从左侧数组开头开始滑动
            int windowLeft = left;
            int windowRight = left;
            // 右侧记录的指针，最开始从右侧数组的开头开始滑动
            int indexRight = middle + 1;
            // 当用来记录的指针没有滑出边界时
            while (indexRight <= right) {
                // 根据前置2定义出来的满足题意的左侧数应在的范围
                long prefixSumLower = prefixSum[indexRight] - upper;
                long prefixSumUpper = prefixSum[indexRight] - lower;
                // 如果窗口右侧没有出界，并且该窗口数还在范围内，则右滑
                while (windowRight <= middle && prefixSum[windowRight] <= prefixSumUpper) {
                    // 为什么没有prefixSum[windowRight] >= prefixSumLower判断? 因为小于prefixSumLower的时候窗口左右侧都会一起滑
                    // 大于这个数的时候窗口左侧是不会动的，如果加上这个条件就可能出现windowRight < windowLeft的情况
                    windowRight++;
                }
                // 如果窗口左侧没有出界，并且该窗口数不在范围内，则右滑
                while (windowLeft <= middle && prefixSum[windowLeft] < prefixSumLower) {
                    windowLeft++;
                }
                // 左右窗口中间的数就是当前右侧指针指向的数的所有答案
                result += windowRight - windowLeft;
                // 滑动右侧指针
                indexRight++;
            }

            // 常规的归并排序
            long[] help = new long[right - left + 1];
            int index = 0;
            int p1 = left;
            int p2 = middle + 1;
            while (p1 <= middle && p2 <= right) {
                help[index++] = prefixSum[p1] <= prefixSum[p2] ? prefixSum[p1++] : prefixSum[p2++];
            }
            while (p1 <= middle) {
                help[index++] = prefixSum[p1++];
            }
            while (p2 <= right) {
                help[index++] = prefixSum[p2++];
            }
            for (int i = 0; i < help.length; i++) {
                prefixSum[left + i] = help[i];
            }
            return result;
        }
    }
}
