package com.acs.designpattern;

/**
 * Factory design pattern
 * Creates instances based upon the specified type passed.
 * @author asingh
 *
 */
public class Factory {

	// An interface implemented by all concrete types.
	static interface Shape {
		void draw();
	}
	
	static class Circle implements Shape {
		@Override
		public void draw() {
			System.out.println("Drawing Circle");
		}
	}
	
	static class Rectangle implements Shape {
		@Override
		public void draw() {
			System.out.println("Drawing Rectangle");
		}
	}
	
	static class Square implements Shape {
		@Override
		public void draw() {
			System.out.println("Drawing Square");
		}
	}
	
	// ShapeFactory is a factory pattern that creates concrete instances.
	static class ShapeFactory {
		// A static method avoids creating an instance.
		public static Shape getShape(String type) {
			if ("circle".equalsIgnoreCase(type)) {
				return new Circle();
			}
			if ("rectangle".equalsIgnoreCase(type)) {
				return new Rectangle();
			}
			if ("square".equalsIgnoreCase(type)) {
				return new Square();
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(" Factory example ");
		System.out.println("Creating Circle from ShapeFactory ");
		Shape circle = ShapeFactory.getShape("circle");
		circle.draw();
		System.out.println("Creating Rectangle from ShapeFactory ");
		Shape rectangle = ShapeFactory.getShape("rectangle");
		rectangle.draw();
		System.out.println("Creating Square from ShapeFactory ");
		Shape square = ShapeFactory.getShape("square");
		square.draw();
	}

}
