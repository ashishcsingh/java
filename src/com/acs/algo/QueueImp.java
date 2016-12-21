package com.acs.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class QueueImp {
	
	/**
	 * Finds top N elements.
	 * @param data
	 * @param n
	 * @return
	 */
	public static int[] topN(int[] data, int n) {
		PriorityQueue<Integer> pq = new PriorityQueue<>(n + 1, (a, b) -> a - b);
		for(int i: data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
				pq.poll();
				pq.offer(i);
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
		PriorityQueue<Integer> pq = new PriorityQueue<Integer> (n + 1);
		for(Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
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
		PriorityQueue<Integer> pq = new PriorityQueue<Integer> (n + 1, (i1, i2) -> {
				return i2 - i1;
		});
		for(Integer i : data) {
			if (pq.size() < n) {
				pq.offer(i);
			} else {
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
	public static List<Point> closestNPoints(List<Point> data, int n) {
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
	
	public static void main(String[] args) {
		// Test topN()
		System.out.println("Testing topN testing");
		assert Arrays.equals(topN(new int[] {1,2,3,5,6,7,8}, 5), new int[] {3,5,6,7,8});
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
		List<Point> outputPoints = closestNPoints(points, 5);
		for(Point point: outputPoints) {
			System.out.print(point + " ");
		}
		assert Objects.equals(outputPoints, Arrays.asList(new Point[]{new Point(4,4,4), new Point(3,3,3),
				new Point(2,2,2), new Point(1,1,1), new Point(0,0,0)}));
	}

}
