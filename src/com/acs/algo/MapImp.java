package com.acs.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	
	static int fibinocci(int n, Map<Integer, Integer> cache) {
		if (cache.get(n) != null) {
			return cache.get(n);
		}
		if (n <= 1) {
			return n;
		}
		cache.put(n, fibinocci(n - 1, cache) + fibinocci(n - 2, cache));
		return cache.get(n);
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
		Map<Integer, Integer> cache = new HashMap<>();
		int countFlips = coinDenominations(cache, new int[]{1, 2, 3}, 3);
		System.out.println("Total count flips to sum 3 from 1,2,3 coins : " + countFlips);
		
		assert Objects.equals(leastKRepeatedNum(new int[] {5, 6, 5, 6, 1, 2}, 2), new HashMap<Integer, Integer>(){
		{
			put(5, 2);
			put(6, 2);
		}});
		
		System.out.println(fibinocci(10, new HashMap<>()));
		assert Objects.equals(fibinocci(10, new HashMap<>()), 55);
		
		System.out.println("Done: Testing puzzles with Hashmaps");

	}
}
