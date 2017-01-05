package com.acs.designpattern;

/**
 * Abstract Factory design pattern
 * Creates factory and then instances based upon the specified type passed.
 * @author asingh
 *
 */
public class AbstractFactory {

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
	
	// An interface implemented by all concrete types.
	static interface Color {
		void fill();
	}
	
	static class Red implements Color {
		@Override
		public void fill() {
			System.out.println("Filling Red");
		}
	}
	
	static class Orange implements Color {
		@Override
		public void fill() {
			System.out.println("Filling Orange");
		}
	}
	
	
	static class Blue implements Color {
		@Override
		public void fill() {
			System.out.println("Filling Blue");
		}
	}
	
	static abstract class AbstractDisplayFactory {
		public abstract Shape getShape(String type);
		public abstract Color getColor(String type);
	}
	
	// ShapeFactory is a factory pattern that creates concrete instances.
	static class ShapeFactory extends AbstractDisplayFactory {
		@Override
		public Shape getShape(String type) {
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
		
		@Override
		public Color getColor(String type) {
			return null;
		}

	}
	
	// ColorFactory is a factory pattern that creates concrete instances.
	static class ColorFactory extends AbstractDisplayFactory {
		@Override
		public Color getColor(String type) {
			if ("red".equalsIgnoreCase(type)) {
				return new Red();
			}
			if ("orange".equalsIgnoreCase(type)) {
				return new Orange();
			}
			if ("blue".equalsIgnoreCase(type)) {
				return new Blue();
			}
			return null;
		}
		@Override
		public Shape getShape(String type) {
			return null;
		}
	}
	
	// Factory producer class.
	static class FactoryProducer {
		public static AbstractDisplayFactory getFactory(String type) {
			if ("shape".equalsIgnoreCase(type)) {
				return new ShapeFactory();
			}
			if ("color".equalsIgnoreCase(type)) {
				return new ColorFactory();
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(" Abstract Factory example ");
		System.out.println("Creating Circle FactoryProducer ");
		Shape circle = FactoryProducer.getFactory("shape").getShape("circle");
		circle.draw();
		System.out.println("Creating Square FactoryProducer ");
		Shape square = FactoryProducer.getFactory("shape").getShape("square");
		square.draw();
		System.out.println("Creating Red from FactoryProducer ");
		Color red = FactoryProducer.getFactory("color").getColor("red");
		red.fill();
		System.out.println("Creating Orange from FactoryProducer ");
		Color orange = FactoryProducer.getFactory("color").getColor("orange");
		orange.fill();
	}

}
