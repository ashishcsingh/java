package com.acs.designpattern;

/**
 * Adapter design pattern
 * Wrapper class to meet contractual requirement.
 * @author asingh
 *
 */
public class Adapter {

	static interface Automobile {
		void drive();
	}
	
	static class Car implements Automobile {
		public void drive() {
			System.out.println("Moving by car");
		}
	}
	
	// An existing Bike that meets similar drive() requirement.
	static class Bike {
		public void Ride() {
			System.out.println("Moving by bike");
		}
	}
	
	// Adapter on top of bike to meet drive requirements.
	static class BikeAutomobileAdapter implements Automobile {
		private Bike bike;
		public BikeAutomobileAdapter(Bike bike) {
			this.bike = bike;
		}
		// Riding fulfills drive requirements.
		@Override
		
		public void drive() {
			bike.Ride();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(" Adapter design pattern ");
		System.out.println(" Drive() on car ");
		new Car().drive();
		System.out.println(" Drive() on bike using AutomobileAdapter ");
		Bike bike = new Bike();
		Automobile bikeDriving = new BikeAutomobileAdapter(bike);
		bikeDriving.drive();
	}
}
