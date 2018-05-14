package com.acs.algo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.acs.algo.SetImp.Summable;
import com.acs.algo.SetImp.SummableImpl;
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


	/* This class will be given a list of words (such as might be tokenized
	 * from a paragraph of text), and will provide a method that takes two
	 * words and returns the shortest distance (in words) between those two
	 * words in the provided text.
	 * Example:
	 *   WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
	 *   assert(finder.distance("fox", "the") == 3);
	 *   assert(finder.distance("quick", "fox") == 1);
	 *
	 * "quick" appears twice in the input. There are two possible distance values for "quick" and "fox":
	 *     (3 - 1) = 2 and (4 - 3) = 1.
	 * Since we have to return the shortest distance between the two words we return 1.
	 */
	public static class WordDistanceFinder {
		Map<String, List<Integer>> map = new HashMap<>();
		public WordDistanceFinder (List<String> words) {
			int i =0;
			for (String word: words) {
				map.putIfAbsent(word, new ArrayList<>());
				map.get(word).add(i++);
			}
		}
		public int distance (String wordOne, String wordTwo) {
			if (map.get(wordOne) == null || map.get(wordTwo) == null) {
				return -1;
			}
			int maxIndexOne = map.get(wordOne).size();
			int maxIndexTwo = map.get(wordTwo).size();
			int indexOne = 0;
			int indexTwo = 0;
			int minDistance = Integer.MAX_VALUE;
			while (indexOne < maxIndexOne && indexTwo < maxIndexTwo) {
				int valueOne = map.get(wordOne).get(indexOne);
				int valueTwo = map.get(wordTwo).get(indexTwo);
				minDistance = Math.min(minDistance, Math.abs(valueOne - valueTwo));
				if (valueOne < valueTwo) {
					indexOne++;
				} else {
					indexTwo++;
				}
			}
			return minDistance;
		}
	}

	/**
	 * Get all connected contacts via DFS
	 * @param contact
	 * @param contactToEmail
	 * @param emailToContact
	 * @return
	 */
	public static Set<String> connectedContacts(String contact, Map<String, Collection<String>> contactToEmail, Map<String, Collection<String>> emailToContact) {
		Set<String> visited = new HashSet<>();
		Deque<String> stack = new ArrayDeque<>();
		stack.push(contact);
		while(!stack.isEmpty()) {
			String current = stack.pop();
			if (visited.contains(current)) {
				continue;
			}
			for (String email : contactToEmail.get(current)) {
				stack.addAll(emailToContact.get(email));
			}
			visited.add(current);
		}
		return visited;
	}

	/**
	 * Reverse lookup from emailToContact and vice versa.
	 * @param map
	 * @return
	 */
	public static Map<String, Collection<String>> reverseLookup(Map<String, Collection<String>> map) {
		Map<String, Collection<String>> output = new HashMap<>();
		for (Entry<String, Collection<String>> entry : map.entrySet()) {
			for (String val : entry.getValue()) {
				output.putIfAbsent(val, new HashSet<>());
				output.get(val).add(entry.getKey());
			}
		}
		return output;
	}


	/**
	 * https://www.careercup.com/question?id=5651199228379136
	 * Test should return in O(1) whether there are two nums that totals the num.
	 */
	public static interface TwoSum {
		public void store (int num);
		public boolean test(int num);
	}

	public static class TwoSumImpl implements TwoSum {
		Map<Integer, Integer> map = new HashMap<>();
		@Override
		public void store(int num) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}

		@Override
		public boolean test(int num) {
			for (Entry<Integer, Integer> e : map.entrySet()) {
				// If second part exists
				if (map.containsKey(num - e.getKey())) {
					// If second part is same as first part
					if (num - e.getKey() == e.getKey()) {
						// If second part does not exist separately
						if (e.getValue() == 1) {
							return false;
						}
						return true;
					}
					return true;
				}
			}
			return false;
		}
	}
	
    public static int[] twoSumIndexes(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        int i = 0;
        for (int num: nums) {
            map.computeIfAbsent(num, k -> new ArrayList<>()).add(i++);
        }
        for (int num: nums) {
            if (num == target - num) {
                if (map.get(num).size() > 1) {
                    return new int[] {map.get(num).get(0), map.get(num).get(1)};
                } else {
                    continue;
                }
            }
            if (map.containsKey(target - num)) {
                return new int[] {map.get(num).get(0), map.get(target - num).get(0)};
            }
        }
        return null;
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

		WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
		assert(finder.distance("fox", "the") == 3);
		assert(finder.distance("quick", "fox") == 1);

		System.out.println("EmailToContact");
		Map<String, Collection<String>> directory = ImmutableMap.of("ashish singh", ImmutableList.<String>of("ashishcsingh@gmail.com",
				"singhashish37@gmail.com"), "ashish c singh", ImmutableList.<String>of("singhashish37@gmail.com"));
		Map<String, Collection<String>> emailToContacts = reverseLookup(directory);
		System.out.println(connectedContacts("ashish singh", directory, emailToContacts));
		
		int[] result = twoSumIndexes(new int[]{3,3}, 6);
		assert(result[0] == 0);
		assert(result[1] == 1);

		// Two parts should exist.
		TwoSum sums = new TwoSumImpl();
		sums.store(4);
		sums.store(5);
		sums.store(6);
		assert !sums.test(7);
		assert !sums.test(8);
		assert sums.test(9);
		assert sums.test(10);

		System.out.println("Done: Testing puzzles with Hashmaps");

	}
}
