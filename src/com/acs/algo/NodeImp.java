package com.acs.algo;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
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
		public Node(int d, Node l, Node r) {
			left = l;
			right = r;
			data = d;
		}
	}
	
	/**
	 * TreeNode to doudbly linkedlist.
	 * left -> prev
	 * right -> next.
	 * Last node's next points to first.
	 * First node's prev points to last.
	 * @param tree node
	 * @return doubly node
	 */
	public static Node buildDoubly(Node node) {
		// When null then return null.
		if (node == null) {
			return null;
		}
		Node head = node;
		Node end = node;
		// Explore and connect left doubly list. 
		if (node.left != null) {
			Node leftHead = buildDoubly(node.left);
			Node leftEnd = leftHead.left;
			// Current node is after left side doubly list.
			node.left = leftEnd;
			leftEnd.right = node;
			head = leftHead;
		}
		// Explore and connect right doubly list. 
		if (node.right != null) {
			Node rightHead = buildDoubly(node.right);
			Node rightEnd = rightHead.left;
			// Current node is before right side doubly list.
			node.right = rightHead;
			rightHead.left = node;
			end = rightEnd;
		}
		head.left = end;
		end.right = head;
		return head;
	}
	
	public static boolean isBST(Node root) {
		if (root == null) {
			return true;
		}
		if (root.left != null && root.data < root.left.data) {
			return false;
		}
		if (root.right != null && root.data > root.right.data) {
			return false;
		}
		return isBST(root.left) && isBST(root.right);
	}
	
	public static int maxBST(Node root) {
		if (root == null) {
			return 0;
		}
		int ls = maxBST(root.left);
		int rs = maxBST(root.right);
		if (isBST(root)) {
			return ls + rs + 1;
		} else {
			return Math.max(ls, rs);
		}
	}
	
	/**
	 * Node with Inorder iterators.
	 * @author asingh
	 *
	 */
	public static class NodeIterable implements Iterable<Integer> {
		public NodeIterable left, right;
		public int data;
		NodeIterable(int data) {
			this.data = data;
		}
		NodeIterable(NodeIterable left, NodeIterable right, int data) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		@Override
		public Iterator<Integer> iterator() {
			return new NodeIterator(this);
		}
		private class NodeIterator implements Iterator<Integer> {
			Stack<NodeIterable> stack = new Stack<>();
			NodeIterator(NodeIterable node) {
				traverseLefts(node);
			}
			private void traverseLefts(NodeIterable node) {
				if (node == null) {
					return;
				}
				stack.push(node);
				traverseLefts(node.left);
			}
			@Override
			public boolean hasNext() {
				return !stack.isEmpty();
			}
			@Override
			public Integer next() {
				if (hasNext()) {
					NodeIterable node = stack.pop();
					traverseLefts(node.right);
					return node.data;
				}
				throw new NoSuchElementException();
			}
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
		Deque<Node> s = new ArrayDeque<>();
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
	 * DFS and swap content of tree.
	 * @param node
	 */
	public static void mirrorTree(Node node) {
		if (node != null) {
			mirrorTree(node.left);
			// Swap content of left and right nodes.
			{
				Node temp = node.left;
				node.left = node.right;
				node.right = temp;
			}
			mirrorTree(node.right);
		}
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
	 * Find path in a maze using DFS.
	 * @param maze
	 * @param x
	 * @param y
	 * @param desX
	 * @param desY
	 * @param path
	 * @return
	 */
	public static boolean findPath(char[][] maze, int x, int y, int desX, int desY, Stack<Entry<Integer, Integer>> path) {
		// Detect bad input, can add more conditions about x, y.
		if (maze == null || maze[y][x] == '1') {
			throw new IllegalArgumentException();
		}
		// If reached destination then print the stack path.
		if (x == desX && y == desY) {
			maze[desY][desX] = '+';
			for (Entry<Integer, Integer> point : path) {
				System.out.print(point.getKey() + ", " + point.getValue() + " ==> ");
			}
			System.out.println(y + ", " + x);
			return true;
		}
		// If the current value as a possible valid point.
		path.push(new AbstractMap.SimpleEntry<Integer, Integer>(y, x));
		// Must mark current point as visited.
		maze[y][x] = '1';
		// Explore all neighbors that are valid.
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (x + i >= 0 && x + i <= desX && y + j >= 0 && y + j <= desY) {
					if (maze[y + j][x + i] == '0') {
						return findPath(maze, x + i, y + j, desX, desY, path);
					}
				}
			}
		}
		// At this point the current point was not reaching a valid point so remove it.
		maze[y][x] = '0';
		path.pop();
		return false;
	}
	
	static class ListNode {
		public int data;
		public ListNode next;
		ListNode(int data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		ListNode() {}
	}
	
	/**
	 * Given K sorted linkedList, return one sorted list.
	 * @param lists
	 * @return
	 */
	public static ListNode mergeKSortedLists(List<ListNode> lists) {
		PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.size(), (a, b) -> a.data - b.data);
		for (ListNode node : lists) {
			pq.offer(node);
		}
		ListNode head = null, cur = null, node = null;
		while(!pq.isEmpty()) {
			node = pq.poll();
			if (head == null) {
				head = cur = node;
			} else {
				cur.next = node;
				cur = cur.next;
			}
			if (node.next != null) {
				pq.offer(node.next);
			}
		}
		return head;
	}
	
	/**
	 * Finds the path to destination Node.
	 *      1
	 *     2  3
	 *   4      7
	 *   
	 *   
	 *   path -> 1-2-4
	 * @param root
	 * @param data
	 */
	public static void pathToNode(Node root, int target, Deque<Integer> current, Deque<Integer> result) {
		if (root == null || !result.isEmpty()) {
			return;
		}
		current.addLast(root.data);
		if (root.data == target) {
			result.addAll(current);
			return;
		}
		if (root.left != null) {
			pathToNode(root.left, target, current, result);
		}
		if (root.right != null) {
			pathToNode(root.right, target, current, result);
		}
		current.removeLast();
	}
	
	public static int lcaNode(Node root, int d1, int d2) {
		Deque<Integer> result1 = new LinkedList<Integer>();
		pathToNode(root, d1, new LinkedList<Integer>(), result1);
		Deque<Integer> result2 = new LinkedList<Integer>();
		pathToNode(root, d2, new LinkedList<Integer>(), result2);
		if (result1.isEmpty() || result2.isEmpty()) {
			return -1;
		}
		Iterator<Integer> itr1 = result1.iterator();
		Iterator<Integer> itr2 = result2.iterator();
		int ancestor = -1;
		while (itr1.hasNext() && itr2.hasNext()) {
			int value1 = itr1.next();
			int value2 = itr2.next();
			if (value1 != value2) {
				return ancestor;
			}
			ancestor = value1;
		}
		return ancestor;
	}
	
	
	/**
	 * Finds minimum range to cover elements in all the lists.
	 * @param lists
	 * @return
	 */
	public static int minRange(List<ListNode> lists) {
		PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.size(), (a, b) -> (a.data - b.data));
		int max = Integer.MIN_VALUE, result = Integer.MAX_VALUE;
		for(ListNode node : lists) {
			pq.offer(node);
			max = Math.max(max, node.data);
		}
		result = Math.min(result, max - pq.peek().data + 1);
		while(!pq.isEmpty()) {
			ListNode node = pq.poll();
			if (node.next != null) {
				pq.offer(node.next);
				max = Math.max(max, node.next.data);
				result = Math.min(result, max - pq.peek().data + 1);
			} else {
				break;
			}
		}
		return result;
	}
	
	/**
	 * Compute max diameter of a tree.
	 * @param node
	 * @return
	 */
	public static int depth(Node node) {
		if (node == null || (node.left == null && node.right == null)) {
			return 0;
		}
		// From depth of left and right.
		int depth = Math.max(depth(node.left), depth(node.right));
		// From height of left and right.
		int heightLeft = node.left == null ? 0 : height(node.left) + 1;
		int heightRight = node.right == null ? 0 : height(node.right) + 1;
		// Max of depth and combined left/right heights.
		return Math.max(depth, heightLeft + heightRight);
	}
	
	public static int height(Node node) {
		if (node == null || (node.left == null && node.right == null)) {
			return 0;
		}
		return Math.max(height(node.left), height(node.right)) + 1;
	}
	
	/**
	 * Find second smallest in a Tree.
	 * https://www.careercup.com/question?id=5196022759292928
	 */
	public static int secondSmallest(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Node empty");
		}
		int smallest = node.data;
		int second = Integer.MAX_VALUE;
		Deque<Node> deq = new LinkedList<>();
		deq.push(node);
		while (!deq.isEmpty()) {
			Node cur = deq.pop();
			if (cur.data != smallest) {
				second = Math.min(second, cur.data);
			}
			if (cur.left != null) {
				deq.push(cur.left);
			}
			if (cur.right != null) {
				deq.push(cur.right);
			}
		}
		return second;
	}

   public static class NodeWithParent {
        public NodeWithParent left, right, parent;
        public int data;
        NodeWithParent(int data) {
            this.data = data;
        }
    };
    
    static NodeWithParent dfs(NodeWithParent root, int data) {
        if (root != null) {
            if (root.data == data) {
                return root;
            }
            NodeWithParent left = dfs(root.left, data);
            if (left != null) {
                return left;
            }
            NodeWithParent right = dfs(root.right, data);
            if (right != null) {
                return right;
            }
        }
        return null;
    }
    
    static int heightFromRoot(NodeWithParent node) {
        if (node == null) {
            return 0;
        }
        int result = 1;
        if (node.parent != null) {
           result = heightFromRoot(node.parent) + 1;
        }
        return result;
    }
    
    static public NodeWithParent lcaWithParent(NodeWithParent root, int d1, int d2) {
        NodeWithParent nodeLeft = dfs(root, d1);
        NodeWithParent nodeRight = dfs(root, d2);
        if (nodeLeft == null) {
            return null;
        }
        if (nodeRight == null) {
            return null;
        }
        int heightLeft = heightFromRoot(nodeLeft);        
        int heightRight = heightFromRoot(nodeRight);
        if (heightLeft > heightRight) {
            for (int i = 0;  i < heightLeft - heightRight; i++) {
                nodeLeft = nodeLeft.parent;
            }
        } else {
             for (int i = 0;  i < heightRight - heightLeft; i++) {
                nodeRight = nodeRight.parent;
             }
        }
        while (nodeLeft.data != nodeRight.data) {
            nodeLeft = nodeLeft.parent;
            nodeRight = nodeRight.parent;
        }
        return nodeLeft;
    }
    
    static public interface NestedInteger
    {
        /** @return true if this NestedInteger holds a single integer, rather than a nested list */
        boolean isInteger();
     
        /** @return the single integer that this NestedInteger holds, if it holds a single integer
         * Return null if this NestedInteger holds a nested list */
        Integer getInteger();
     
        /** @return the nested list that this NestedInteger holds, if it holds a nested list
         * Return null if this NestedInteger holds a single integer */
        List<NestedInteger> getList();
    }
    
    static class NestedIntegerImpl implements NestedInteger {
    	int data;
    	List<NestedInteger> list;
    	boolean isDigit = false;
    	
    	NestedIntegerImpl(int data) {
    		this.data = data;
    		this.isDigit = true;
    	}
    	
    	NestedIntegerImpl(List<NestedInteger> list) {
    		this.list = list;
    		this.isDigit = false;
    	}
    	
		@Override
		public boolean isInteger() {
			if (isDigit) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Integer getInteger() {
			return data;
		}

		@Override
		public List<NestedInteger> getList() {
			return list;
		}
    }
    
    public static int depthSum (List<NestedInteger> input)
    {
        return depthSumHeight(input, 1);
    }

    private static int depthSumHeight(List<NestedInteger> input, int height) {
       int sum = 0;
       for (NestedInteger ni : input) {
          if (ni.isInteger()) {
             sum += ni.getInteger() * height;
          } else {
             sum += depthSumHeight(ni.getList(), height + 1);
          }
       }
       return sum;
    }
    
    /**
     * Given child, parent, isLeft relationship build tree.
     * https://www.careercup.com/question?id=5668114807128064
     * @author asingh
     *
     */
    static class Relation {
    	Integer parent;
    	Integer child;
    	boolean isLeft;
    	Relation(Integer child, Integer parent, boolean isLeft) {
    		this.child = child;
    		this.parent = parent;
    		this.isLeft = isLeft;
    	}
    }
    
    /**
     * Builds tree using Relations.
     * https://www.careercup.com/question?id=5668114807128064
     * @param relations
     * @return
     */
    public static Node buildTree(List<Relation> relations) {
    	Node root = null;
    	Map<Integer, Node> map = new HashMap<>();
    	for (Relation r : relations) {
    		if (r.parent == null) {
    			map.putIfAbsent(r.child, new Node(r.child));
    			root = map.get(r.child);
    			continue;
    		}
			Node node = new Node(r.child);
			map.putIfAbsent(r.parent, new Node(r.parent));
			Node parent = map.get(r.parent);
			if (r.isLeft) {
				parent.left = node;
			} else {
				parent.right = node;
			}
			map.put(r.child, node);
    	}
    	return root;
    }
    

	/**
	 * 
	 * Covert right sided tree to upside down.
	 *     1
	 *   2   3
	 * 4  5
	 * 
	 *  To 
	 *     4
	 *   5  2
	 *     3  1
	 *   
	 * https://www.careercup.com/question?id=6266917077647360
	 * @param root
	 * @return
	 */
	public static Node upSideDown(Node root) {
		if (root == null) {
			return root;
		}
		Deque<Node> stack = createStack(root);
		return createTree(stack);
	}
	
	private static Deque<Node> createStack(Node root) {
		Deque<Node> stack = new ArrayDeque<>();
		stack.push(root);
		Node cur = stack.peek();
		while(cur.left != null) {
			stack.push(cur.right);
			stack.push(cur.left);
			cur = stack.peek();
		}
		return stack;
	}
	
	private static Node createTree(Deque<Node> stack) {
		Node root = stack.pop();
		Node cur = root;
		while(!stack.isEmpty()) {
			Node left = stack.pop();
			Node right = stack.pop();
			cur.left = left;
			cur.right = right;
			cur = cur.right;
		}
		return root;
	}
	
	static class Point {
		int x;
		int y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		Point() {}
		public String toString() {
			return "x : " + x + " y : " + y;
		}
	}
	
	
	/**
	 * Track water path, scene[][] contains height of different objects.
	 * @param scene
	 * @param point
	 * @param result
	 * @param tmpPath
	 */
	public static void waterPath(int[][] scene, int prevHeight, Point point, List<Point> tmpPath, List<List<Point>> result) {
		if (point.x >= scene.length || point.y < 0 || point.y >= scene[0].length || scene[point.x][point.y] > prevHeight) {
			result.add(new ArrayList<>(tmpPath));
			return;
		}
		if (scene[point.x][point.y] <= prevHeight) {
			tmpPath.add(point);
			waterPath(scene, scene[point.x][point.y], new Point(point.x + 1, point.y - 1), tmpPath, result);
			waterPath(scene, scene[point.x][point.y], new Point(point.x + 1, point.y), tmpPath, result);
			waterPath(scene, scene[point.x][point.y], new Point(point.x + 1, point.y + 1), tmpPath, result);
			tmpPath.remove(tmpPath.size() - 1);
		}
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
		
		// Check dedupContacts
		// c1..4 should one connected contact and c5 should be another set.
		Map<String, List<String>> contactToEmails = new HashMap<>();
		contactToEmails.put("c1", Arrays.asList("email1", "email2"));
		contactToEmails.put("c2", Arrays.asList("email1", "email3"));
		contactToEmails.put("c3", Arrays.asList("email3", "email6"));
		contactToEmails.put("c4", Arrays.asList("email6", "email7"));
		contactToEmails.put("c5", Arrays.asList("email8", "email9"));
		dedupContacts(contactToEmails);
		
		char[][] maze = new char[][] {
			{ '0', '1', '0', '0', '0'},
			{ '0', '1', '0', '1', '0'},
			{ '0', '1', '0', '1', '0'},
			{ '0', '0', '0', '1', '0'},
		};
		System.out.println("Using DFS : ");
		findPath(maze, 0, 0, 4, 3, new Stack<Entry<Integer, Integer>>());
		
		// Test simple mirror.
		Node m3 = new Node(new Node(1), new Node(5), 3);
		mirrorTree(m3);
		assert m3.left.data == 5 : "after swapping it should be 5";
		assert m3.right.data == 1 : "after swapping it should be 1";
		
		ListNode nl1 = new ListNode(1, new ListNode(5, new ListNode(7, new ListNode(9, null))));
		ListNode nl2 = new ListNode(2, new ListNode(4, new ListNode(8, new ListNode(10, null))));
		ListNode nl3 = new ListNode(3, new ListNode(6, new ListNode(11, new ListNode(12, null))));
		List<ListNode> lists = Arrays.asList(nl1, nl2, nl3);
		ListNode sortedListNode = mergeKSortedLists(lists);
		for(int i = 1; i<=12; i++) {
			assert sortedListNode.data == i;
			System.out.print(sortedListNode.data + ", ");
			sortedListNode = sortedListNode.next;
		}
		
		ListNode nl4 = new ListNode(10, new ListNode(50, new ListNode(90, null)));
		ListNode nl5 = new ListNode(1, new ListNode(26, null));
		ListNode nl6 = new ListNode(100, null);
		List<ListNode> lists2 = Arrays.asList(nl4, nl5, nl6);
		
		System.out.println();
		assert minRange(lists2) == 75;
		
		/*
		 *         3
		 *        2 4
		 *       1   5
		 */
		NodeIterable n12345 = new NodeIterable(new NodeIterable(null, null, 2), new NodeIterable(null, null, 4), 3);
		n12345.left.left = new NodeIterable(1);
		n12345.right.right = new NodeIterable(5);
		assert n12345.data == 3;
		assert n12345.left.data == 2;
		assert n12345.left.left.data == 1;
		assert n12345.right.data == 4;
		assert n12345.right.right.data == 5;
		System.out.println("should be 1,2,3,4,5 while iterating inoder");
		for(Integer val: n12345) {
			System.out.print(val + ", ");
		}
		System.out.println();
		System.out.println("Another attempt");
		Iterator<Integer> itr = n12345.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next() + ", ");
		}
		
		Node node12345 = new Node(new Node(null, null, 2), new Node(null, null, 4), 3);
		node12345.left.left = new Node(1);
		node12345.right.right = new Node(5);
		assert node12345.data == 3;
		assert node12345.left.data == 2;
		assert node12345.left.left.data == 1;
		assert node12345.right.data == 4;
		assert node12345.right.right.data == 5;
		Node doublyNode = buildDoubly(node12345);
		Node tempNode = doublyNode.right;
		System.out.println("Print list");
		System.out.print(doublyNode.data + ", ");
		System.out.print(doublyNode.right.data + ", ");
		while (tempNode.right != doublyNode) {
			tempNode = tempNode.right;
			System.out.print(tempNode.data + ", ");
		}
		
		tempNode = doublyNode.left;
		System.out.println("Print list reverse");
		System.out.print(doublyNode.data + ", ");
		System.out.print(doublyNode.left.data + ", ");
		while (tempNode.left != doublyNode) {
			tempNode = tempNode.left;
			System.out.print(tempNode.data + ", ");
		}
		
		assert doublyNode.data == 1;
		assert doublyNode.right.data == 2;
		assert doublyNode.right.right.data == 3;
		assert doublyNode.right.right.right.data == 4;
		assert doublyNode.right.right.right.right.data == 5;
		assert doublyNode.right.right.right.right.right.data == 1;
		assert doublyNode.right.right.right.right.right.right.data == 2;
		assert doublyNode.left.data == 5;
		assert doublyNode.right.left.data == 1;
		assert doublyNode.right.right.left.data == 2;
		assert doublyNode.right.right.right.left.data == 3;
	
		/**
		 * 						6
		 * 					5		4			
		 * 			3
		 * 		1		2
		 * 
		 */
		Node nDeep = new Node(new Node(new Node(new Node(1), new Node(2), 3), null, 5), new Node(4), 6);
		assert height(nDeep) == 3; 
		assert depth(nDeep) == 4;
		assert nDeep.left.left.left.data == 1;
		
		Deque<Integer> resultPath = new LinkedList<>();
		pathToNode(nDeep, 2, new LinkedList<Integer>(), resultPath);
		System.out.println("Path to 2 should be : 6532 : " + resultPath);
		// Lowest common ancestor for a binary tree.
		// Found by capturing path from both the nodes and then checking the first match.
		assert lcaNode(nDeep, 1, 2) == 3;
		
		/**
		 *      2
		 *   2     5
		 *  2  3  5  10
		 */
		Node n225 = new Node(new Node(2), new Node(5), 2);
		n225.left = new Node(new Node(2), new Node(3), 2);
		n225.right = new Node(new Node(5), new Node(10), 5);
		assert secondSmallest(n225) == 3;
		
	   Node n123 = new Node(new Node(1), new Node(3), 2);
	   assert isBST(n123);
	   assert maxBST(n123) == 3;
	
       NodeWithParent n1Parent = new NodeWithParent(1);
       n1Parent.left = new NodeWithParent(2);
       n1Parent.left.parent = n1Parent;
       n1Parent.right = new NodeWithParent(3);
       n1Parent.right.parent = n1Parent;
       assert lcaWithParent(n1Parent, 2, 3) == n1Parent;
		
       // NestedInteger test.
       NestedInteger ni = new NestedIntegerImpl(Arrays.asList(new NestedIntegerImpl(1), new NestedIntegerImpl(1)));
       List<NestedInteger> listsNi = Arrays.asList(ni, new NestedIntegerImpl(2), ni);
       assert depthSum(listsNi) == 10;
       
       List<Relation> relations = Arrays.asList(
           new Relation(15, 20, true),
           new Relation(19, 80, true),
           new Relation(17, 20, false),
           new Relation(16, 80, false),
           new Relation(80, 50, false),
           new Relation(50, null, false),
           new Relation(20, 50, true));
       Node root = buildTree(relations);
       
       assert root.data == 50;
       assert root.left.data == 20;
       assert root.right.data == 80;
       
       Node n23456 = new Node(new Node(new Node(5), new Node(6), 4), new Node(3), 2);
       Node flippedNode = upSideDown(n23456);
       assert flippedNode.data == 5;
       assert flippedNode.left.data == 6;
       assert flippedNode.right.data == 4;
      
       int[][] scene = new int[][] {
    	   {0, 5, 0, 0},
    	   {2, 6, 3, 0},
    	   {1, 2, 3, 0},
    	   {1, 1, 1, 1},
       };
       List<List<Point>> paths = new ArrayList<>();
       waterPath(scene, 10, new Point(0, 1), new ArrayList<>(), paths);
       paths.forEach(System.out::println);
       
		System.out.println();
		
		// Means no assert failure.
		System.out.println("Done: Testing Graph related puzzles");
	}
}
