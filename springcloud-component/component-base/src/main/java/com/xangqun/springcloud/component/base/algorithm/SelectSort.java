package com.xangqun.springcloud.component.base.algorithm;

import java.util.Comparator;

/**
 * 简单选择排序算法
 *
 * @param <E>
 * @author laixiangqun
 * @date 2009-12-5
 */
public class SelectSort<E extends Comparable<E>> extends Sort<E> {

    /**
     * 排序算法的实现，对数组中指定的元素进行排序
     *
     * @param array 待排序的数组
     * @param from  从哪里开始排序
     * @param end   排到哪里
     * @param c     比较器
     */
    @Override
    public void sort(E[] array, int from, int end, Comparator<E> c) {
        int minlIndex;//最小索引
        /*
         * 循环整个数组（其实这里的上界为 array.length - 1 即可，因为当 i= array.length-1
         * 时，最后一个元素就已是最大的了，如果为array.length时，内层循环将不再循环），每轮假设
         * 第一个元素为最小元素，如果从第一元素后能选出比第一个元素更小元素，则让让最小元素与第一
         * 个元素交换
         */
        for (int i = from; i <= end; i++) {
            minlIndex = i;//假设每轮第一个元素为最小元素
            //从假设的最小元素的下一元素开始循环
            for (int j = i + 1; j <= end; j++) {
                //如果发现有比当前array[smallIndex]更小元素，则记下该元素的索引于smallIndex中
                if (c.compare(array[j], array[minlIndex]) < 0) {
                    minlIndex = j;
                }
            }

            //先前只是记录最小元素索引，当最小元素索引确定后，再与每轮的第一个元素交换
            swap(array, i, minlIndex);
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer[] intgArr = {5, 9, 1, 4, 1, 2, 6, 3, 8, 0, 7};
        SelectSort<Integer> insertSort = new SelectSort<Integer>();
        Sort.testSort(insertSort, intgArr);
        Sort.testSort(insertSort, null);
    }
}