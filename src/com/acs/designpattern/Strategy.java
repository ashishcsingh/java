package com.acs.designpattern;

public class Strategy {

	// Functionality declaration. 
	static interface FlyingAbility {
		public void fly();
	}
	
	// Multiple implementations of the functionality.
	static class CannotFly implements FlyingAbility {
		@Override
		public void fly() {
			System.out.println("Cannot fly");
		}
	}
	
	static class CanFly implements FlyingAbility {
		@Override
		public void fly() {
			System.out.println("Can fly");
		}
	}
	
	// Consumer of the functionality.
	static class Dog {
		private FlyingAbility flyingAbility;
		Dog(FlyingAbility ability) {
			flyingAbility = ability;
		}
		public void setFlyingAbility(FlyingAbility ability) {
			flyingAbility = ability;
		}
		public FlyingAbility getFlyingAbility() {
			return flyingAbility;
		}
		public void tryFlying() {
			flyingAbility.fly();
		}
 	}
	
	public static void main(String[] args) {
		System.out.println("Strategy pattern example of changing functionality on the fly");
		System.out.println("Explore default dog's flying power ");
		Dog dog = new Dog(new CannotFly());
		dog.tryFlying();
		System.out.println("Giving flying power ");
		dog.setFlyingAbility(new CanFly());
		dog.tryFlying();
	}
}
