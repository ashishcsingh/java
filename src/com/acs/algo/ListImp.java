package com.acs.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class ListImp {
	public static int[] arrayUnique(int[] data) {
		Set<Integer> set = new TreeSet<Integer>();
		for (Integer i : data) {
			set.add(i);
		}
		int[] result = new int[set.size()];
		int i = 0;
		for (int v : set) {
			result[i++] = v;
		}
		return result;
	}

	public static int percentileVal(int[] data, double percentile) {
		if (data == null || (percentile < 0 && percentile > 0)) {
			throw new IllegalArgumentException();
		}
		return data[(int) (Math.ceil(percentile * (data.length - 1)))];
	}

	/**
	 * arr: 1...N Finds missing number by computing sum and substracting from n
	 * * n+1/2
	 * 
	 * @param arr
	 * @return
	 */
	public static int findMissingNumber(int[] arr) {
		int foundSum = 0;
		if (arr == null) {
			throw new IllegalArgumentException("Null array");
		}
		for (int i : arr) {
			foundSum += i;
		}
		return (arr.length + 1) * (arr.length + 2) / 2 - foundSum;
	}

	/**
	 * Builds random ordered 1 to N numbers in an array.
	 * 
	 * @param count
	 * @return
	 */
	public static int[] populateRandomNumber(int count) {
		if (count < 1) {
			throw new IllegalArgumentException("count less than 0");
		}
		int[] data = new int[count];
		for (int i = 0; i < count; i++) {
			data[i] = i + 1;
		}
		for (int i = count - 1; i >= 0; i--) {
			// Find random between 0 to i-1.
			int random = new Random().nextInt(i);
			// swap.
			int temp = data[i];
			data[i] = data[random];
			data[random] = temp;
		}
		return data;
	}

	/**
	 * Computes sum of continious range in an array.
	 * 
	 * @param data
	 * @return
	 */
	public static int maxSumContiniousSubArray(int[] data) {
		int[] sums = new int[data.length];
		int sum = 0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
			sum = Math.max(0, sum);
			sums[i] = sum;
		}
		return Arrays.stream(sums).max().getAsInt();
	}

	/**
	 * Count total bits set in data[]
	 * 
	 * @param data
	 * @return
	 */
	public static int countBits(int[] data) {
		int sum = 0;
		for (int d : data) {
			for (int i = 0; i < 32; i++) {
				sum += (d & (1 << i)) > 0 ? 1 : 0;
			}
		}
		return sum;
	}

	/**
	 * All elems on left is smaller than pivot (center) and all elems on right
	 * is bigger than pivot. Returns swapped out point.
	 * 
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 */
	private static <T extends Comparable<T>> int partition(T[] data, int start, int end) {
		int pivotLoc = (start + end) / 2;
		T pivot = data[pivotLoc];
		while (start < end) {
			while (data[start].compareTo(pivot) < 1) {
				start++;
			}
			while (data[end].compareTo(pivot) > -1) {
				end--;
			}
			if (start < end) {
				T temp = data[start];
				data[start] = data[end];
				data[end] = temp;
				start++;
				end--;
			}
		}
		return start;
	}

	/**
	 * Implement quick sort.
	 * 
	 * @param data
	 * @param start
	 * @param end
	 */
	public static <T extends Comparable<T>> void quickSort(T[] data, int start, int end) {
		if (start < end) {
			int pivotLoc = partition(data, start, end);
			quickSort(data, start, pivotLoc - 1);
			quickSort(data, pivotLoc, end);
		}
	}

	/**
	 * Reverses number passed. -123 => -321
	 * 
	 * @param num
	 * @return
	 */
	public static int reverseNum(int num) {
		boolean neg = false;
		if (num < 0) {
			neg = true;
			num *= -1;
		}
		int[] digits = new int[10];
		int i = 0;
		while (num > 0) {
			digits[i++] = num % 10;
			num /= 10;
		}
		int result = 0;
		for (int j = 0; j < i; j++) {
			result *= 10;
			result += digits[j];
		}
		return neg ? -result : result;
	}

	public static int waterStored(int[] val) {
		int[] leftMax = new int[val.length];
		int[] rightMax = new int[val.length];
		// Find wall height from left.
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < val.length; i++) {
			if (max < val[i]) {
				max = val[i];
			}
			leftMax[i] = max;
		}
		// Find wall height from right.
		max = Integer.MIN_VALUE;
		for (int i = val.length - 1; i >= 0; i--) {
			if (max < val[i]) {
				max = val[i];
			}
			rightMax[i] = max;
		}
		int waterSum = 0;
		for (int i = 0; i < val.length; i++) {
			waterSum += Math.max(0, Math.min(leftMax[i], rightMax[i]) - val[i]);
		}
		return waterSum;
	}

	/**
	 * Count bulbs that are on when n people each flip 1 to n times.
	 * @param n
	 * @return
	 */
	public static int bulbFlipper(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("cannot accept less than 1");
		}
		boolean bulbs[] = new boolean[n];
		for (int i = 1; i <= n; i++) {
			flipWithGap(bulbs, i);
		}
		int count = 0;
		for (int i = 0; i < bulbs.length; i++) {
			if (bulbs[i]) {
				count++;
			}
		}
		return count;
	}

	private static void flipWithGap(boolean bulbs[], int gap) {
		for (int i = gap - 1; i < bulbs.length; i += gap) {
			bulbs[i] = !bulbs[i];
		}
	}
	
	public static class Segment {
		public float start, end;
		Segment(float start, float end) {
			this.start = start;
			this.end = end;
		}
		Segment() {}
	};
	
	/**
	 * Total watched time for a movie.
	 * @param list  Time segments of movie
	 * @return total time watched.
	 */
	public static float totalWatchedTime(List<Segment> list) {
		if (list == null || list.size() < 1) {
			throw new IllegalArgumentException();
		}
		Collections.sort(list, (s1, s2) -> (int) (s1.start - s2.start));
		List<Segment> output = new ArrayList<>();
		output.add(list.get(0));
		for(int i = 1; i < list.size(); i++) {
			Segment s1 = output.get(output.size() - 1);
			Segment s2 = list.get(i);
			// Check merge
			if (s1.end > s2.start) {
				output.set(output.size() - 1, new Segment(Math.min(s1.start, s2.start), Math.max(s1.end, s2.end)));
			} else {
				output.add(s2);
			}
		}
		float total = 0;
		for(Segment s : output) {
			total += (s.end - s.start);
		}
		return total;
	}
	
	/**
	 * Simple Binary search.
	 * @param data
	 * @param search
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean binarySearch(int[] data, int search, int start, int end) {
		while (start < end) {
			int mid = (start + end) / 2;
			if (data[mid] < search) {
				start = mid + 1;
			} else if (data[mid] > search) {
				end = mid - 1;
			} else {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// Test arrayUnique()
		System.out.println("Start: Test list puzzles");
		assert Arrays.equals(arrayUnique(new int[] { 1, 2, 3, 5, 6, 1, 2, 4 }), new int[] { 1, 2, 3, 4, 5, 6 });
		assert Arrays.equals(arrayUnique(new int[] { 1, 2 }), new int[] { 1, 2 });
		assert Arrays.equals(arrayUnique(new int[] {}), new int[] {});
		assert Arrays.equals(arrayUnique(new int[] { 1, 1, 1, 1, 1 }), new int[] { 1 });

		// Test percentileVal()
		assert percentileVal(new int[] { 1, 2, 3, 4, 5 }, 0.5) == 3;
		assert percentileVal(new int[] { 1, 2, 3, 4, 5 }, 1.0) == 5;

		// Test missing number.
		assert findMissingNumber(new int[] { 1, 2, 3, 5 }) == 4;
		assert findMissingNumber(new int[] { 1 }) == 2;

		// Test max sum of continous sub array.
		assert maxSumContiniousSubArray(new int[] { 1, -2, 4, 5, -6 }) == 9;
		assert maxSumContiniousSubArray(new int[] { 5, -2, 4, 1, -6 }) == 8;

		// Test count of bits.
		assert countBits(new int[] { 1, 2, 3 }) == 4;
		assert countBits(new int[] { 1, 2, 3, 3 }) == 6;

		// Test quick sort.
		Integer[] data = new Integer[] { 4, 3, 2, 8 };
		quickSort(data, 0, 3);
		assert Arrays.equals(data, new Integer[] { 2, 3, 4, 8 });
		System.out.println("Reverse number : " + -321 + " is : " + reverseNum(-321));

		assert bulbFlipper(5) == 2;
		
		assert binarySearch(new int[]{1, 2, 3, 4, 5 }, 3, 0, 5) : "Should be found";
		assert binarySearch(new int[]{1, 2, 3, 4, 7 }, 5, 0, 5) == false : "Should not be found";
	
		assert (int) totalWatchedTime(Arrays.asList(new Segment(0.0f, 5.0f),
				new Segment(3.0f, 8.0f), new Segment(9.0f, 12.0f))) == (int) 11.0f;
		
		// No assert failure means all work fine.
		System.out.println("Finish: Test list puzzles.");
	}
}
