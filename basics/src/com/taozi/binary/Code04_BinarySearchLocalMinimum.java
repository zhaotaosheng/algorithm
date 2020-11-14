package com.taozi.binary;

/**
 * 无序数组中，相邻两个数字不相等，找到局部最小
 *
 * @author ZhaoTaoSheng
 * @since 2020/11/14 20:02
 */
public class Code04_BinarySearchLocalMinimum {

    /**
     * 无序数组中，相邻两个数字不相等，找到局部最小
     *
     * @param arr 查找数组
     * @return 位置下标，没有返回 -1
     */
    public static int localMinimum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // 如果数组只有1位或0位置小于1位置时，0位置为局部最小
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        // 如果数组 N-1 位置小于 N-2 位置时，N-1 位置为局部最小
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        // 以上两个条件没满足则说明0位置到1位置是单调下降，N-1 位置到 N-2 位置单调上升
        // 所以在中间必定会存在一个拐点，其值小于左右两侧的值
        // 由于对左右两侧最边界做过判断了所以在 2~N-2范围开始搜索
        int left = 1;
        int right = arr.length - 2;
        int middle;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            // 如果中间值大于左侧值，则形成 middle-1 到 middle 的单调上升，
            // 则只需要在middle的左侧继续搜索
            if (arr[middle - 1] < arr[middle]) {
                right = middle - 1;
            }
            // 如果中间值大于右侧值，则形成 middle 到 middle-1 的单调下降，
            // 则只需要在middle的右侧继续搜索
            else if (arr[middle + 1] < arr[middle]) {
                left = middle + 1;
            }
            // 进到这里说明中间值小于两侧，则它就是局部最小
            else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 生成无序且相邻数值不等的数组
     *
     * @param maxSize  数组最大长度
     * @param maxValue 数组最大值
     * @param seed     数组初始值
     * @return 数组arr
     */
    public static int[] generateRandomArray(int maxSize, int maxValue, int seed) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        if (arr.length == 0) {
            arr = new int[1];
        }
        // 数组0位置初始值
        arr[0] = seed;
        int magic;
        for (int i = 1; i < arr.length; i++) {
            // 生成一个变化数值，取值范围 [-maxValue,1)U(0,maxValue] 的整数
            do {
                magic = (int) ((maxValue + 1) * Math.random())
                        - (int) ((maxValue + 1) * Math.random());
            } while (magic == 0);
            // 由前一位数生成后一位数
            arr[i] = arr[i - 1] + magic;
            // 一旦生成的数字大于最大值，由于arr[i - 1]、magic均小于maxValue
            // 则arr[i] - maxValue 也一定小于 maxValue
            if (arr[i] > maxValue) {
                arr[i] = arr[i] - maxValue;
            }
            // 一旦生成的数字小于于最小值，由于arr[i - 1]、magic均大于maxValue
            // 则arr[i] + maxValue 也一定大于 -maxValue
            else if (arr[i] < -maxValue) {
                arr[i] = arr[i] + maxValue;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int times = 1000;
        int maxSize = 1000;
        int maxValue = 1000;
        int seed = 2;
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue, seed);
            int localMinimum = localMinimum(arr);
            if (localMinimum != -1 &&
                    !((localMinimum == 0 || arr[localMinimum - 1] > arr[localMinimum])
                            && (localMinimum == arr.length - 1 || arr[localMinimum + 1] > arr[localMinimum]))) {
                System.out.println("出现问题");
                return;
            }
        }
        System.out.println("没有问题");
    }
}
