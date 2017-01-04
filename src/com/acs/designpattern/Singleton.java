package com.acs.designpattern;

/**
 * Singleton design pattern
 * static instance initialized in a thread safe getInstance static method.
 * @author asingh
 *
 */
public class Singleton {
	
	// one static instance per JVM.
	private static Singleton instance = null;
	// Constructor must be private.
	private Singleton() {
	} 
	// Lazy thread safe initialization.
	public static Singleton getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized(Singleton.class) {
			// Re check before creating an instance.
			if (instance == null) {
				instance = new Singleton();
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Creating singleton instance ");
		Singleton singleton = Singleton.getInstance();
		Singleton anotherInstance = Singleton.getInstance();
		System.out.println("Validating singleton is singleton");
		assert singleton == anotherInstance;
	}

}
