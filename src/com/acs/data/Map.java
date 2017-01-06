package com.acs.data;

import java.util.Set;

public interface Map<K, V> {
	public Set<K> keySet();
	public Set<Entry<K,V>> entrySet();
	public V get(K key);
	public V put(K key, V value);
	public static interface Entry<K,V> extends Comparable<Entry<K,V>> {
		K getKey();
		V getValue();
		K setKey(K key);
		V setValue(V value);
	}
}
