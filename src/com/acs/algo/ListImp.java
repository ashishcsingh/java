package com.acs.algo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;

public class ListImp {
	public static int[] arrayUnique(int[] data) {
		Set<Integer> set = new HashSet<>();
		for (Integer i : data) {
			set.add(i);
		}
		return set.stream().mapToInt(i -> i).toArray();
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
	 * Print NW to SE
	 * https://www.careercup.com/question?id=5163286157852672
	 * @param data
	 */
	public static void printNWToSE(int[][] data) {
		if (data == null) {
			return;
		}
		int max = data.length + data[0].length;
		int xstart = -data.length + 1, ystart = data.length - 1;
		for (int i = 0; i < max; i++) {
			int x = Math.max(xstart++, 0);
			int y = Math.max(ystart--, 0);
			for (int j = 0; j < max; j++) {
				if (y < data.length && x < data[0].length) {
				  	System.out.print(data[y++][x++] + " ");
				} else {
					System.out.println();
					break;
				}
			}	
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
	
	/**
	 * Max profit over daily stock prices.
	 * @param values
	 * @return
	 */
	public static float maxStockProfit(List<Float> values) {
		if (values.size() < 2) {
			throw new IllegalArgumentException();
		}
		float profit = 0.0F, min = values.get(0);
		for (int i = 1; i<values.size(); i++) {
			profit = Math.max(profit, values.get(i) - min);
			min = Math.min(min, values.get(i));
		}
		return profit;
	}
	
	/**
	 * Toggle lockers
	 *  1 2 3 4 5 6 7 8
	 *  o o o o o o o o
	 *  o c o c o c o c
	 *  o c c c o o o c
	 *  o c c o o o o o
	 *  o c c o c o o o
	 *  o c c o c c o o
	 *  o c c o c c c o
	 *  o c c o c c c c
	 *  
	 * https://www.careercup.com/question?id=6263110739427328
	 * @param args
	 */
	public static void printToggledOutput(int size) {
		boolean arr[] = new boolean[size];
		int inc = 1;
		while (inc <= size) {
			for (int i = inc - 1; i < size; i+=inc) {
				arr[i] = !arr[i];
			}
			inc++;
		}
		for (int i = 0; i < size; i++) {
			if (arr[i]) {
				System.out.print(i + 1 + " , ");
			}
		}
	}
	
	public static void printToggledOutputOtimized(int size) {
		int val = 1;
		while (val * val <= size) {
			System.out.print(val * val + " , ");
			val++;
		}
	}
	
	/**
	 * 6 : 2 * 3
	 * 12: 2 * 6
	 *     2 * 2 * 3
	 *     3 * 4
	 *     3 * 2 * 2
	 *     4 * 3
	 *     6 * 2
	 *     
	 *     https://www.careercup.com/question?id=5759894012559360
	 *     
	 *     1. List<Integer> = getMultFactors()
	 *     2. for i : factors :
	 *     		iLine = String.valueOf(i) + "*"
	 *     		lines
	 *     		for line: lineFactors:
	 *     			lines.add(iLine += "line)
	 *     	 return lines;  
	 * @param num
	 */	
	private static List<String> getLineFactors(int num) {
		List<String> lines = new ArrayList<String>();
		List<Integer> factors = getNumFactors(num);
		if (factors.isEmpty() || factors.size() == 1) {
			return Collections.singletonList(String.valueOf(num));
		}
		for (int i : factors) {
			String iWord = String.valueOf(i) + "*";
			for (String line: getLineFactors(num / i)) {
				lines.add(iWord + line);
			}
		}
		return lines;
	}
	
	/**
	 * https://www.careercup.com/question?id=5689376707182592
	 * @param data
	 * @return
	 */
	public static boolean canFormTriangle(int[] data) {
		if (data.length < 3) {
			return false;
		}
		List<Integer> sides = Ints.asList(data);
		sides.sort( (a, b) -> a - b);
		for (int i = 1; i< data.length - 1; i++) {
			if (data[i-1] + data[i] > data[i + 1]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all mult factors.
	 * @param num
	 * @return
	 */
	private static List<Integer> getNumFactors(int num) {
		List<Integer> factors = new ArrayList<>();
		for(int i = 2 ; i <= num/2; i++) {
			if (num % i == 0) {
				factors.add(i);
			}
		}
		return factors;
	}
	
	private static boolean isTriangle(Integer a, Integer b, Integer c) {
		return a + b > c && a + c > b && b + c > a;
	}
	
	/**
	 * Return all permutations of valid triangle constrained sides.
	 * All abc : a + b > c && a + c > b && b + c > a
	 * @param data
	 * @return
	 */
	public static List<List<Integer>> triangleTriplets(List<Integer> data) {
		List<List<Integer>> output = new ArrayList<>();
		if (data == null || data.size() < 3) {
			return output;
		}
		data.sort( (a, b) -> a - b);
		for (int i = 0; i < data.size(); i++) {
			for (int j = i + 1; j < data.size(); j++) {
				for (int k = j + 1; k < data.size(); k++) {
					int a = data.get(i);
					int b = data.get(j);
					int c = data.get(k);
					if (isTriangle(a, b, c)) {
						output.add(Arrays.asList(a, b, c));
					} else {
						break;
					}
				}
			}
		}
		return output;
	}
	
	/**
	 * Returns the index of the biggest VM
	 * @param vms
	 * @return
	 */
	public static int biggestVm(Collection<Map<String, Float>> vms) {
		if (vms == null || vms.size() == 0) {
			throw new IllegalArgumentException();
		}
		float minCpu = vms.stream().min( (a, b) -> (int) (a.get("cpu") - b.get("cpu"))).get().get("cpu");
		float minMem = vms.stream().min( (a, b) -> (int) (a.get("mem") - b.get("mem"))).get().get("mem");
		int index = 0, curIndex = 0;
		float biggest = 2;
		for (Map<String, Float> vm : vms) {
			float cur = vm.get("cpu") / minCpu + vm.get("mem") / minMem;
			if (biggest < cur) {
				biggest = cur;
				index = curIndex;
			}
			curIndex++;
		}
		return index;
	}

	/**
	 * data[x][y] == true then x follows y.
	 * if this is false then y is not a influencer so skip y.
	 * So complexity is linear.
	 * Influencer is the one that is followed by everyone and does not follow anyone.
	 * @param data
	 * https://www.careercup.com/question?id=6482755168763904
	 * @return
	 */
	public static int influencer(boolean[][] data) {
		if (data == null) {
			return -1;
		}
		boolean[] skipInfluencers = new boolean[data.length];
		for(int leader=0; leader<data.length; leader++) {
			if (skipInfluencers[leader]) {
				continue;
			}
			int follower = 0;
			for (follower = 0; follower < data.length; follower++) {
				if (leader == follower) {
					continue;
				}
				if (!data[follower][leader]) {
					skipInfluencers[leader] = true;
					break;
				}
			}
			if (follower == data.length) {
				return leader;
			}
		}
		return -1;
	}
	
	/**
	 * data[x][y] == true then x follows y.
	 * Influencer is the one that is followed by everyone and does not follow anyone.
	 * @param data
	 * https://www.careercup.com/question?id=6482755168763904
	 * @return
	 */
	public static int influencerPoor(boolean[][] data) {
		if (data == null) {
			return -1;
		}
		for(int i=0; i<data.length; i++) {
			boolean isInfluencer = true;
			for (int j=0; j < data.length; j++) {
				if (i == j) {
					continue;
				}
				if (data[i][j] || !data[j][i]) {
					isInfluencer = false;
					break;
				}
			}
			if (isInfluencer) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Given two arrays of any size, compute sum from back.
	 * https://www.careercup.com/question?id=5631950045839360
	 * @param args
	 */
	public static int[] sumTwoArrays(int[] a, int[] b) {
		if (a.length > b.length) {
			return sumTwoArraysInt(a, b);
		} else {
			return sumTwoArraysInt(b, a);
		}
	}
	
	private static int[] sumTwoArraysInt(int[] a, int[] b) {
		int[] c = new int[a.length + 1];
		for (int i = 1; i <= a.length; i++) {
			c[c.length - i] = a[a.length - i] + (b.length < i ? 0 : b[b.length - i]);
			if (c[c.length - i] > 10) {
				c[c.length - i] -= 10;
				c[c.length - i - 1] += 1;
			}
		}
		return c;
	}
	
	/**
	 * Tasks with time, after every task there is a forcedWait.
	 * Compute max coverage possible after waiting forcedWait after every tasks.
	 *    5 2 3 10, 5 -> 15
	 * @param current
	 * @param covered
	 * @param forcedWait
	 * @param leftWait
	 * @return
	 */
	public static int maxCoverage(int[] tasks, int current, int forcedWait, int leftWait) {
		if (current == tasks.length - 1) {
			return tasks[current];
		}
		int result = 0;
		if (leftWait == 0 && current + 2 < tasks.length) {
			result = tasks[current] + maxCoverage(tasks, current + 2, forcedWait, Math.max(forcedWait - tasks[current + 1], 0));
		}
		if (current + 1 < tasks.length) {
			result = Math.max(result, maxCoverage(tasks, current + 1, forcedWait, Math.max(leftWait - tasks[current], 0)));
		}
		return result;
	}
	
	public static int maxCoverage2(int[] tasks, int current, int forcedWait, int leftWait, Map<Integer, Integer> cache, Deque<Integer> selection) {
		if (current == tasks.length - 1) {
			selection.push(tasks[current]);
			cache.put(current, tasks[current]);
			return tasks[current];
		}
		if (cache.get(current) != null) {
			return cache.get(current);
		}
		int result = 0;
		if (leftWait == 0 && current + 2 < tasks.length) {
			selection.push(tasks[current]);
			result = tasks[current] + maxCoverage2(tasks, current + 2, forcedWait, Math.max(forcedWait - tasks[current + 1], 0), cache, selection);
		}
		if (current + 1 < tasks.length) {
			int temp = maxCoverage2(tasks, current + 1, forcedWait, Math.max(leftWait - tasks[current], 0), cache, selection);
			if (temp > result) {
				// If skipping was better.
				if (result != 0) {
					selection.pop();
				}
				result = temp;
			}
		}
		cache.put(current, result);
		return result;
	}
	
	/**
	 * Given data compute max sum of sub sequence, using tabulation.
	 * @param d
	 * @return
	 */
	public static int maxSumSubSequence(int[] d) {
		int[] sum = new int[d.length];
		for (int i = 0; i < d.length; i++) {
			sum[i] = d[i];
		}
		for (int i = 1; i < d.length; i++) {
			for (int j = 0; j < i; j++) {
				if (d[j] < d[i] && sum[i] < sum[j] + d[i]) {
					sum[i] = sum[j] + d[i];
				}
			}
		}
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < sum.length; i++) {
			max = Math.max(max, sum[i]);
		}
		return max;
	}
	
	/**
	 * Using tabulation compute the max profit of cutting rod.
	 * @param d
	 * @param len
	 * @param cache
	 * @return
	 */
	public static int maxProfitCutting(int[] d, int len, Map<Integer, Integer> cache) {
		if (len == 1) {
			return d[0];
		}
		if (len < 0) {
			return 0;
		}
		if (cache.containsKey(len)) {
			return cache.get(len);
		}
		int profit = d[0];
		for (int i = 1; i < d.length; i++) {
			profit = Math.max(profit, d[i] + maxProfitCutting(d, len - i - 1, cache));
		}
		cache.put(len, profit);
		return profit;
	}
	
	/**
	 * Returns indices of elems with matching sum.
	 * @param arr
	 * @param sum
	 * @return
	 */
	public static int[] maxSumSubArray(int[] arr, int sum) {
		int cur = 0, left = 0, right = 0;
		while(right < arr.length) {
			while (right < arr.length && cur < sum) {
				cur += arr[right++];
			}
			if (cur == sum) {
				return new int[] {left, right - 1};
			}
			while (left < right && cur > sum) {
				cur -= arr[left++];
			}
		}
		return null;
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
		
		assert maxStockProfit(Arrays.asList(1F, 2F, 1F, 5F)) == 4F;
		
		System.out.println(" Toggled : ");
		printToggledOutput(10);
		System.out.println();
		System.out.println(" Toggled optimized");
		printToggledOutputOtimized(10);
		System.out.println();
		
		
		int num = 12;
		System.out.println("Num factors for " + num + " : ");
		getNumFactors(num).forEach(System.out::println);
		System.out.println("Line factors for " + num + " : ");
		getLineFactors(num).forEach(System.out::println);
		
	    int arr[][] = new int[3][4];
	    arr[0] = new int[] { 1,  2,  3,  4 };
	    arr[1] = new int[] { 5,  6,  7,  8 };
	    arr[2] = new int[] { 9, 10, 11, 12 };

	    System.out.println("PrintNWtoSE");
		printNWToSE(arr);
		
		assert canFormTriangle(new int[] {4, 2, 3});
		assert canFormTriangle(new int[] {4, 11, 6}) == false;
		
		System.out.println("Triangle contrained output: ");
		System.out.println(triangleTriplets(Arrays.asList(3, 4, 5, 6, 7)));
		
		// Influencer 
		boolean[][] influencerData = {
				{false, true, true},
				{true, false, true},
				{false, false, false}
		};
		assert influencer(influencerData) == 2;
		
		assert biggestVm(ImmutableList.of(ImmutableMap.of("cpu", 2.4f, "mem", 8.0f), ImmutableMap.of("cpu", 3.0f, "mem", 16.0f))) == 1;
		
		int a[] = {1, 2, 3, 4};
		int b[] = {1, 2, 3};
		int c[] = sumTwoArrays(a, b);
		System.out.println(Arrays.toString(c));
		
		assert maxCoverage(new int[] {5, 2, 3, 10}, 0, 5, 0) == 15;
		
		// Dynamic with path capture.
		Deque<Integer> selection = new ArrayDeque<>();
		Map<Integer, Integer> map = new HashMap<>();
		assert maxCoverage2(new int[] {5, 2, 3, 10}, 0, 5, 0, map, selection) == 15;
		
		assert maxSumSubSequence(new int[]{1,5,3,7,5,6,7}) == 22;
		assert maxProfitCutting(new int[]{1, 3, 5, 6}, 8, new HashMap<>()) ==  19;
		
		System.out.println("maxSumSubArray" + Arrays.toString(maxSumSubArray(new int[]{1, 3, 5, 2, 5, 4}, 12)));
		
		// No assert failure means all work fine.
		System.out.println("Finish: Test list puzzles.");
	}
}
