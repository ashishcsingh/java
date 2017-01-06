package com.acs.data;

import java.util.Set;
import java.util.TreeSet;

/**
 * JDK compliant HashMap get(), put(), entrySet() and keySet() implementations.
 * @author asingh
 *
 * @param <K>
 * @param <V>
 */
public class HashMap<K extends Comparable, V> implements Map<K, V> {
	private final int INTIAL_CAPACITY = 16;
	private int capacity = INTIAL_CAPACITY;
	private Node<K, V>[] table;
	private int count;
	private Set<K> keySet = new TreeSet<>();
	private Set<Map.Entry<K, V>> entrySet = new TreeSet<>();

	private static int hashKey(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	private int lookupIndex(Object key) {
		return (int) ((Math.abs(hashKey(key))) % capacity);
	}

	boolean isNode(Node<K, V> node, K key) {
		return node.getHash() == hashKey(key) && node.getKey().equals(key);
	}

	/**
	 * Previous Node would help attach the new Node in put()
	 * 
	 * @param startNode
	 * @param key
	 * @return Previous Node or the desired Node.
	 */
	private Node<K, V> getNodeOrPrevious(Node<K, V> startNode, K key) {
		int hashValue = hashKey(key);
		Node<K, V> cur = startNode, prev = startNode;
		while (cur != null) {
			if (cur.getHash() == hashValue && cur.equals(key)) {
				return cur;
			}
			prev = cur;
			cur = cur.next;
		}
		return prev;
	}

	private Node<K, V> createNode(K key, V value) {
		Node<K, V> node = new Node<>(hashKey(key), key, value);
		keySet.add(key);
		entrySet.add(node);
		count++;
		return node;
	}

	@SuppressWarnings("unchecked")
	HashMap() {
		table = new Node[capacity];
	}

	@SuppressWarnings("hiding")
	private class Node<K extends Comparable, V> implements Map.Entry<K, V> {
		K key;
		V value;
		int hash;
		Node<K, V> next;

		public Node(int hash, K key, V value) {
			this.hash = hash;
			this.key = key;
			this.value = value;

		}

		public int getHash() {
			return hash;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public K setKey(K key) {
			this.key = key;
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}

		@Override
		public int compareTo(Entry<K,V> o) {
			return o.getKey().compareTo(o.getKey());
		}

	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return entrySet;
	}

	@Override
	public V get(K key) {
		Node<K, V> startNode = table[lookupIndex(key)];
		if (startNode == null) {
			return null;
		}
		Node<K, V> locatedNode = getNodeOrPrevious(startNode, key);
		if (isNode(locatedNode, key)) {
			return locatedNode.getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		int index = lookupIndex(key);
		Node<K, V> startNode = table[index];
		if (startNode == null) {
			table[index] = createNode(key, value);
			return null;
		}
		Node<K, V> locatedNode = getNodeOrPrevious(startNode, key);
		if (isNode(locatedNode, key)) {
			V oldValue = locatedNode.getValue();
			locatedNode.setValue(value);
			return oldValue;
		}
		locatedNode.next = createNode(key, value);
		return null;
	}

	@Override
	public Set<K> keySet() {
		return keySet;
	}
	
	public static void main(String[] args) {
		System.out.println("HashMap implementation");
		Map<Integer, String> map = new HashMap<>();
		map.put(1,"One");
		map.put(2,"Two");
		map.put(3,"Three");
		assert map.get(1).equals("One");
		assert map.get(2).equals("Two");
		assert map.get(3).equals("Three");
		assert map.get(4) == null;
	}
}
