package com.acs.algo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Collection;

public class SetImp {

	/**
	 * Finds duplicate.
	 * @param data
	 * @return
	 */
	public static int findDuplicate(int[] data) {
		Set<Integer> set = new HashSet<>();
		for (int i: data) {
			// If set can not add then return found duplicate.
			if(!set.add(i)) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	
	/**
	 * Finds top N unique elems
	 * @param data
	 * @param n
	 * @return
	 */
	public static <T extends Comparable<T>> Collection<T> topNUniqueElems(List<T> data, int n) {
		SortedSet<T> set = new TreeSet<>();
		for (int i = 0; i < data.size(); i++) {
			if (set.size() > n) {
				set.remove(set.first());
			}
			set.add(data.get(i));
		}
		return set;
	}
		
	
	public static void main(String[] args) {
		System.out.println("Testing setImp tests");
		assert findDuplicate(new int[]{1,2,3,3}) == 3 : "Should be 3";
		System.out.println("Top N unique elems");
		topNUniqueElems(Arrays.asList(3, 1, 6, 7, 7, 3, 5, 8, 5, 6), 3).forEach(System.out::println);
		System.out.println("Done setImp tests");
	}
}
