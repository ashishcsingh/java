package com.acs.algo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class NodeImp {
	/**
	 * Keeping it simple Graph related programs, no getters/setters.
	 *
	 */
	public static class Node {
		Node left, right;
		int data;
		Node() {}
		Node(Node l, Node r, int d) {
			left = l;
			right = r;
			data = d;
		}
	}
	
	/**
	 * Computes height of tree.
	 * @param node
	 * @param heightParent
	 * @return
	 */
	public static int heightTree(Node node, int heightParent) {
		if (node == null) {
			return heightParent;
		}
		return Math.max(heightTree(node.left, heightParent + 1),
				heightTree(node.right, heightParent + 1));
	}
	
	/**
	 * Computes height of the tree.
	 * @param node
	 * @param heightParent
	 * @param cache
	 * @return
	 */
	public static int heightTree(Node node, int heightParent, Map<Node, Integer> cache) {
		if (node == null) {
			return heightParent;
		}
		if (cache.get(node) != null) {
			return cache.get(node);
		}
		 int height = Math.max(heightTree(node.left, heightParent + 1),
				heightTree(node.right, heightParent + 1));
		 cache.put(node, height);
		 return height;
	}
	
	/**
	 * checks if on path one can find sum matching the exactly.
	 * @param node
	 * @param sumLeft
	 * @return
	 */
	public static boolean isSumOnPath(Node node, int sumLeft) {
		if (node == null) {
			return false;
		}
		if (sumLeft - node.data == 0) {
			return true;
		}
		return isSumOnPath(node.left, sumLeft - node.data) || isSumOnPath(node.right, sumLeft - node.data);
	}
	
	/**
	 * Checks if tree is balanced.
	 * By checking if either left/right nodes are off by more than 1 or they are imbalanced.
	 * @param node
	 * @param heightParent
	 * @param cache
	 * @return
	 */
	public static boolean isTreeBalanced(Node node, int heightParent, Map<Node, Integer> cache) {
		if (node == null) {
			return true;
		}
		if (Math.abs(heightTree(node.left, heightParent + 1) - heightTree(node.right, heightParent + 1)) > 1 ||
				!isTreeBalanced(node.left, heightParent + 1, cache) || !isTreeBalanced(node.right, heightParent + 1, cache)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Breadth first search.
	 * @param root
	 * @param data
	 * @return
	 */
	public static Node bfs(Node root, int data) {
		if (root == null) {
			return null;
		}
		Queue<Node> q = new LinkedList<>();
		q.add(root);
		while(!q.isEmpty()) {
			Node node = q.remove();
			if (node.data == data) {
				return node;
			}
			if (node.left != null) {
				q.add(node.left);
			}
			if (node.right != null) {
				q.add(node.right);
			}
		}
		return null;
	}
	
	/**
	 * Depth first search.
	 * @param root
	 * @param data
	 * @return
	 */
	public static Node dfs(Node root, int data) {
		if (root == null) {
			return null;
		}
		Stack<Node> s = new Stack<>();
		s.add(root);
		while(!s.isEmpty()) {
			Node node = s.pop();
			if (node.data == data) {
				return node;
			}
			if (node.left != null) {
				s.add(node.left);
			}
			if (node.right != null) {
				s.add(node.right);
			}
		}
		return null;
	}
	
	/**
	 * Print all children at same height in the same line.
	 * By maintaining two queues, print new line when parent queue becomes empty.
	 * @param root
	 */
	public static void levelOrdering(Node root) {
		Queue<Node> q = new LinkedList<>();
		Queue<Node> c = new LinkedList<>();
		q.add(root);
		while(!q.isEmpty() || !c.isEmpty()) {
			// process node and print it.
			Node node = q.remove();
			System.out.print(" " + node.data);
			// extract children and populate c.
			if (node.left != null) {
				c.add(node.left);
			}
			if (node.right != null) {
				c.add(node.right);
			}
			// when parent q is empty then print new line and move c to q.
			if (q.isEmpty()) {
				q = c;
				c = new LinkedList<>();
				System.out.print("\n");
			}
		}
	}
	
	/**
	 * Rotate tree.
	 * @param root
	 */
	public static void rotate(Node root) {
		if (root == null) {
			return;
		}
		Stack<Node> s = new Stack<>();
		s.add(root);
		while(!s.isEmpty()) {
			Node node = s.pop();
			if (node != null) {
				Node temp = node.left;
				node.left = node.right;
				node.right = temp;
			}
			if (node.left != null) {
				s.push(node.left);
			}
			if (node.right != null) {
				s.push(node.right);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Start: Testing Graph related puzzles");
		
		// Node:
		// 		   n1
		//		n2	 *
		// 	n3     n4
		Node n1 = new Node(new Node(new Node(null, null, 3), new Node(null, null, 4), 2), null, 1);
		System.out.println("Height of tree: " + heightTree(n1, 0));
		System.out.println("Height of tree with cache/dynamic: " + heightTree(n1, 0, new HashMap<>()));
		System.out.println("SumOnPath of tree for 3: " + isSumOnPath(n1, 3));
		System.out.println("isTreeBalanced: " + isTreeBalanced(n1, 0, new HashMap<>()));
		System.out.println("Level ordering without rotation: ");
		levelOrdering(n1);
		rotate(n1);
		System.out.println("Level ordering after rotation: ");
		levelOrdering(n1);
		// Means no assert failure.
		System.out.println("Done: Testing Graph related puzzles");
		
	}
}
