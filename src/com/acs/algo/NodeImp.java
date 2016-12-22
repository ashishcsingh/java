package com.acs.algo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
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
		Node(int val) {
			this.data = val;
		}
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
	 * In a loop.
	 * @param root
	 */
	public static void levelOrdering2(Node root) {
		if (root == null) {
			throw new IllegalArgumentException("root null");
		}
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		while (!q.isEmpty()) {
			int size = q.size();
			for(int i=0; i<size; i++) {
				Node node = q.remove();
				System.out.print(" " + node.data);
				if (node.left != null) {
					q.add(node.left);
				}
				if (node.right != null) {
					q.add(node.right);
				}
			}
			System.out.println();
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
	
	/**
	 * Inserts node into tree created from val.
	 * @param root
	 * @param val
	 * @return
	 */
	public static Node insertToBst(Node root, int val) {
		Node newNode = new Node(val);
		if (root == null) {
			return newNode;
		}
		Node cur = root, prev = root;
		while (cur != null) {
			prev = cur;
			if (cur.data > val) {
				cur = cur.left;
			} else {
				cur = cur.right;
			}
		}
		if (prev.data > val) {
			prev.left = newNode;
		} else {
			prev.right = newNode;
		}
		return root;
	}
	
	/**
	 * Keep progressing in the same direction of a & b when conflict return that node.
	 * @param root
	 * @param a
	 * @param b
	 * @return
	 */
	public static int lca(Node root, int a, int b) {
		if (root == null) {
			throw new IllegalArgumentException("Cannot locate common parent");
		}
		if (root.data > a && root.data > b) {
			return lca(root.left, a, b);
		}
		if (root.data <= a && root.data <= b) {
			return lca(root.right, a, b);
		}
		return root.data;
	}
	
	/**
	 * Returns left most width.
	 * @param node
	 * @param width
	 * @return
	 */
	public static int minWidth(Node node, int width) {
		if (node == null) {
			return width;
		}
		return Math.min(minWidth(node.left, width - 1), minWidth(node.right, width + 1));
	}
	
	/**
	 * Returns right most width.
	 * @param node
	 * @param width
	 * @return
	 */
	public static int maxWidth(Node node, int width) {
		if (node == null) {
			return width;
		}
		return Math.max(maxWidth(node.left, width - 1), maxWidth(node.right, width + 1));
	}
	
	/**
	 * Prints all elems at the desired width.
	 * @param node
	 * @param desired
	 * @param current
	 */
	public static void printWidthElem(Node node, int desired, int current) {
		if (node != null) {
			printWidthElem(node.left, desired, current - 1);
			if (desired == current) {
				System.out.print(" " + node.data);
			}
			printWidthElem(node.right, desired, current + 1);
		}
	}
	
	/**
	 * Print elems from the left most point to right most point layer by layer.
	 * @param node
	 */
	public static void verticalOrdering(Node node) {
		int minWidthLen = minWidth(node, 0);
		int maxWidthLen = maxWidth(node, 0);
		for(int i=minWidthLen; i<=maxWidthLen; i++) {
			printWidthElem(node, i, 0);
			System.out.println();
		}
	}
	
	/**
	 * Is this tree balanced?
	 * @param root
	 * @return
	 */
	public static boolean isBst(Node root) {
		if (root == null) {
			return true;
		}
		if (root.left != null && root.data <= root.left.data) {
			return false;
		}
		if (root.right != null && root.data > root.right.data) {
			return false;
		}
		return isBst(root.left) && isBst(root.right);
	}
	
	/**
	 * Is same value accross the tree.
	 * @param root
	 * @return
	 */
	public static boolean isSameTree(Node root) {
		if (root == null) {
			return true;
		}
		if (root.left != null && root.data != root.left.data) {
			return false;
		}
		if (root.right != null && root.data != root.right.data) {
			return false;
		}
		return isSameTree(root.left) && isSameTree(root.right);
	}
	
	
	/**
	 * Tester methods.
	 * @param args
	 */
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
		System.out.println("Level ordering for random tree");
		Node randomTree = null;
		for(int i=0; i<10; i++) {
			randomTree = insertToBst(randomTree, Math.abs(new Random().nextInt()) % 10);
		}
		levelOrdering2(randomTree);
		System.out.println("Level ordering for random tree");
		Node serialedNode = null;
		for(int i=5; i<10; i++) {
			serialedNode = insertToBst(serialedNode, i + 1);
		}
		for(int i=0; i<5; i++) {
			serialedNode = insertToBst(serialedNode, i + 1);
		}
		levelOrdering2(serialedNode);
		System.out.println("Vertical ordering for serial tree");
		System.out.println("Is serialized tree balanced: " + isBst(serialedNode));
		verticalOrdering(serialedNode);
		// Means no assert failure.
		System.out.println("Done: Testing Graph related puzzles");
		
	}
}
