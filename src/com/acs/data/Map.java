package com.acs.data;

import java.util.List;

public interface Map<K, V> {
	public V get(K key);
	public V put(K key, V value);
	public static interface Entry<K,V> {
		K getKey();
		V getValue();
		K setKey(K key);
		V setValue(V value);
	}
	// Choosing List over Set because Comparator for unknown types need detailed implementations.
	public List<K> keyList();
	public List<Entry<K, V>> entryList();
}
