package com.xangqun.springcloud.component.base.algorithm;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * 归并排序算法
 *
 * @param <E>
 * @author laixiangqun
 * @date 2009-12-11
 */
public class MergeSort<E extends Comparable<E>> extends Sort<E> {

    /**
     * 排序算法的实现，对数组中指定的元素进行排序
     *
     * @param array 待排序的数组
     * @param from  从哪里开始排序
     * @param end   排到哪里
     * @param c     比较器
     */
    @Override
    public void sort(E[] arr, int from, int end, Comparator<E> c) {
        partition(arr, from, end, c);
    }

    /**
     * 递归划分数组
     *
     * @param arr
     * @param from
     * @param end
     * @param c    void
     */
    private void partition(E[] arr, int from, int end, Comparator<E> c) {
        //划分到数组只有一个元素时才不进行再划分
        if (from < end) {
            //从中间划分成两个数组
            int mid = (from + end) / 2;
            partition(arr, from, mid, c);
            partition(arr, mid + 1, end, c);
            //合并划分后的两个数组
            merge(arr, from, end, mid, c);
        }
    }

    /**
     * 数组合并，合并过程中对两部分数组进行排序
     * 前后两部分数组里是有序的
     *
     * @param arr
     * @param from
     * @param end
     * @param mid
     * @param c    void
     */
    private void merge(E[] arr, int from, int end, int mid, Comparator<E> c) {
        E[] tmpArr = (E[]) Array.newInstance(arr[0].getClass(), end - from + 1);
        int tmpArrIndex = 0;//指向临时数组
        int part1ArrIndex = from;//指向第一部分数组
        int part2ArrIndex = mid + 1;//指向第二部分数组

        //由于两部分数组里是有序的，所以每部分可以从第一个元素依次取到最后一个元素，再对两部分
        //取出的元素进行比较。只要某部分数组元素取完后，退出循环
        while ((part1ArrIndex <= mid) && (part2ArrIndex <= end)) {
            //从两部分数组里各取一个进行比较，取最小一个并放入临时数组中
            if (c.compare(arr[part1ArrIndex], arr[part2ArrIndex]) < 0) {
                //如果第一部分数组元素小，则将第一部分数组元素放入临时数组中，并且临时数组指针
                //tmpArrIndex下移一个以做好下次存储位置准备，前部分数组指针part1ArrIndex
                //也要下移一个以便下次取出下一个元素与后部分数组元素比较
                tmpArr[tmpArrIndex++] = arr[part1ArrIndex++];
            } else {
                //如果第二部分数组元素小，则将第二部分数组元素放入临时数组中
                tmpArr[tmpArrIndex++] = arr[part2ArrIndex++];
            }
        }
        //由于退出循环后，两部分数组中可能有一个数组元素还未处理完，所以需要额外的处理，当然不可
        //能两部分数组都有未处理完的元素，所以下面两个循环最多只有一个会执行，并且都是大于已放入
        //临时数组中的元素
        while (part1ArrIndex <= mid) {
            tmpArr[tmpArrIndex++] = arr[part1ArrIndex++];
        }
        while (part2ArrIndex <= end) {
            tmpArr[tmpArrIndex++] = arr[part2ArrIndex++];
        }

        //最后把临时数组拷贝到源数组相同的位置
        System.arraycopy(tmpArr, 0, arr, from, end - from + 1);
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer[] intgArr = {5, 9, 1, 4, 1, 2, 6, 3, 8, 0, 7};
        MergeSort<Integer> insertSort = new MergeSort<Integer>();
        Sort.testSort(insertSort, intgArr);
        Sort.testSort(insertSort, null);
    }
}
