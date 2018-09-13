package com.xangqun.springcloud.component.base.algorithm;


import java.util.Comparator;

/**
 * 冒泡排序算法
 * @author laixiangqun
 * @date 2009-12-9
 *
 * @param <E>
 */
public class BubbleSort<E extends Comparable<E>> extends Sort<E> {

	/**
	 * 排序算法的实现，对数组中指定的元素进行排序
	 * @param array 待排序的数组
	 * @param from 从哪里开始排序
	 * @param end 排到哪里
	 * @param c 比较器
	 */
	@Override
	public void sort(E[] array, int from, int end, Comparator<E> c) {
		//需array.length - 1轮比较
		for (int k = 1; k < end - from + 1; k++) {
			//每轮循环中从最后一个元素开始向前起泡，直到i=k止，即i等于轮次止
			for (int i = end - from; i >= k; i--) {
				//按照一种规则（后面元素不能小于前面元素）排序
				if (c.compare(array[i], array[i - 1]) < 0) {
					//如果后面元素小于了（当然是大于还是小于要看比较器实现了）前面的元素，则前后交换
					swap(array, i, i - 1);
				}
			}
		}
	}

	/**
	* 测试
	* @param args
	*/
	public static void main(String[] args) {
		Integer[] intgArr = { 7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 11, 10 };
		BubbleSort<Integer> sort = new BubbleSort<Integer>();
		BubbleSort.testSort(sort, intgArr);
		BubbleSort.testSort(sort, null);
	}
}
