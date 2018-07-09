package com.acs.algo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.codehaus.groovy.util.StringUtil;

import com.google.common.collect.ImmutableList;

import groovyjarjarantlr.StringUtils;

public class StringImp {
	
	/**
	 * Input string with 
	 * @param str
	 * @return
	 */
	public static boolean balanceBraces(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		Deque<Character> stack = new ArrayDeque<>();
		for (char c : str.toCharArray()) {
			if (c == '{' || c == '[' || c == '(') {
				stack.push(c);
			}
			if (c == '}' || c == ']' || c == ')') {
				if (stack.isEmpty()) {
					return false;
				}
				char c1 = stack.pop();
				if (!matchingBrace(c1, c)) {
					return false;
				}
			}
		}
		if (!stack.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private static boolean matchingBrace(char c1, char c2) {
		if (c1 == '{' && c2 == '}') {
			return true;
		}
		if (c1 == '[' && c2 == ']') {
			return true;
		}
		if (c1 == '(' && c2 == ')') {
			return true;
		}
		if (c1 == '"' && c2 == '"') {
			return true;
		}
		return false;
	}
	
	/**
	 * a+b* validates aaab, aaabbbbb, a
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean validateSimpleRegex(char[] regex, char[] str) {
		if (regex == null || str == null) {
			throw new IllegalArgumentException("Null String");
		}
		int i, j;
		for (i=0, j=0; i < regex.length && j < str.length; i+= 2) {
			char par = regex[i];
			char opr = regex[i+1];
			switch(opr) {
				case '*': 
					while (j < str.length && str[j] == par) {
						j++;
					}
					break;
				case '+': 
					if (str[j] == par) {
						j++;
					} else {
						return false;
					}
					while (j < str.length && str[j] == par) {
						j++;
					}
					break;
				case '?':  
					if (str[j] == par) {
						j++;
					}
					break;
				default:
					return false;
			}
		}
		if (i >= regex.length && j >= str.length) {
			return true;
		}
		return false;
	}
	
	/**
	 * place Char at Loc of the String.
	 * @param str
	 * @param c
	 * @param loc
	 * @return
	 */
	private static String placeCharAtLoc(String str, char c, int loc) {
		String prev = str.substring(0, loc);
		String post = str.substring(loc);
		return prev + c + post;
	}
	
	/**
	 * Builds all permutations of strings.
	 * @param str
	 * @return
	 */
	public static List<String> permutations(String str) {
		if (str.length() <= 1) {
			return (List<String>) ImmutableList.of(str);
		}
		List<String> subPermutations = permutations(str.substring(1));
		List<String> allStrings = new ArrayList<String>();
		// Insert into subPermutation Strings.
		for(String sub : subPermutations) {
			for(int i = 0; i<= sub.length(); i++) {
				allStrings.add(placeCharAtLoc(sub, str.charAt(0), i));
			}
		}
		return allStrings;
	}
	
	/**
	 * prints (()), ()() for 2.
	 * @param str
	 * @param loc
	 * @param left
	 * @param right
	 */
	public static void printParathesis(char[] str, int loc, int left, int right) {
		if (left == 0 && right == 0) {
			System.out.println(str);
		}
		if(left > 0) {
			str[loc] = '(';
			printParathesis(str, loc + 1, left - 1, right);
		}
		if (right > left) {
			str[loc] = ')';
			printParathesis(str, loc + 1, left, right - 1);
		}
	}
	
	/**
	 * 2D word Scrable with Cross options.
	 * Check if a word exists.
	 * Check if the start alphabet matches.
	 * @param board
	 * @param word
	 * @return
	 */
	public static boolean wordExistsInScrable(char[][]board, String word) {
		if (board == null || word == null) {
			throw new IllegalArgumentException("not valid");
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == word.charAt(0) && wordExistsInScrable(board, i, j, 0, word)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Alphabet at loc is already matched, the proceedings are matched.
	 * @param board
	 * @param y
	 * @param x
	 * @param loc
	 * @param word
	 * @return
	 */
	private static boolean wordExistsInScrable(char[][] board, int y, int x, int loc, String word) {
		if (word.length() - 1 == loc) {
			return true;
		}
		board[y][x] = '*';
		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j++) {
				if ( i >= 0 && i < board.length && j >= 0 && j < board.length && loc - 1 < word.length() &&
						board[i][j] == word.charAt(loc + 1)) {
					 if (wordExistsInScrable(board, i, j, loc + 1, word)) {
						 board[y][x] = word.charAt(loc);
						 return true;
					 }
				}
			}
		}
		board[y][x] = word.charAt(loc);
		return false;
	}
	
	
	/**
	 * First skip spaces then skip all non-spaces then increase count until end of length.
	 * @param words
	 * @return
	 */
	public static int countWords(char[] words) {
		int i = 0;
		if (words == null) {
			throw new IllegalArgumentException("Null String");
		}
		int count = 0;
		while (i < words.length) {
			// Skip spaces.
			while (i < words.length && words[i] == ' ') {
				i++;
			}
			// Skip non spaces and increase count.
			if (i < words.length) {
				while (i < words.length && words[i] != ' ') {
					i++;
				}
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count unique words using hashset.
	 * @param words
	 * @return
	 */
	public static int countUniqueWords(String words) {
		Set<String> set = new HashSet<>(Arrays.asList(words.split("\\s+")));
		return set.size();
	}
	
	/**
	 * compute word and count.
	 * @param line
	 * @return
	 */
	public static Map<String, Integer> countWords(String line) {
		Map<String, Integer> wordMap = new HashMap<>();
		String[] words = line.split("\\W");
		for (String word : words) {
			wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
		}
		return wordMap;
	}
	
	/**
	 * print each word seperated by line nos observed in document.
	 * https://careercup.com/question?id=5742635739250688
	 * @param document
	 */
	public static void printWordLineNumber(String document) {
		Map<String, List<Integer>> outputMap = new HashMap<>();
		String[] lines = document.split("\\n");
		int lineCount = 1;
		for(String line: lines) {
			for(String word: line.split("\\W")) {
				List<Integer> listLineNo = outputMap.get(word);
				if (listLineNo == null) {
					listLineNo = new ArrayList<>();
					outputMap.put(word, listLineNo);	
				}
				outputMap.get(word).add(lineCount);
			}
			lineCount++;
		}
		for(Map.Entry<String, List<Integer>> e : outputMap.entrySet()) {
			System.out.print(e.getKey());
			e.getValue().forEach( lineNo -> System.out.print(" " + lineNo));
			System.out.println();
		}
	}
	
	/**
	 * Is Palindrome
	 * @param str
	 * @return
	 */
	public static boolean isPalindrom(String str) {
		if (str == null || str.length() == 1) {
			return true;
		}
		int i = 0, j = str.length() - 1;
		while ( i < j) {
			if (str.charAt(i) != str.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}
	
	/**
	 * Trie datastructure.
	 * @author asingh
	 *
	 */
	public static class Trie {
		private static class TNode {
			public TNode[] child;
			public boolean isWord;
		}
		private TNode root = new TNode();
		
		/**
		 * Train Trie.
		 * @param words
		 */
		public void init(String[] words) {
			for (String word: words) {
				TNode node = root;
				for (char c : word.toCharArray()) {
					if (node.child == null) {
						node.child = new TNode[26];
					}
					if (node.child[c - 'a'] == null) {
						node.child[c - 'a'] = new TNode();
					}
					node = node.child[c - 'a'];
				}
				node.isWord = true;
			}
		}
		

		/**
		 * Matches with exact word
		 * @param word
		 * @param index
		 * @param node
		 * @return
		 */
		private boolean exists(String word) {
			if (word == null || "".equals(word) || !word.matches("[a-z]*")) {
				throw new IllegalArgumentException();
			}
			return exists(word.toCharArray(), 0, root);
		}
		
		private boolean exists(char[] word, int index, TNode node) {
			if (node == null) {
				return false;
			}
			if (word.length == index && node.isWord) {
				return true;
			}
			if (node.child == null || word.length <= index) {
				return false;
			}
			return exists(word, index + 1, node.child[word[index] - 'a']);
		}
		
		/**
		 * Matches fo.d ==> food, foed etc.
		 * @param word
		 * @return
		 */
		public boolean existsWithDot(String word) {
			if (word == null || "".equals(word) || !word.matches("[.a-z]*")) {
				throw new IllegalArgumentException();
			}
			return existsWithDot(word.toCharArray(), 0, root);
		}
		
		private boolean existsWithDot(char[] word, int index, TNode node) {
			if (node == null) {
				return false;
			}
			if (word.length == index && node.isWord) {
				return true;
			}
			if (node.child == null || word.length <= index) {
				return false;
			}
			if (word[index] == '.') {
				for (int i=0; i<26; i++) {
					if (existsWithDot(word, index + 1, node.child[i])) {
						return true;
					}
				}
				return false;
			} else {
				return existsWithDot(word, index + 1, node.child[word[index] - 'a']);
			}
		}
		
	}
	
	static class WordNode {
		Set<WordNode> neighbors = new HashSet<>();
		String word;
		WordNode(String word) {
			this.word = word;
		}
	}
	
	/**
	 * Prints path from start word to end word with a jump of one char.
	 * Build graph of words and then DFS
	 * @param start
	 * @param end
	 * @param dictionary
	 */
	public static void pathBetweenWords(String start, String end, String[] dictionary) {
		WordNode root = buildGraph(start, dictionary);
		System.out.println(dfsPathToWord(start, end, root));
	}
	
	
	private static WordNode buildGraph(String start, String[] dictionary) {
		Map<String, WordNode> wordMap = new HashMap<>();
		for (String word: dictionary) {
			wordMap.put(word, new WordNode(word));
		}
		for (String word1: dictionary) {
			for (String word2: dictionary) {
				if (word1.equals(word2)) {
					continue;
				}
				if (isAlphabetApart(word1, word2)) {
					wordMap.get(word1).neighbors.add(wordMap.get(word2));
				}
			}
		}
		return wordMap.get(start);
	}
	
	private static boolean isAlphabetApart(String word1, String word2) {
		int diff = 0;
		if (word1 == null || word2 == null) {
			return false;
		}
		if (word1.length() != word2.length()) {
			return false;
		}
		for(int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				diff++;
			}
			if (diff > 1) {
				return false;
			}
		}
		if (diff == 1) {
			return true;
		}
		return false;
	}
	
	private static List<String> dfsPathToWord(String start, String end, WordNode root) {
		Stack<WordNode> stack = new Stack<>();
		List<String> path = new ArrayList<>();
		stack.push(root);
		Set<WordNode> visited = new HashSet<>();
		while(!stack.isEmpty()) {
			WordNode current = stack.pop();
			if (visited.contains(current)) {
				continue;
			}
			stack.addAll(current.neighbors);
			path.add(current.word);
			visited.add(current);
		}
		return path;
	}
	
	/**
	 * "man cow duck"
	 * ->
	 * "duck cow man"
	 * @param data
	 */
	public static void printReverse(char[] data) {
		int endPoint = data.length - 1;
		for(int i=data.length - 1; i >= 0; i--) {
			if (data[i] == ' ') {
				System.out.print(new String(Arrays.copyOfRange(data, i + 1, endPoint + 1)) + " ");
				endPoint = i - 1;
			}
		}
		System.out.println( Arrays.copyOfRange(data, 0, endPoint + 1));
	}
	
	
	/**
	 *   "abcdefcdxaaaaa"
	 *   search "cdx"
	 *   
	 * @param data
	 * @param search
	 * @return
	 */
	public static int subStr(char[] data, char[] search) {
		for(int i = 0; i < data.length; i++) {
			int j = 0;
			for (j = 0; j < search.length; j++) {
				if (data[i + j] != search[j]) {
					i += j;
					j = 0;
					break;
				}
			}
			if (j == search.length) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * goodnightsleep is breakable.
	 * @param string
	 * @param start
	 * @param end
	 * @param cache
	 * @param dic
	 * @return
	 */
	public static boolean isBreakable(char[] string, int start, int end, boolean[] cache, Set<String> dic) {
		if (string == null || cache == null || start >= end) {
			throw new IllegalArgumentException();
		}
		if (dic.contains(String.valueOf(string, start, end - start + 1))) {
			return true;
		}
		if (cache[start]) {
			return false;
		} else {
			cache[start] = true;
		}
		for (int pivot = start + 1; pivot < end; pivot++) {
			if (dic.contains(String.valueOf(string, start, pivot - start + 1))) {
				if (isBreakable(string, pivot + 1, end, cache, dic)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * goodnightsleep is breakable.
	 * @param string
	 * @param start
	 * @param end
	 * @param cache
	 * @param dic
	 * @return
	 */
	public static boolean isBreakable(String string, int start, boolean[] cache, Set<String> dic) {
		if (string == null || cache == null) {
			throw new IllegalArgumentException();
		}
		if (dic.contains(string)) {
			return true;
		}
		if (cache[start]) {
			return false;
		} else {
			cache[start] = true;
		}
		for (int pivot = 1; pivot < string.length(); pivot++) {
			if (dic.contains(string.substring(0, pivot + 1))) {
				if (isBreakable(string.substring(pivot + 1), start + pivot + 1, cache, dic)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static String skipOutChar(String str, int loc) {
		if (str.length() <= 1) {
			return "";
		}
		String skipped = str.substring(0, loc);
		if (loc < str.length() - 1) {
			skipped += str.substring(loc + 1);
		}
		return skipped;
	}
	
	/**
	 * Permutate variation.
	 * @param current
	 * @param left
	 */
	public static void permutate(String current, String left) {
		if ("".equals(left)) {
			System.out.println(current);
			return;
		}
		for (int i = 0; i< left.length(); i++) {
			permutate(current + left.charAt(i), skipOutChar(left, i));
		}
	}
	
	private static String sortWord(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		return String.valueOf(chars);
	}
	
	public static int coolingTime(List<String> jobs, int cooling) {
		int time = 0;
		Map<String, Integer> map = new HashMap<>();
		for (String job: jobs) {
			Integer foundTime = map.get(job);
			if (foundTime != null && (time - foundTime <= cooling)) {
				time = time - foundTime + cooling - 1;
			}
			map.put(job, time);
			time++;
		}
		return time;
	}
	
	public static void printSameAnagrams(List<String> words) {
		Map<String, List<String>> map = new HashMap<>();
		for (String word : words) {
			String sorted = sortWord(word);
			map.putIfAbsent(sorted, new ArrayList<>());
			map.get(sorted).add(word);
		}
		
		for (String key : map.keySet()) {
			System.out.println(map.get(key));
		}
	}
	
	/**
	 * Using List.
	 * @param current
	 * @param left
	 */
	public static void printPermutate2(char[] string, int current) {
		if (current == string.length) {
			System.out.print(String.valueOf(string) + " , ");
			return;
		}
		for(int i = current; i < string.length; i++) {
			swap(string, current, i);
			printPermutate2(string, current + 1);
			swap(string, current, i);
		}
	}
	
	private static void swap(char[] string, int to, int from) {
		// swap characters.
		char temp = string[to];
		string[to] = string[from];
		string[from] = temp;
	}
	
	/**
	 * jab = > 012
	 * Mod 10 each character.
	 * @param str
	 * @return
	 */
	public static String convertToDigit(String str) {
		if (str == null || !str.matches("[a-zA-Z]*")) {
			throw new IllegalArgumentException();
		}
		StringBuilder sb = new StringBuilder();
		for(char c : str.toLowerCase().toCharArray()) {
			sb.append((c - 'a' + 1) % 10);
		}
		return sb.toString();
	}
	
	/**
	 * str can take 0..9 and +,-
	 * ex. 43 + 12 - 3
	 * @param str
	 * @return
	 */
	public static int eval(String str) {
		int result = 0;
		boolean neg = false;
		if (str.charAt(0) == '-') {
			neg = true;
		}
		int current = 0;
		for (char c: str.toCharArray()) {
			switch(c) {
			case ' ':
				break;
			case '+' : 
				current = neg? -current : current;
				result += current;
				neg = false;
				current = 0;
				break;
			case '-' :
				current = neg? -current : current;
				result += current;
				neg = true;
				current = 0;
				break;
			default:
				current *= 10;
				current += (c - '0');
				break;
			}
		};
		current = neg? -current : current;
		result += current;
		return result;
	}
	
	/**
	 * 1+2...9 == 100 then print
	 * Find all such equations made of +,-
	 * @param str
	 * @param i
	 */
	public static void printEvalsTo100(String str, int i) {
		if (i == 10) {
			if (eval(str) == 100) {
				System.out.println(str);
			}
			return;
		}
		printEvalsTo100(str + String.valueOf(i), i + 1);
		if (i > 1) {
			printEvalsTo100(str + "+" + String.valueOf(i), i + 1);
		}
		printEvalsTo100(str + "-" + String.valueOf(i), i + 1);
	}
	
	/**
	 * print phone pad.
	 * @param out
	 * @param in
	 * @param len
	 */
	public static void printPhonePad(char[] chars, char[] nums, int len) {
		if (len == nums.length) {
			System.out.print(String.valueOf(chars) + " , ");
			return;
		}
		char[] alphabets = alphas(nums[len]);
		for (char c: alphabets) {
			chars[len] = c;
			printPhonePad(chars, nums, len + 1);
		}
	}
	
	private static char[] alphas(char c) {
		if (c == '0') {
			return new char[]{' '};
		}
		if (c < '1' || c > '9') {
			throw new IllegalArgumentException("Must be valid number");
		}
		char[] chars = new char[3];
		for(int i = 0; i< 3; i++) {
			chars[i] = (char) ((c - '1') * 3 + 'A' + i);
		}
		return chars;
	}
	
	public static boolean isRotation(String str1, String str2) {
		if (str1.length() != str2.length()) {
			return false;
		}
		String doubleStr1 = str1 + str1;
		if(doubleStr1.indexOf(str2) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Roman to value
	 * @param str
	 * @return
	 */
	public static int romanValue(String str) {
		int result = 0;
		int lastVal = 1000, curVal = 0;
		for(char c : str.toUpperCase().toCharArray()) {
			switch(c) {
				case 'I' : curVal = 1; break;
				case 'V' : curVal = 5; break;
				case 'X' : curVal = 10; break;
				case 'L' : curVal = 50; break;
				case 'C' : curVal = 100; break;
				case 'D' : curVal = 500; break;
				case 'M' : curVal = 1000; break;
				default : return -1;
			}
			if (lastVal < curVal) {
				result = result - 2 * lastVal;
			}
			result += curVal;
			lastVal = curVal;
		}
		return result;
	}
	
	
	/**
	 * Parse double
	 * 1. +- 44.221
	 * https://www.careercup.com/question?id=5747740665446400
	 * @param str
	 * @return
	 */
	public static double parseNumber(String str) {
		if (str == null || !str.matches("\\+?\\-?\\d+.?\\d+")) {
			throw new IllegalArgumentException();
		}
		boolean negative = false;
		str = str.trim();
		char[] chars = str.toCharArray();
		int i = 0;
		if (chars[0] == '-') {
			negative = true;
			i++;
		} else if (chars[0] == '+') {
			i++;
		}
		double val = 0.0;
		while(i < str.length() && chars[i] != '.') {
			val *= 10;
			val += chars[i] - '0';
			i++;
		}
		double afterDotVal = 1;
		if (chars[i] == '.') {
			i++;
			while(i < str.length()) {
				afterDotVal *= 0.1;
				val += (chars[i] - '0') * afterDotVal;
				i++;
			}
		}
		return negative ? - val : val;
	}
	
	/**
	 * Max palindrom length if any length data can be removed.
	 * https://www.careercup.com/question?id=5705985515585536
	 * @param data
	 * @param start
	 * @param end
	 * @param cache
	 * @return
	 */
	public static int maxPalindrome(Integer[] data, int start, int end, Integer[][] cache) {
		if (data == null) {
			return 0;
		}
		if (start > end) {
			return 0;
		}
		if (cache[start][end] != null) {
			return cache[start][end];
		}
		int result = 0;
		if (start == end) {
			result = 1;
		} else if (data[start] == data[end]) {
			result = 2 + maxPalindrome(data, start + 1, end - 1, cache);
		} else {
			result = Math.max(maxPalindrome(data, start + 1, end, cache),
					maxPalindrome(data, start, end - 1, cache));
		}
		cache[start][end] = result;
		return result;
	}
	
	/**
	 * Finds min distance between indexes of words.
	 * https://www.careercup.com/question?id=6051601991073792
	 * @param list
	 * @param a
	 * @param b
	 * @return
	 */
	public static int minDistanceWords(String list, String a, String b) {
		int min = Integer.MAX_VALUE;
		int ai = -1, bi = -1;
		String[] words = list.toLowerCase().split("\\W+");
		int i = 0;
		for (String word: words) {
			if (word.equals(a)) {
				ai = i;
			} else if (word.equals(b)) {
				bi = i;
			}
			if (ai != -1 && bi != -1) {
				min = Math.min(min, Math.abs(ai - bi));
			}
			i++;
		}
		return min;
	}
	
	
	/**
	 * Find max length of Palindome sub sequence.
	 * 
	 * Using dynamic programming via memoization.
	 * @param str
	 * @param left
	 * @param right
	 * @param cache
	 * @return
	 */
	public static int maxLenPalindromSubSequence(String str, int left, int right, int[][] cache) {
		if (cache[left][right] != 0) {
			return cache[left][right];
		}
		if (left > right) {
			return 0;
		}
		if (left == right) {
			cache[left][right] = 1;
			return cache[left][right];
		}
		if (left == right - 1 && str.charAt(left) == str.charAt(right)) {
			cache[left][right] = 2;
			return cache[left][right];
		}
		if (str.charAt(left) != str.charAt(right)) {
			cache[left][right] = Math.max(maxLenPalindromSubSequence(str, left, right - 1, cache),
					maxLenPalindromSubSequence(str, left + 1, right, cache));
			return cache[left][right];
		}
		if (str.charAt(left) == str.charAt(right)) {
			cache[left][right] = maxLenPalindromSubSequence(str, left + 1, right - 1, cache) + 2;
			return cache[left][right];
		}
		throw new IllegalStateException();
	}
	
	private static boolean matchedMap(Map<Character, Integer> tMap, Map<Character, Integer> sMap) {
		for (Map.Entry<Character, Integer> e : tMap.entrySet()) {
			if (!sMap.containsKey(e.getKey()) || sMap.get(e.getKey()) < e.getValue()) {
				return false;
			}
		}
		return true;
	}
	
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int left = 0, right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(left, map.get(s.charAt(right)) + 1);
            }
            map.put(s.charAt(right), right);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
	
	/**
	 * return smallest string in s that has all characters of t.
	 * @param s
	 * @param t
	 * @return
	 */
	public static String minSubSequence(String s, String t) {
		Map<Character, Integer> tMap = new HashMap<>();
		Map<Character, Integer> sMap = new HashMap<>();
		for (Character c: t.toCharArray()) {
			tMap.put(c, tMap.getOrDefault(c, 0) + 1);
		}
		int start = 0;
		String minString = "";
		int min = Integer.MAX_VALUE;
		for (int end = 0; end < s.length(); end++) {
			if (end < s.length() && !matchedMap(tMap, sMap)) {
				Character c = s.charAt(end);
				sMap.put(c, sMap.getOrDefault(c, 0) + 1);
			}
			if (matchedMap(tMap, sMap) && min > end - start) {
				min = end - start;
				// start might have not been initialized in the shrinking stage.
				minString = s.substring(start < 1 ? 0 : start - 1, end);
			}
			while (start < end && matchedMap(tMap, sMap)) {
				Character c = s.charAt(start++);
				sMap.put(c, sMap.getOrDefault(c, 0) - 1);
			}
		}
		return minString;
	}
	
	public static void main(String[] args) {
		System.out.println("Start: Testing String related puzzles");
		System.out.println("Testing countWords()");
		assert countWords("".toCharArray()) == 0;
		assert countWords("aa aa ".toCharArray()) == 2;
		assert countWords(" ".toCharArray()) == 0;
		assert countWords(" x xx xxx x ".toCharArray()) == 4;
		System.out.println("Testing simple regex");
		assert validateSimpleRegex("A+B*".toCharArray(), "AAAABBBBB".toCharArray());
		assert !validateSimpleRegex("A+B*".toCharArray(), "AAAABBBBBC".toCharArray());
		assert !validateSimpleRegex("A?B*".toCharArray(), "AAAABBBBBC".toCharArray());
		assert validateSimpleRegex("A*B*C?".toCharArray(), "AAAABBBBBC".toCharArray());
		System.out.println("Testing permutations of 'abc'");
		for(String str: permutations("abc")) {
			System.out.println(str);
		}
		System.out.println("Testing parathesis of 3 length");
		printParathesis(new char[6], 0, 3, 3);
		System.out.println(countUniqueWords("that is that  is"));
		assert countUniqueWords("the world the    world the") == 2 : "spaces are breaking";
		String palindromeData = "abcdcba";
		System.out.println("is Palindrom " + palindromeData + " : " + isPalindrom(palindromeData));
		
		Trie trie = new Trie();
		trie.init(new String[] { "food", "drink" });
		assert trie.exists("food");
		assert trie.exists("drink");
		assert trie.exists("drinsk") == false;
		assert trie.existsWithDot("food");
		assert trie.existsWithDot("foo.");
		assert trie.existsWithDot("drin.");
		assert trie.existsWithDot("foa") == false;
		assert trie.existsWithDot("fo") == false;
		
		System.out.println("CountWords for 'Hello World Hello'");
		Map<String, Integer> wordCount = countWords("Hello World Hello");
		wordCount.entrySet().forEach(System.out::println);
		
		String document = "some \n hello \n some hello";
		printWordLineNumber(document);
		
		
		//Input:  Dictionary = {POON, PLEE, SAME, POIE, PLEA, PLIE, POIN}
        //start = TOON
        //target = PLEA
		//Output: 7
		//Explanation: TOON - POON - POIN - POIE - PLIE - PLEE - PLEA
		String[] dictionary = {"TOON", "POON", "PLEE", "SAME", "POIE", "PLEA", "PLIE", "POIN"};
		pathBetweenWords("TOON", "PLEA", dictionary);
		
		printReverse("man cow duck".toCharArray());
		assert subStr("abcdefcdxaaaaa".toCharArray(), "cdx".toCharArray()) == 6 : " should be 6";
		assert subStr("abcdefcdxaaaaa".toCharArray(), "cdz".toCharArray()) == -1 : "should not be found";
		
		String test = "goodnightsleep";
		int size = test.length();
		assert isBreakable(test.toCharArray(), 0, size - 1, new boolean[size], new HashSet<>(Arrays.asList("good", "night", "sleep")));
		assert isBreakable(test, 0, new boolean[size], new HashSet<>(Arrays.asList("good", "night", "sleep")));
		
		System.out.println("permutate with call stack ");
		permutate("", "abc");
		
		assert sortWord("cba").equals("abc");
		
		System.out.println("Printing same anagrams");
		printSameAnagrams(Arrays.asList("boat", "toab", "abc", "cba", "a"));
		assert eval("12 + 5 - 5") == 12;
		assert eval("-12 - 5 + 5 + 12") == 0;
		printEvalsTo100("", 1);
		char[][] scrable = { 
				{'p', 'l', 'a', 'c', 'e'},
				{'s', 'm', 'i', 'l', 'e'},
				{'w', 'i', 'r', 'e', 'd'},
				{'w', 'i', 'r', 'e', 'd'},
				{'w', 'i', 'r', 'e', 'd'}
		};
		assert wordExistsInScrable(scrable, "mice") == true : "should exists in from center going right above";
		assert wordExistsInScrable(scrable, "xxx") == false: "should not exists in from center going right above";
		
		assert coolingTime(Arrays.asList("A", "B", "A", "B"), 3) == 6;
		
		System.out.println("Print phone pad for 12:");
        char[] dataAlphas = alphas('1');
        assert dataAlphas[0] == 'A';
        assert dataAlphas[1] == 'B';
        assert dataAlphas[2] == 'C';
        assert dataAlphas.length == 3;
        printPhonePad(new char[2], new char[]{'1', '2'}, 0);
        System.out.println("Permutations2 : ");        
        printPermutate2("ABC".toCharArray(), 0);
        System.out.println();
        assert isRotation("abc", "cab");
        assert !isRotation("abc", "cba");
        
        // Roman testing
        assert romanValue("XVI") == 16;
        assert romanValue("XII") == 12;
        assert romanValue("XIV") == 14;
        
        assert parseNumber("-4.215") == -4.215;
        
        // Max palindrom length
        assert maxPalindrome(Arrays.asList(1,2,3,4,5,4,3,2,1,2,3,4,5).toArray(new Integer[0]), 0, 12, new Integer[13][13]) == 9;
        
        assert minDistanceWords("world hello super man and woman welcome man to world", "man", "world") == 2;
        
        assert convertToDigit("jab").equals("012");
        
        assert maxLenPalindromSubSequence("supuspp", 0, 6, new int[7][7]) == 5;
        
        System.out.println("minSubSequence " + minSubSequence("abdcdfadca", "ac"));
        assert minSubSequence("abdcdfadca", "ac").equals("adc");
        
        assert balanceBraces("(){}[[{()}]]");
        assert !balanceBraces("()}[[{()}]]");
        
        assert lengthOfLongestSubstring("abcadaac") == 4;
        
		System.out.println("Done: Testing String related puzzles");
	}
}
