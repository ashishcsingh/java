package com.acs.algo;

import java.util.HashSet;
import java.util.Set;

public class SetImp {

	public static int findDuplicate(int[] data) {
		Set<Integer> set = new HashSet<>(data.length);
		for (int i: data) {
			// If set can not add then return found duplicate.
			if(!set.add(i)) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public static void main(String[] args) {
		System.out.println("Testing setImp puzzles");
		assert findDuplicate(new int[]{1,2,3,3}) == 3 : "Should be 3";
	}
}
