package com.acs.algo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.awt.Point;
import java.util.AbstractMap;
import java.util.ArrayList;
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
	 * Lots of space.
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
	
	/**
	 * Minimal space
	 * @param intervals
	 * @return
	 */
	public static int coveredRange2(List<Interval> intervals) {
		int totalDistance = 0;
		Interval last = new Interval(0, 0);
		intervals.sort( (a, b) -> a.start - b.start);
		for (Interval i : intervals) {
			if (i.start > last.end) {
				totalDistance += (i.end - i.start);
				last = i;
			} else if (i.start == last.start && i.end > last.end) {
				totalDistance += (i.end - last.end);
				last = i;
			} else if (i.start < last.end && i.end > last.end) {
				totalDistance += (i.end - last.end);
				last = i;
			}
		}
		return totalDistance;
	}
	
	/**
	 * Maze is filled with player ids.
	 * Find points sprted by the biggest same player area.
	 * @author asingh
	 *
	 */
	static class MazeSolver {
		private int[][] maze;
		
		private Set<Point> getTeamPoints(int player) {
			Set<Point> teamPoints = new HashSet<> ();
			for (int y = 0; y < maze.length; y++) {
				for (int x = 0; x < maze[0].length; x++) {
					if (maze[y][x] == player) {
						teamPoints.add(new Point(y, x));
					}
				}
			}
			//System.out.println("debug total points found : " + teamPoints);
			return teamPoints;
		}
		
		public MazeSolver(int size) {
			Random random = new Random();
			int[][] maze = new int[size][size];
			for(int i = 0; i<size; i++) {
				for(int j = 0; j<size; j++) {
					maze[i][j] = random.nextInt(size);
				}
			}
			this.maze = maze;
		}
		
		public MazeSolver(int[][] maze) {
			this.maze = maze;
		}
		
		/**
		 * Get points sorted by largest same player area.
		 * @param player
		 * @return
		 */
		public Collection<Point> getMaxHitPoints(int player) {
			Set<Point> points = getTeamPoints(player);
			List<Set<Point>> areas = new ArrayList<>();
			while (!points.isEmpty()) {
				Point cur = getAny(points);
				Set<Point> connected = new HashSet<>();
				neighbors(cur, connected, player);
				//System.out.println("debug neighbors points found : " + connected + " for " + cur);
				areas.add(connected);
				points.removeAll(connected);
			}
			// Sorting based upon area size.
			areas.sort( (a, b) -> (b.size() - a.size()));
			List<Point> result = new ArrayList<>();
			//System.out.println("debug areas " + areas.size());
			// Take any point in that set.
			for (Set<Point> set : areas) {
				result.add(getAny(set));
			}
			return result;
		}
		
		/**
		 * Return any random point from collection.
		 * @param points
		 * @return
		 */
		private Point getAny(Collection<Point> points) {
			for (Point p : points) {
				return p;
			}
			return null;
		}
		
		/**
		 * DFS to all 4 directions (up, down, left and right) for same player.
		 * @param cur
		 * @param visited
		 * @param player
		 */
		private void neighbors(Point cur, Set<Point> visited, int player) {
			if (!visited.contains(new Point(cur.y, cur.x))) {
				if (maze[cur.y][cur.x] == player) {
					visited.add(new Point(cur.y, cur.x));
				}
				if (cur.y >= 1 && player == maze[cur.y-1][cur.x]) {
					neighbors(new Point(cur.y - 1, cur.x), visited, player);
				}
				if (cur.x >= 1 && player == maze[cur.y][cur.x-1]) {
					neighbors(new Point(cur.y, cur.x - 1), visited, player);
				}
				if (cur.y < maze.length-1 && player == maze[cur.y+1][cur.x]) {
					neighbors(new Point(cur.y + 1, cur.x), visited, player);
				}
				if (cur.x < maze[0].length-1 && player == maze[cur.y][cur.x+1]) {
					neighbors(new Point(cur.y, cur.x+1), visited, player);
				}
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("==== MAZE ====");
			for(int i = 0; i < maze.length; i++) {
				sb.append("\n");
				for(int j = 0; j < maze.length; j++) {
					sb.append(maze[i][j]);
				}
			}
			return sb.toString();
		}
		
		/**
		 * Point (y, x) aware of hashCode(), equals() and toString().
		 * @author asingh
		 *
		 */
		static class Point {
			int y, x;
			Point(int y, int x) {
				this.y = y;
				this.x = x;
			}
			
			@Override
			public int hashCode() {
				return x * x + y * y;
			}
			
			@Override
			public boolean equals(Object other) {
				if (other == null || this.getClass() != other.getClass()) {
					return false;
				}
				Point check = (Point) other;
				return this.x == check.x && this.y == check.y;
			}
			@Override
			public String toString() {
				return "{Y:"+y+",X:"+x+"}";
			}
		}
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
		assert coveredRange2(Arrays.asList(new Interval(3, 4), new Interval(3, 6))) == 3;
		

		int mazeSize = 5, mazePlayer = 1;
		System.out.println("For maze size : " + mazeSize);
		System.out.println("For maze player : " + mazePlayer);
		MazeSolver ms = new MazeSolver(mazeSize);
		System.out.println(ms);
		System.out.println(ms.getMaxHitPoints(mazePlayer));
		
		System.out.println("Done setImp tests");
	}
}
