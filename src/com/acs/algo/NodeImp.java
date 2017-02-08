package com.acs.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class NodeImp {
	/**
	 * Keeping it simple Graph related programs, no getters/setters.
	 *
	 */
	public static class Node {
		public Node left, right;
		public int data;
		public Node() {}
		public Node(int val) {
			this.data = val;
		}
		public Node(Node l, Node r, int d) {
			left = l;
			right = r;
			data = d;
		}
	}
	
	public static class SingleNode {
		SingleNode next;
		int data;
		public SingleNode(int data) {
			this.data = data;
		}
		public SingleNode() {
		}
	}
	
	public static class DoubleNode {
		DoubleNode next, prev;
		int data;
		public DoubleNode(int data) {
			this.data = data;
		}
		public DoubleNode() {
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
	 * Add current node to end.
	 * Detect child node and print path.
	 * Remove last elem.
	 * @param node
	 * @param path
	 */
	public static void printPathToLeaf(Node node, LinkedList<Node> path) {
		// Null check and return.
		if (node == null) {
			return;
		}
		// Add current node to path.
		path.addLast(node);
		// Detect child node and print path.
		if (node.left == null && node.right == null) {
			for (Node n: path) {
				System.out.print(n.data + " ");
			}
			System.out.println();
		}
		// DFS.
		printPathToLeaf(node.left, path);
		printPathToLeaf(node.right, path);
		// Remove the current elem.
		path.removeLast();
	}
	
	/**
	 * Add last current node to currentPath.
	 * If child node and maxPath.size() < currentPath.size() then maxPath = currentPath
	 * DFS
	 * Remove last current node from currentPath.
	 * @param node
	 * @param currentPath
	 * @param maxPath
	 */
	public static void getLongestPath(Node node, LinkedList<Node> currentPath, LinkedList<Node> maxPath) {
		// Null node return.
		if (node == null) {
			return;
		}
		// Add last current node to currentPath.
		currentPath.addLast(node);
		// Detect child and set max path if currentPath is bigger.
		if (node.left == null && node.right == null) {
			if (currentPath.size() > maxPath.size()) {
				maxPath.clear();
				maxPath.addAll(currentPath);
			}
		}
		// DFS.
		getLongestPath(node.left, currentPath, maxPath);
		getLongestPath(node.right, currentPath, maxPath);
		currentPath.removeLast();
	}
	
	/**
	 * Checks if a loop exists.
	 * @param node
	 * @return
	 */
	public static boolean hasLoop(SingleNode node) {
		SingleNode fast = node, slow = node;
		while (fast != null && fast.next != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds the loop point if no loop then return null.
	 * @param start
	 * @return
	 */
	public static SingleNode getLoopPoint(SingleNode start) {
		SingleNode slow = start, fast = start, meetingPoint = null;
		while (fast != null && fast.next!= null) {
			fast = fast.next.next;
			slow = slow.next;
			if (slow == fast) {
				meetingPoint = fast;
				break;
			}
		}
		if (meetingPoint != null) {
			slow = start;
			while (slow != meetingPoint) {
				slow = slow.next;
				meetingPoint = meetingPoint.next;
			}		
		}
		return meetingPoint;
	}
	
	/**
	 * Check LinkedList is palindrome with no knowledge of length.
	 * @param start
	 * @return
	 */
	public static boolean isPalindrome(SingleNode start) {
		// Null checks.
		if (start == null || start.next == null) {
			return true;
		}
		// Detect end while pushing content to stack.
		Stack<Integer> stack = new Stack<>();
		SingleNode slow = start, fast = start;
		while (true) {
			if (fast == null) {
				break;
			}
			if (fast.next == null) {
				slow = slow.next;
				break;
			}
			stack.push(slow.data);
			fast = fast.next.next;
			slow = slow.next;
		}
		// Match stack's content with List.
		while (!stack.isEmpty()) {
			if (stack.peek() != slow.data) {
				return false;
			}
			stack.pop();
			slow = slow.next;
		}
		if (slow == null && stack.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Reverses the linked list.
	 * @param node
	 * @return
	 */
	public static SingleNode reverseList(SingleNode node) {
		if (node == null || node.next == null) {
			return node;
		}
		SingleNode prev = null, next = node, base = node;
		while (base != null) {
			next = base.next;
			base.next = prev;
			prev = base;
			base = next;
		}
		return prev;
	}
	
	/**
	 * Dedup Contacts with common emails
	 * Build hashMap email to contact.
	 * for c : contacts
	 *   if (c in visitedContacts)
	 *       continue;
	 * 	 setContacts = dfs(contact)
	 *   print setContacts
	 *   visitedContacts.remove(setContacts)
	 *    
	 */
	public static void dedupContacts(Map<String, List<String>> contactToEmails) {
		Map<String, List<String>> emailToContacts = buildEmailToContact(contactToEmails);
		Set<String> visitedContacts = new HashSet<>();
		for (String contact : contactToEmails.keySet()) {
			// Skip contact that is already visited.
			if (visitedContacts.contains(contact)) {
				continue;
			}
			// Print all next connectedContacts.
			Set<String> connectedContacts = dfsContacts(contact, contactToEmails, emailToContacts);
			System.out.println(connectedContacts);
			visitedContacts.addAll(connectedContacts);
		}
	}
	
	/**
	 * For each contact, for each email, add contact -> email.
	 * @param contactToEmails
	 * @return
	 */
	private static Map<String, List<String>> buildEmailToContact(Map<String, List<String>> contactToEmails) {
		Map<String, List<String>> emailToContacts = new HashMap<>();
		for (Entry<String, List<String>> entry : contactToEmails.entrySet()) {
			for (String email: entry.getValue()) {
				emailToContacts.putIfAbsent(email, new ArrayList<String>());
				emailToContacts.get(email).add(entry.getKey());
			}
		}
		return emailToContacts;
	}
	
	/**
	 * Get all contacts that are connected.
	 * @param contact
	 * @param contactToEmails
	 * @param emailToContacts
	 * @return
	 */
	private static Set<String> dfsContacts(String contact, Map<String, List<String>> contactToEmails,
			Map<String, List<String>> emailToContacts) {
		Set<String> visited = new HashSet<>();
		Stack<String> stack = new Stack<>();
		stack.push(contact);
		while(!stack.isEmpty()) {
			// Get the depth first search.
			String current = stack.pop();
			// Skip visited nodes.
			if (visited.contains(current)) {
				continue;
			}
			// Get all connections.
			for (String email : contactToEmails.get(current)) {
				stack.addAll(emailToContacts.get(email));
			}
			// Mark current as visited to avoid looping.
			visited.add(current);
		}
		return visited;
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
		System.out.println("Path to leaf ");
		printPathToLeaf(serialedNode, new LinkedList<>());
		System.out.println("Longest Path to leaf ");
		LinkedList<Node> maxPath = new LinkedList<>();
		getLongestPath(serialedNode, new LinkedList<>(), maxPath);
		for (Node n : maxPath) {
			System.out.print(n.data + " ");
		}
		System.out.println();
		
		// 1 -> 2 -> 3 -> 4 ->
		//      ^-  <-  <-|
		SingleNode singleNode = new SingleNode(1);
		singleNode.next = new SingleNode(2);
		singleNode.next.next = new SingleNode(3);
		singleNode.next.next.next = new SingleNode(4);
		singleNode.next.next.next.next = singleNode.next;
		System.out.println("Has Loop : " + hasLoop(singleNode));
		System.out.println("Find Loop point : " + getLoopPoint(singleNode).data);
		
		
		// 1 -> 2 -> 2 -> 1 : Yes!
		SingleNode palindrome = new SingleNode(1);
		palindrome.next = new SingleNode(2);
		palindrome.next.next = new SingleNode(2);
		palindrome.next.next.next = new SingleNode(1);
		System.out.println("Is Palindrome: " + isPalindrome(palindrome));
		
		// 1 -> 2 -> 2 -> 1 -> 3 : No!
		palindrome.next.next.next.next = new SingleNode(3);
		System.out.println("Is Palindrome (no): " + isPalindrome(palindrome));
		
		// Check reversing module.
		reverseList(palindrome);
		assert palindrome.data == 3;
		assert palindrome.next.data == 1;
		
		// Check dedupContacts
		// c1..4 should one connected contact and c5 should be another set.
		Map<String, List<String>> contactToEmails = new HashMap<>();
		contactToEmails.put("c1", Arrays.asList("email1", "email2"));
		contactToEmails.put("c2", Arrays.asList("email1", "email3"));
		contactToEmails.put("c3", Arrays.asList("email3", "email6"));
		contactToEmails.put("c4", Arrays.asList("email6", "email7"));
		contactToEmails.put("c5", Arrays.asList("email8", "email9"));
		dedupContacts(contactToEmails);
		
		// Means no assert failure.
		System.out.println("Done: Testing Graph related puzzles");
	}
}
