package com.acs.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.ImmutableList;

public class StringImp {
	
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
		assert eval("12 + 5 - 5") == 12;
		assert eval("-12 - 5 + 5 + 12") == 0;
		System.out.println("Done: Testing String related puzzles");
	}
}
