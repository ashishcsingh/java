package com.acs.algo;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class QueueImp {

	/**
	 * Finds top N elements.
	 * 
	 * @param data
	 * @param n
	 * @return
	 */
	public static int[] topN(int[] data, int n) {
		// By default it builds min heap based priority queue.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<>(n + 1);
		for (int i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the smallest with the new item.
				pq.offer(i);
				pq.poll();
			}
		}
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = pq.poll();
		}
		return result;
	}

	/**
	 * Finds the top n elements out of passed data. By using PriorityQueue
	 * (offer, poll) min heap
	 * 
	 * @param data
	 * @param n
	 * @return
	 */
	public static List<Integer> topNElem(List<Integer> data, int n) {
		// By default it builds min heap based priority queue.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<Integer>(n + 1);
		for (Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the smallest with the new item.
				pq.offer(i);
				pq.poll();
			}
		}
		List<Integer> output = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			output.add(pq.poll());
		}
		return output;
	}

	/**
	 * Finds the bottom n elements out of passed data. By using PriorityQueue
	 * (offer, poll) min heap
	 * 
	 * @param data
	 * @param n
	 * @return
	 */
	public static List<Integer> bottomNElem(List<Integer> data, int n) {
		// Max heap, b - a.
		// n + 1, to prevent loss of any of the top N elements in polling.
		Queue<Integer> pq = new PriorityQueue<Integer>(n + 1, (a, b) -> b - a);
		for (Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				// Replace the largest with new item.
				pq.offer(i);
				pq.poll();
			}
		}
		List<Integer> output = new ArrayList<Integer>(n);
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
			float distanceThis = this.x * this.x + this.y * this.y + this.z * this.z;
			float distanceO = o.x * o.x + o.y * o.y + o.z * o.z;
			return (int) (distanceO - distanceThis);
		}

		public String toString() {
			return "(" + this.x + ", " + this.y + ", " + this.z + ")";
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			Point other = (Point) o;
			return other.x == this.x && other.y == this.y && other.z == this.z;
		}
	}

	public static List<Point> largestNPoints(List<Point> data, int n) {
		PriorityQueue<Point> pq = new PriorityQueue<Point>(n + 1);
		for (Point point : data) {
			if (pq.size() < n) {
				pq.offer(point);
			} else {
				pq.offer(point);
				pq.poll();
			}
		}
		List<Point> output = new ArrayList<Point>();
		for (int i = 0; i < n; i++) {
			output.add(pq.poll());
		}
		return output;
	}

	/**
	 * Find K Largest elements.
	 * 
	 */
	public static List<Integer> topKElements(List<Integer> data, int k) {
		NavigableSet<Integer> set = new TreeSet<>((a, b) -> a - b);
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
	 * Problem: Inside a list the words are sorted list1 -->aaa,bbb,ddd,xyxz,...
	 * list2-->bbb,ccc,ccc,hkp,.. list3> ddd,eee,,ffff,lmn,.. Solution: 1. while
	 * PriorityQueue<Entry<String, Integer>> is not empty 2. polling the
	 * smallest and replacing it with the same list's next elem.
	 * 
	 * @param listStrings
	 */
	public static void printSmallestString(List<List<String>> listStrings) {
		Queue<Entry<String, Integer>> pq = new PriorityQueue<>(listStrings.size(),
				(a, b) -> a.getKey().compareTo(b.getKey()));
		for (int i = 0; i < listStrings.size(); i++) {
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
		while (!pq.isEmpty()) {
			Entry<String, Integer> entry = pq.poll();
			System.out.print(entry.getKey() + " ");
			int minList = entry.getValue();
			int minListNextIndex = ++listIndex[minList];
			// Insert next element in min list.
			if (minListNextIndex < listStrings.get(minList).size()) {
				pq.offer(new AbstractMap.SimpleEntry<String, Integer>(listStrings.get(minList).get(minListNextIndex),
						minList));
			}
		}
	}

	/**
	 * Round queue that serves data.
	 * 
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
		 * 
		 * @param input
		 * @param desiredLen
		 * @return Max possible data that can be inserted.
		 */
		public int enqueue(byte[] input, int desiredLen) {
			if ((start + length) % size >= start) {
				// [ start --> length]
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
				// [ -> length start -> ]
				int first = Math.min(desiredLen, start - (start + length) % size);
				System.arraycopy(input, 0, data, (start + length) % size, first);
				length = length + first;
				return first;
			}
		}

		/**
		 * Dequeue data to output of desiredLen via FIFO (queue)
		 * 
		 * @param output
		 * @param desiredLen
		 * @return Max possible data that can be removed.
		 */
		public int dequeue(byte[] output, int desiredLen) {
			if ((start + length) % size < start) {
				// [ -> length start -> ]
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
	 * 
	 * @param lists
	 * @return
	 */
	public static List<Integer> mergeKSortedArrays(Collection<Collection<Integer>> lists) {
		Queue<Integer> queue = new PriorityQueue<>();
		for (Collection<Integer> list : lists) {
			for (Integer item : list) {
				queue.add(item);
			}
		}
		List<Integer> output = new ArrayList<Integer>();
		for (Integer item : queue) {
			output.add(item);
		}
		return output;
	}

	/**
	 * Parses line to RPN
	 * 
	 * @param line
	 * @param operators
	 * @param operands
	 */
	private static void parseReversePolishNotation(String line, Deque<Character> operators, Deque<Double> operands) {
		String[] words = line.split("\\s");
		boolean expectedNum = true;
		for (String word : words) {
			if (expectedNum) {
				try {
					Double operand = Double.parseDouble(word);
					operands.push(operand);
				} catch (NumberFormatException nfe) {
					expectedNum = false;
				}
			}
			if (!expectedNum) {
				if (word.matches("[+-/*]")) {
					operators.push(word.charAt(0));
				} else {
					throw new IllegalStateException();
				}
			}
		}
	}

	/**
	 * Input : 4 5 3 + / perform: 5 + 3 = 8 4 / 8 = 0.5
	 * https://www.careercup.com/question?id=4906033149378560
	 * 
	 * @param line
	 * @return
	 */
	public static Double computeReversePolishNotation(String line) {
		float result = 0;
		// Parse out line
		Deque<Double> numStack = new ArrayDeque<>();
		Deque<Character> OpsStack = new ArrayDeque<>();
		parseReversePolishNotation(line, OpsStack, numStack);
		for (char operator : OpsStack) {
			double val1, val2;
			switch (operator) {
			case '+':
				val2 = numStack.pop();
				val1 = numStack.pop();
				numStack.push(val1 + val2);
				break;
			case '-':
				val2 = numStack.pop();
				val1 = numStack.pop();
				numStack.push(val1 - val2);
				break;
			case '*':
				val2 = numStack.pop();
				val1 = numStack.pop();
				numStack.push(val1 * val2);
				break;
			case '/':
				val2 = numStack.pop();
				val1 = numStack.pop();
				numStack.push(val1 / val2);
				break;
			}
		}
		return numStack.pop();
	}

	/**
	 * Check if data exists in a shifted sorted list.
	 * https://www.careercup.com/question?id=5747740665446400
	 * 
	 * @param num
	 * @param data
	 * @return
	 */
	public static boolean shiftedBinarySearch(List<Integer> num, int data) {
		// If empty.
		if (num == null || num.size() == 0) {
			return false;
		}
		int start = 0, end = num.size() - 1;
		while (start <= end) {
			int middle = (start + end) / 2;
			if (num.get(middle) == data) {
				return true;
			}
			if ((num.get(start) <= data && num.get(middle) > data)
					|| (num.get(start) >= data && num.get(middle) < data)) {
				end = middle - 1;
			} else {
				start = middle + 1;
			}
		}
		return false;
	}

	static class Point2D {
		float x, y;

		Point2D(float x, float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point2D{" + x + ", " + y + "}";
		}
	}

	/**
	 * findNearestPoints https://www.careercup.com/question?id=4758558331633664
	 * 
	 * @param points
	 * @param target
	 * @param count
	 * @return
	 */
	public static Collection<Point2D> findNearestPoints(Collection<Point2D> points, Point2D target, int count) {
		Queue<Point2D> pq = new PriorityQueue<>(count + 1, (a, b) -> {
			float distanceA = (target.x - a.x) * (target.x - a.x) + (target.y - a.y) * (target.y - a.y);
			float distanceB = (target.x - b.x) * (target.x - b.x) + (target.y - b.y) * (target.y - b.y);
			return (int) ((int) distanceB - distanceA);
		});
		for (Point2D point : points) {
			if (count >= pq.size()) {
				pq.add(point);
			} else {
				pq.add(point);
				pq.remove();
			}
		}
		return pq;
	}

	/**
	 * Basic queue for storing generic data.
	 * 
	 * @author asingh
	 *
	 * @param <T>
	 */

	public static interface BasicQueue<T> {
		public boolean add(T val);

		public T remove();
	}

	public static class BasicQueueImp<T> implements BasicQueue<T> {
		private int capacity, start = 0, length = 0;
		private List<T> data;

		public BasicQueueImp(int capacity) {
			this.capacity = capacity;
			data = new ArrayList<>(capacity);
		}

		public boolean add(T val) {
			if (length == capacity) {
				return false;
			}
			int loc = (start + length) % capacity;
			if (data.size() > loc) {
				data.set(loc, val);
			} else {
				data.add(val);
			}
			length = length + 1;
			return true;
		}

		public T remove() {
			if (length == 0) {
				throw new IllegalStateException("Queue empty");
			}
			T result = data.get(start);
			// This avoids dangling references.
			data.set(start, null);
			start = (start + 1) % capacity;
			length--;
			return result;
		}
	}

	public static interface BlockingQueue<T> {
		void enqueue(T val) throws InterruptedException;

		public T dequeue() throws InterruptedException;
	}

	// enqueue and dequeue do not block each other.
	public static class LinkedBlockingQueue<T> implements BlockingQueue<T> {
		private List<T> queue = new LinkedList<>();
		private int capacity = 16;

		LinkedBlockingQueue(int capacity) {
			this.capacity = capacity;
		}

		@Override
		public synchronized void enqueue(T val) throws InterruptedException {
			while (queue.size() == capacity) {
				wait();
			}
			if (queue.isEmpty()) {
				notifyAll();
			}
			queue.add(val);
		}

		@Override
		public synchronized T dequeue() throws InterruptedException {
			while (queue.isEmpty()) {
				wait();
			}
			if (queue.size() == capacity) {
				notifyAll();
			}
			return queue.remove(0);
		}
	}

	// enqueue and dequeue do not block each other.
	public static class ArrayBlockingQueue<T> implements BlockingQueue<T> {
		private List<T> queue = new ArrayList<>();
		private int capacity = 16;

		ArrayBlockingQueue(int capacity) {
			this.capacity = capacity;
		}

		@Override
		public synchronized void enqueue(T val) throws InterruptedException {
			while (queue.size() == capacity) {
				wait();
			}
			if (queue.isEmpty()) {
				notifyAll();
			}
			queue.add(val);
		}

		@Override
		public synchronized T dequeue() throws InterruptedException {
			while (queue.isEmpty()) {
				wait();
			}
			if (queue.size() == capacity) {
				notifyAll();
			}
			return queue.remove(0);
		}
	}

	public static void main(String[] args) {
		// Test topN()
		System.out.println("--- Testing topN testing");
		assert Arrays.equals(topN(new int[] { 1, 2, 3, 5, 6, 7, 8 }, 5), new int[] { 3, 5, 6, 7, 8 });
		assert topKElements(Arrays.asList(1, 2, 3, 5, 6, 7, 8), 5).equals(Arrays.asList(3, 5, 6, 7, 8));
		System.out.println("Testing topNElem");
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			data.add(i);
		}
		List<Integer> output = topNElem(data, 5);
		assert Objects.equals(output, Arrays.asList(new Integer[] { 95, 96, 97, 98, 99 }));
		for (Integer i : output) {
			System.out.print(i + " ");
		}
		System.out.println("\nTesting bottomNElem");
		output = bottomNElem(data, 5);
		assert Objects.equals(output, Arrays.asList(new Integer[] { 4, 3, 2, 1, 0 }));
		for (Integer i : output) {
			System.out.print(i + " ");
		}
		System.out.println("\nTesting closestNPoints");
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			points.add(new Point(i, i, i));
		}
		List<Point> outputPoints = largestNPoints(points, 5);
		for (Point point : outputPoints) {
			System.out.print(point + " ");
		}

		List<List<String>> lists = new ArrayList<>();
		lists.add(Arrays.asList("aaa", "bbb", "ddd", "xyxz"));
		lists.add(Arrays.asList("bbb", "ccc", "ccc", "hkp"));
		lists.add(Arrays.asList("ddd", "eee", "ffff", "lmn"));

		printSmallestString(lists);

		assert Objects.equals(outputPoints, Arrays.asList(new Point[] { new Point(4, 4, 4), new Point(3, 3, 3),
				new Point(2, 2, 2), new Point(1, 1, 1), new Point(0, 0, 0) }));

		RoundQueue rq = new RoundQueue(5);
		rq.enqueue("abcdefghijk".getBytes(), 8);
		byte[] fromQ = new byte[8];
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

		// Reverse Polish Notation
		// 4 + 5 + 6 - 5
		assert computeReversePolishNotation("4 5 6 5 + + -") == 10.0F;
		// 4 + 5 * 1
		assert computeReversePolishNotation("4 5 1 + *") == 9.0F;
		// 4 * 5 + 1
		assert computeReversePolishNotation("1 4 5 + *") == 21.0F;

		assert shiftedBinarySearch(Arrays.asList(3, 4, 5, 1, 2), 3);
		assert shiftedBinarySearch(Arrays.asList(3, 4, 5, 1, 2), 2);

		Collection<Point2D> point2Ds = Arrays.asList(new Point2D(3, 4), new Point2D(10, 10), new Point2D(1, 1),
				new Point2D(2, 2));
		System.out.println(findNearestPoints(point2Ds, new Point2D(1, 1), 2));

		BasicQueue<Integer> q = new BasicQueueImp<>(5);
		System.out.println("Adding 1 : " + q.add(1));
		System.out.println("Adding 2 : " + q.add(2));
		System.out.println("Adding 3 : " + q.add(3));
		System.out.println("Adding 4 : " + q.add(4));
		System.out.println("Adding 5 : " + q.add(5));
		System.out.println("Adding 6 : " + q.add(6));
		System.out.println("Removing 1 : " + q.remove());
		System.out.println("Removing 2 : " + q.remove());
		System.out.println("Removing 3 : " + q.remove());
		System.out.println("Adding 6 : " + q.add(6));
		System.out.println("Removing 4 : " + q.remove());

		BlockingQueue<Integer> bq = new LinkedBlockingQueue<>(3);
		// 3 enqueue followed by mix of enqueue and dequeue operations
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					System.out.println("Enqueing " + i);
					bq.enqueue(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					System.out.println("Dequeuing " + bq.dequeue());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		System.out.println("---  Done test");
	}
}
