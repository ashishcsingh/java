package com.acs.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class MapImp {	
	public static Map<Integer, Integer> histogram(int... data) {
		Map<Integer, Integer> map = new HashMap<>();
		for(int i: data) {
			map.put(i, map.getOrDefault(i, 0) + 1);
		}
		return map;
	}
	
	/**
	 * least K repeated nums.
	 * @param data
	 * @param k
	 * @return
	 */
	public static Map<Integer, Integer> leastKRepeatedNum(int[] data, int k) {
		Map<Integer, Integer> histgramAll = histogram(data);
		Map<Integer, Integer> result = new HashMap<>();
		for(Map.Entry<Integer, Integer> e : histgramAll.entrySet()) {
			if (e.getValue() >= k) {
				result.put(e.getKey(), e.getValue());
			}
		}
		return result;
	}
	
	/**
	 * Count all combinations of coin flips to reach the sum.
	 * @param cache
	 * @param counter
	 * @param coins
	 * @param sum
	 * @return
	 */
	public static int coinDenominations(Map<Integer, Integer> cache, int[] coins, int sum) {
		if (cache == null || coins == null || sum <= 0) {
			throw new IllegalArgumentException("invalid parameters");
		}
		if (cache.get(sum) != null) {
			return cache.get(sum);
		}
		// compute coin counts.
		int result = 0;
		for(int coin: coins) {
			if (sum - coin < 0) {
				// Skip, nothing more to progress.
				continue;
			} else if (sum - coin == 0) {
				// Target reached.
				result += 1;
			} else {
				// Keep hunting in smaller denominations.
				result += coinDenominations(cache, coins, sum - coin);
			}
		}
		cache.put(sum, result);
		return result;
	}
	
	/**
	 * fibinocci using caching.
	 * @param n
	 * @param cache
	 * @return
	 */
	public static int fibinocci(int n, Map<Integer, Integer> cache) {
		if (cache.get(n) != null) {
			return cache.get(n);
		}
		if (n <= 1) {
			return n;
		}
		cache.put(n, fibinocci(n - 1, cache) + fibinocci(n - 2, cache));
		return cache.get(n);
	}
	
	/**
	 *  Count Conversion int to char 121 => 3
	 * @param digits
	 * @param index
	 * @return
	 */
	public static int countDigitToChars(int[] digits, int index) {
		if (digits == null || index > digits.length) {
			return 0;
		}
		if (index == digits.length) {
			return 1;
		}
		int count = 0;
		count += countDigitToChars(digits, index + 1) ;
		if (index < digits.length - 1 && digits[index] <= 2 && digits[index+1] < 7) {
			count += countDigitToChars(digits, index + 2);
		}
		return count;
	}
	
	/**
	 * Minimum coin denomination to reach a target.
	 * @param cache
	 * @param coins
	 * @param sum
	 * @return
	 */
	public static int minDenomination(Map<Integer, Integer> cache, int[] coins, int sum) {
		if (cache == null || coins == null) {
			throw new IllegalArgumentException("invalid params");
		}
		if (sum < 0) {
			return Integer.MAX_VALUE - 1;
		}
		if (sum == 0) {
			return 0;
		}
		if (cache.get(sum) != null) {
			return cache.get(sum);
		}
		int result = Integer.MAX_VALUE - 1;
		for (int coin: coins) {
			result = Math.min(result, minDenomination(cache, coins, sum - coin) + 1);
		}
		cache.put(sum, result);
		return result;
	}
	
	/**
	 * Compute execution and cooling cost for set of tasks.
	 * Same task would take cooling time.
	 * @param jobs
	 * @param cooling
	 * @return total time including cooling.
	 */
	public static int jobTimeWithCooling(String[] jobs, int cooling) {
		Map<String, Integer> map = new HashMap<>();
		int current = 0;
		for (String job: jobs) {
			if (map.get(job) == null) {
				map.put(job, current);
			} else {
				int found = map.get(job);
				current = Math.max(current, found + 1 + cooling);
				map.put(job, current);
			}
			current++;
		}
		return current;
	}
	
	
	/**
	 * Print unique nums when not sorted.
	 * @param data
	 */
	public static void printUniqueNums(int[] data) {
		System.out.println("Unqiue nums : ");
		Map<Integer, Integer> countMap = new HashMap<>();
		for (Integer val : data) {
			if (countMap.containsKey(val)) {
				countMap.put(val, countMap.get(val) + 1);
			} else {
				countMap.put(val, 1);
			}
		}
		for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
			if (entry.getValue() == 1) {
				System.out.print(entry.getKey() + ", ");
			}
		}
		System.out.println();
	}
	
	public static void printUniqueNumsForSorted(int[] data) {
		System.out.println("Sorted Unqiue nums : ");
		if (data.length == 1) {
			System.out.print(data[0]);
			return;
		}
		for (int i = 1; i < data.length - 1; i++) {
			if (data[i - 1] != data[i] && data[i] != data[i+1]) {
				System.out.print(data[i] + ", ");
			}
		}
		if (data[data.length - 2] != data[data.length - 1]) {
			System.out.print(data[data.length - 1] + ", ");
		}
	}
	
	/**
	 * Print by all common ingredents
	 *  fried rice : rice, oil, onions
	 *  salad: lettuce
	 *  pasta: rice, tomotos
	 *  
	 *  (fried rice, pasta) , (salad)
	 *  
	 *  
	 *  
	 *  https://www.careercup.com/question?id=5754648968298496
	 * @param recipies
	 */
	public static void printByCommonIngredents(Map<String, List<String>> recipies) {
		Map<String, List<String>> reverseMap = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : recipies.entrySet()) {
			for (String item : entry.getValue()) {
				reverseMap.putIfAbsent(item, new ArrayList<String>());
				reverseMap.get(item).add(entry.getKey());
			}
		}
		for (Map.Entry<String, List<String>> entry : reverseMap.entrySet()) {
			// Skip one and only ingredient.
			if (entry.getValue().size() == 1) {
				continue;
			}
			System.out.print("(");
			for (String item : entry.getValue()) {
				System.out.print(item + ",");
			}
			System.out.println(")");
		}
	}
	

	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		System.out.println("Start: Testing puzzles with Hashmaps");
		assert Objects.equals(histogram(new int[]{2,1,3,4,2,1,3,4}), new HashMap<Integer, Integer>(){
		{
			put(2, 2);
			put(1, 2);
			put(3, 2);
			put(4, 2);
		}});
		
		// Coin flips Dynamic programming.
		int countFlips = coinDenominations(new HashMap<>(), new int[]{1, 2, 3}, 3);
		System.out.println("Total count flips to sum 3 from 1,2,3 coins : " + countFlips);
		int minCountFlips = minDenomination(new HashMap<>(), new int[]{1, 2, 3}, 10);
		System.out.println("Minimum flips to sum 10 from 1,2,3 coins : " + minCountFlips);
		//assert countDigitToChars(new int[] {1,2,1}, 0) == 3;
		System.out.println(" 121 => Chars : " + countDigitToChars(new int[] {1, 2, 1}, 0));
		
		
		assert Objects.equals(leastKRepeatedNum(new int[] {5, 6, 5, 6, 1, 2}, 2), new HashMap<Integer, Integer>(){
		{
			put(5, 2);
			put(6, 2);
		}});
		
		System.out.println(fibinocci(10, new HashMap<>()));
		assert Objects.equals(fibinocci(10, new HashMap<>()), 55);
		assert jobTimeWithCooling(new String[] {"A", "A"}, 3) == 5;
		assert jobTimeWithCooling(new String[] {"A", "B", "A", "B" }, 3) == 6;
		
		int uniqueData[] = {1, 1, 2, 3, 4, 4, 5, 5};
		printUniqueNums(uniqueData);
		System.out.println("Now for sorted");
		printUniqueNumsForSorted(uniqueData);
		
		System.out.println("Print common ingredients ");
		 Map<String, List<String>> recipies = ImmutableMap.of("fried rice", ImmutableList.of("rice", "oil", "onion"),
				 "salad", ImmutableList.of("lettuice"), "pasta", ImmutableList.of("rice", "tomotos"));
		printByCommonIngredents(recipies);
		System.out.println();
		System.out.println("Done: Testing puzzles with Hashmaps");

	}
}
