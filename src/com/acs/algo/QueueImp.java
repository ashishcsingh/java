package com.acs.algo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class QueueImp {
	
	/**
	 * Finds top N elements.
	 * @param data
	 * @param n
	 * @return
	 */
	public static int[] topN(int[] data, int n) {
		// By default it builds min heap based priority queue.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<>(n + 1);
		for(int i: data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the smallest with the new item.
				pq.offer(i);
				pq.poll();
			}
		}
		int[] result = new int[n];
		for(int i=0; i<n; i++) {
			result[i] = pq.poll();
		}
		return result;
	}
	
	/**
	 * Finds the top n elements out of passed data.
	 * By using PriorityQueue (offer, poll) min heap
	 * @param data
	 * @param n
	 * @return
	 */
	public static List<Integer> topNElem(List<Integer> data, int n) {
		// By default it builds min heap based priority queue.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<Integer> (n + 1);
		for(Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the smallest with the new item.
				pq.offer(i);
				pq.poll();
			}
		}
		List<Integer> output = new ArrayList<Integer> (n);
		for (int i = 0; i < n; i++) {
			output.add(pq.poll());
		}
		return output;
	}
	
	/**
	 * Finds the bottom n elements out of passed data.
	 * By using PriorityQueue (offer, poll) min heap
	 * @param data
	 * @param n
	 * @return
	 */
	public static List<Integer> bottomNElem(List<Integer> data, int n) {
		// Max heap, b - a.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<Integer> (n + 1, (a, b) -> b - a);
		for(Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the largest with new item.
				pq.offer(i);
				pq.poll();
			}
		}
		List<Integer> output = new ArrayList<Integer> (n);
		for (int i = 0; i < n; i++) {
			output.add(pq.poll());
		}
		return output;
	}
	
	public static class Point implements Comparable<Point> {
		private int x, y, z;
		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public int compareTo(Point o) {
			float distanceThis = this.x * this.x +
					this.y * this.y +
					this.z * this.z;
			float distanceO = o.x * o.x +
					o.y * o.y +
					o.z * o.z;
			return (int) (distanceO - distanceThis);
		}
		public String toString() {
			return "(" + this.x + ", " + this.y + ", " + this.z + ")"; 
		}
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o==null || o.getClass()!=this.getClass()) {
				return false;
			}
			Point other = (Point) o;
			return other.x == this.x && other.y == this.y && other.z == this.z;
		}
	}
	public static List<Point> largestNPoints(List<Point> data, int n) {
		PriorityQueue<Point> pq = new PriorityQueue<Point>(n + 1);
		for(Point point: data) {
			if(pq.size() < n) {
				pq.offer(point);
			} else {
				pq.offer(point);
				pq.poll();
			}
		}
		List<Point> output = new ArrayList<Point>();
		for(int i=0; i < n; i++) {
			output.add(pq.poll());
		}
		return output;
	}
	
	/**
	 * Find K Largest elements.
	 * 
	 */
	public static List<Integer> topKElements(List<Integer> data, int k) {
		NavigableSet<Integer> set = new TreeSet<>( (a,b) -> a - b);
		for (int i = 0; i < k; i++) {
			set.add(data.get(i));
		}
		for (int i = 0; i < k; i++) {
			set.add(data.get(i));
		}
		for (int i = k; i < data.size(); i++) {
			set.pollFirst();
			set.add(data.get(i));
		}
		return new ArrayList<>(set);
	}
	
	
	/**
	 * Problem:
		Inside a list the words are sorted 
		list1 -->aaa,bbb,ddd,xyxz,... 
		list2-->bbb,ccc,ccc,hkp,.. 
		list3> ddd,eee,,ffff,lmn,.. 
		Solution:
		1. while PriorityQueue<Entry<String, Integer>> is not empty
		2. polling the smallest and replacing it with the same list's next elem.
	 * @param listStrings
	 */
	public static void printSmallestString(List<List<String>> listStrings) {
		Queue<Entry<String, Integer>> pq = new PriorityQueue<>(listStrings.size(),
				(a, b) -> a.getKey().compareTo(b.getKey()));
		for (int i=0; i<listStrings.size(); i++) {
			List<String> list = listStrings.get(i);
			if (list != null && list.size() > 0) {
				pq.offer(new AbstractMap.SimpleEntry<String, Integer>(list.get(0), i));
			} else {
				// This ensure all lists have at least one element.
				throw new IllegalArgumentException("Cannot accept null list or empty lists.");
			}
		}
		
		int[] listIndex = new int[listStrings.size()];
		System.out.println();
		while(!pq.isEmpty()) {
			Entry<String, Integer> entry = pq.poll();
			System.out.print(entry.getKey() + " ");
			int minList = entry.getValue();
			int minListNextIndex = ++listIndex[minList];
			// Insert next element in min list.
			if (minListNextIndex < listStrings.get(minList).size()) {
				pq.offer(new AbstractMap.SimpleEntry<String, Integer>(listStrings.get(minList)
						.get(minListNextIndex), minList));
			}
		}
	}
	
	/**
	 * Round queue that serves data.
	 * @author ashish
	 *
	 */
	public static class RoundQueue {
		final private byte[] data;
		final private int size;
		private int start, length;
		public RoundQueue(int size) {
			this.size = size;
			data = new byte[size];
			this.length = this.start = 0;
		}
		/**
		 * Enqueue data from input of desiredLen via FIFO (queue)
		 * @param input
		 * @param desiredLen
		 * @return Max possible data that can be inserted.
		 */
		public int enqueue(byte[] input, int desiredLen) {
			if ((start + length) % size >= start) {
				//  [  start  --> length]
				int first = Math.min(desiredLen, size - (start + length));
				int second = 0;
				System.arraycopy(input, 0, data, start + length, first);
				if (desiredLen - first > 0) {
					second = Math.min(desiredLen - first, start);
					System.arraycopy(input, first, data, 0, second);
				}
				length = length + first + second;
				return first + second;
			} else {
				// [  -> length    start -> ]
				int first = Math.min(desiredLen, start - (start + length) % size);
				System.arraycopy(input, 0, data, (start + length) % size, first);
				length = length + first;
				return first;
			}
		}
		
		/**
		 * Dequeue data to output of desiredLen via FIFO (queue)
		 * @param output
		 * @param desiredLen
		 * @return Max possible data that can be removed.
		 */
		public int dequeue(byte[] output, int desiredLen) {
			if ((start + length) % size < start) {
				// [  -> length    start -> ]
				int first = Math.min(desiredLen, size - start);
				int second = 0;
				System.arraycopy(data, start, output, 0, first);
				if (desiredLen - first > 0) {
					second = Math.min(desiredLen - first, (start + length) % size);
					System.arraycopy(data, 0, output, first, second);
				}
				start = (start + first + second) % size;
				return first + second;
			} else {
				// [ start -> length ]
				int first = Math.min(desiredLen, length);
				System.arraycopy(data, start, output, 0, first);
				start = start + first;
				return first;
			}
		}
	}
	
	/**
	 * Using priorityQueue merge K sorted array.
	 * @param lists
	 * @return
	 */
	public static List<Integer> mergeKSortedArrays(Collection<Collection<Integer>> lists) {
		Queue<Integer> queue = new PriorityQueue<>();
		for (Collection<Integer> list : lists) {
			for (Integer item: list) {
				queue.add(item);
			}
		}
		List<Integer> output = new ArrayList<Integer>();
		for (Integer item: queue) {
			output.add(item);
		}
		return output;
	}
	
	
	
	public static void main(String[] args) {
		// Test topN()
		System.out.println("--- Testing topN testing");
		assert Arrays.equals(topN(new int[] {1,2,3,5,6,7,8}, 5), new int[] {3,5,6,7,8});
		assert topKElements(Arrays.asList(1,2,3,5,6,7,8), 5).equals(Arrays.asList(3,5,6,7,8));
		System.out.println("Testing topNElem");
		List<Integer> data = new ArrayList<>();
		for(int i = 0; i< 100; i++) {
			data.add(i);
		}
		List<Integer> output = topNElem(data, 5);
		assert Objects.equals(output, Arrays.asList(new Integer[]{95, 96, 97, 98, 99}));
		for(Integer i: output) {
			System.out.print(i + " ");
		}
		System.out.println("\nTesting bottomNElem");
		output = bottomNElem(data, 5);
		assert Objects.equals(output, Arrays.asList(new Integer[]{4, 3, 2, 1, 0}));
		for(Integer i: output) {
			System.out.print(i + " ");
		}
		System.out.println("\nTesting closestNPoints");
		List<Point> points = new ArrayList<>();
		for(int i = 0; i< 100; i++) {
			points.add(new Point(i, i, i));
		}
		List<Point> outputPoints = largestNPoints(points, 5);
		for(Point point: outputPoints) {
			System.out.print(point + " ");
		}
		

		List<List<String>> lists = new ArrayList<>();
		lists.add(Arrays.asList("aaa", "bbb", "ddd", "xyxz"));
		lists.add(Arrays.asList("bbb","ccc","ccc","hkp"));
		lists.add(Arrays.asList("ddd","eee","ffff","lmn"));
		
		printSmallestString(lists);
		
		assert Objects.equals(outputPoints, Arrays.asList(new Point[]{new Point(4,4,4), new Point(3,3,3),
				new Point(2,2,2), new Point(1,1,1), new Point(0,0,0)}));
		
		RoundQueue rq = new RoundQueue(5);
		rq.enqueue("abcdefghijk".getBytes(), 8);
		byte[] fromQ =  new byte[8];
		rq.dequeue(fromQ, 8);
		System.out.println("String : " + new String(fromQ));
		assert "abcde".equals(new String("abcde"));
		
		Collection<Collection<Integer>> lists2 = new ArrayList<Collection<Integer>>();
		lists2.add(Arrays.asList(1, 2, 5, 10));
		lists2.add(Arrays.asList(3, 6, 9, 11));
		lists2.add(Arrays.asList(4, 7, 8, 12));
		
		Collection<Integer> output2 = mergeKSortedArrays(lists2);
		System.out.println("merged k sorted should be 1 to 12: ");
		System.out.println(output2);
		
		System.out.println("---  Done test");
		
	}

}
