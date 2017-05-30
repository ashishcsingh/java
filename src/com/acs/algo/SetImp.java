package com.acs.algo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.AbstractMap;
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
	
	
	/**
	 * Modification of the below problem:
	 * https://www.careercup.com/question?id=5651199228379136
	 * Test should return in O(1) whether there are two nums or 1 num that totals the num.
	 */
	public static interface Summable {
		public void store (int num);
		public boolean test(int num);
	}
	
	public static class SummableImpl implements Summable {
		Set<Integer> set = new HashSet<>();
		@Override
		public void store(int num) {
			set.add(num);
		}
		
		@Override
		public boolean test(int num) {
			for (Integer val : set) {
				if(set.contains(num - val)) {
					return true;
				}
			}
			return false;
		}
	}
	
	static class Interval {
		int start;
		int end;
		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	
	/**
	 * Merges pairs on insert.
	 * https://www.careercup.com/question?id=5766696197423104
	 * @param pairs
	 * @param insert
	 */
	void mergedPairs(List<Entry<Integer, Integer>> pairs, Entry<Integer, Integer> insert) {
		NavigableSet<Entry<Integer, Integer>> set = new TreeSet<>((a, b) -> a.getKey() - b.getKey());
		set.addAll(pairs);
		Entry<Integer, Integer> left = null, right = null;
		for (Entry<Integer, Integer> e : pairs) {
			if (left == null && insert.getKey() > e.getKey() && insert.getKey() < e.getValue()) {
				left = e;
			}
			if (insert.getValue() > e.getKey() && insert.getValue() < e.getValue()) {
				right = e;
			}
		}
		// Covers many spans.
		if (left != null && right != null) {
			set.removeAll(set.subSet(left, right));
			set.remove(right);
			set.add(new AbstractMap.SimpleEntry<Integer, Integer>(left.getKey(), right.getValue()));
			return;
		}
		// The new insert is an isolated entry.
		pairs.add(insert);
	}
	
	
	/**
	 * Covered range.
	 * @param intervals
	 * @return
	 */
	public static int coveredRange(List<Interval> intervals) {
		Set<Integer> data = new HashSet<>();
		for (Interval i : intervals) {
			for (int val = i.start; val < i.end; val++) {
				data.add(val);
			}
		}
		return  data.size();
	}
	
	public static void main(String[] args) {
		System.out.println("Testing setImp tests");
		assert findDuplicate(new int[]{1,2,3,3}) == 3 : "Should be 3";
		System.out.println("Top N unique elems");
		topNUniqueElems(Arrays.asList(3, 1, 6, 7, 7, 3, 5, 8, 5, 6), 3).forEach(System.out::println);
		Summable sums = new SummableImpl();
		sums.store(4);
		sums.store(5);
		sums.store(6);
		assert !sums.test(7);
		assert sums.test(8);
		assert sums.test(9);
		assert sums.test(10);
		assert coveredRange(Arrays.asList(new Interval(3, 4), new Interval(3, 6))) == 3;
		System.out.println("Done setImp tests");
	}
}
