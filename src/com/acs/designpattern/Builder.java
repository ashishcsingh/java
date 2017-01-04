package com.acs.designpattern;

public class Builder {

	static class Robot {
		// All final to promote immutability.
		final private String head;
		final private String body;
		final private String color;
	
		// Builder pattern for Robot.
		static class RobotBuilder {
			// All params are copied as it is here.
			// Mandatory parameters - final.
			private final String head;
			private final String body;
			// Optional must have a default parameter.
			private String color = "black";
			
			// Take mandatory params here.
			public RobotBuilder(String head, String body) {
				this.head = head;
				this.body = body;
			}
			
			// Set optional params here.
			public RobotBuilder color(String color) {
				this.color = color;
				return this;
			}
			
			// The builder method that builds.
			public Robot build() {
				// Validations can be done here.
				if ("water-color".equals(color)) {
					throw new IllegalArgumentException("water color is not allowed");
				}
				Robot robot = new Robot(this);
				return robot;
			}
		}
		
		// Builder based constructor.
		private Robot(RobotBuilder builder) {
			head = builder.head;
			body = builder.body;
			color = builder.color;
		}
		
		// Some feature.
		public void describe() {
			System.out.println(" I am robot with head : " + head  + ", body : " + body + ", color : " + color);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Builder pattern example");
		System.out.println("Build Robot with mandatory head and body and add color in a single invocation");
		Robot steelRobot = new Robot.RobotBuilder("steel-head", "steel-body").color("silver-color").build();
		steelRobot.describe();
		Robot goldRobot = new Robot.RobotBuilder("gold-head", "gold-body").color("gold-color").build();
		goldRobot.describe();
	}
}
