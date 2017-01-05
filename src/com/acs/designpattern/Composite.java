package com.acs.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite design pattern
 * Same method exists for parent/child.
 * @author asingh
 *
 */
public class Composite {

	// A common functionality for both parent and child.
	static interface Traversable {
		public void traverse();
	}
	
	// Child implementing the same functionality.
	private static class Leaf implements Traversable {
		private String value;
		public String getValue() {
			return value;
		}
		public Leaf(String value) {
			this.value = value;
		}
		@Override
		public void traverse() {
			System.out.println(" child : " + value);
		}
	}
	
	// Parent too implementing the same method "traversal()"
	private static class CompositeLeaf extends Leaf {
		private List<Traversable> list = new ArrayList<>();
		public boolean add(Traversable leaf) {
			return list.add(leaf);
		}
		public CompositeLeaf(String value) {
			super(value);
		}
		// This makes parent and child both Traversable.
		@Override
		public void traverse() {
			System.out.println(" parent : " + getValue());
			list.forEach(leaf -> leaf.traverse());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(" Composite design pattern ");
		//   child1    parent2   parent3
		//             child2    parent2
		//						 child2
		List<Traversable> leafs = new ArrayList<>();
		leafs.add(new Leaf("1: child1"));
		leafs.add(new CompositeLeaf("2: parent2") {{ add(new Leaf("2: child2"));}});
		leafs.add(new CompositeLeaf("3: parent3") {{ add(leafs.get(1));}});
		System.out.println("Parent and child all implement the same method traversal()");
		// Invoke the same method across parent/child.
		leafs.forEach( leaf -> leaf.traverse());
	}

}
