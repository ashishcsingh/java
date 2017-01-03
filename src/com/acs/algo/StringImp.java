package com.acs.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * Is Palindrome
	 * @param str
	 * @return
	 */
	public static boolean isPalindrom(String str) {
		if (str == null || str.length() == 1) {
			return true;
		}
		int i = 0, j = str.length() - 1;
		while ( i !=  j) {
			if (str.charAt(i) != str.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
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
		
		System.out.println("Done: Testing String related puzzles");
	}
}
