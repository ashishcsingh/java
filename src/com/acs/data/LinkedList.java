package com.acs.data;

import java.util.NoSuchElementException;

import com.acs.data.LinkedList;

public class LinkedList<E> implements List<E> {
	// Members:
	Node<E> first;
	Node<E> last;
	int size;

	// Inner classes.
	private static class Node<E> {
		E data;
		Node<E> next, prev;
		Node(Node<E> prev, E data, Node<E> next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	private class ListForwardIterator implements Iterator<E> {
		private Node<E> next;
		private Node<E> prev;
		private int nextIndex;
		ListForwardIterator(int index) {
			if (index >= size) {
				next = null;
			} else {
				next = node(index);
				nextIndex = index;
			}
		}
		
		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		@Override
		public E next() {
			prev = next;
			next = next.next;
			nextIndex++;
			return prev.data;
		}
		
	}
	
	Node<E> node(int index) {
		if (index < (size >> 1)) {
			Node<E> itr = first;
			for(int i=0; i<index; i++) {
				itr = itr.next;
			}
			return itr;
		} else {
			Node<E> itr = last;
			for(int i=size-1; i>index; i--) {
				itr = itr.prev;
			}
			return itr;
		}
	}
	
	// Methods:
	LinkedList() {}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public void add(E val) {
		addLast(val);
	}
	
	public void addLast(E val) {
		// TODO Auto-generated method stub
		Node<E> oldLast = last;
		Node<E> node = new Node<>(last, val, null);
		last = node;
		if (oldLast == null) {
			first = node;
		} else {
			oldLast.next = last;
		}
		size++;
	}
	
	public void addFirst(E val) {
		Node<E> oldFirst = first;
		Node<E> node = new Node<>(null, val, first);
		first = node;
		if (oldFirst == null) {
			last = node;
		} else {
			oldFirst.prev = first;
		}
		size++;
	}
	
	private void unlink(Node<E> node) {
		node.data = null;
		node.next = null;
		node.prev = null;
	}
	
	@Override
	public E remove() {
		// TODO Auto-generated method stub
		if (size == 0) {
			throw new NoSuchElementException();
		}
		Node<E> oldLast = last;
		E val = oldLast.data;
		if (size == 1) {
			first = last = null;
		} else {
			last.prev.next = null;
			last = last.prev;
		}
		unlink(oldLast);
		return val;
	}
	
	@Override
	public void insert(E val, int location) {
		if (location < 0 || location >= size) {
			throw new IndexOutOfBoundsException("Location : " + location + " is beyond size: " + size);
		}
		Node<E> nodeLoc = node(location);
		Node<E> newNode = new Node<>(nodeLoc.prev, val, nodeLoc);
		nodeLoc.prev = newNode;
		if (location > 0) {
			nodeLoc.prev.next = newNode;	
		} else {
			first = newNode;
		}
		size++;
	}
	
	@Override
	public void delete(int location) {
		if (location < 0 || location >= size) {
			throw new IndexOutOfBoundsException("Location : " + location + " is beyond size: " + size);
		}
		Node<E> nodeLoc = node(location);
		if(location == 0) {
			first = first.next;
			if (size == 1) {
				last = null;
			}
		} else if (location == size - 1) {
			last = last.prev;
		}
		size--;
		unlink(nodeLoc);
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ListForwardIterator(0);
	}
	
	public Iterator<E> iterator(int index) {
		// TODO Auto-generated method stub
		return new ListForwardIterator(index);
	}

	public static void main(String... args) {
		System.out.println("Testing LinkedList class");
		System.out.println("Started...");
		List<Integer> list = new LinkedList<>();
		for(int i=0; i<10; i++) {
			list.add(i+1);
		}
		assert list.remove() == 10;
		assert list.remove() == 9;
		assert list.size() == 8;
		System.out.println("Done");
	}
}
