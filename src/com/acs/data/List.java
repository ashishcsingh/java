package com.acs.data;

public interface List<E> extends com.acs.data.Iterable<E> {
	void add(E val);
	E remove();
	void insert(E val, int location);
	void delete(int location);
	int size();
	boolean isEmpty();
	E get(int index);
}
