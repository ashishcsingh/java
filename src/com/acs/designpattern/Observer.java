package com.acs.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer design pattern
 * Observer pattern building update invoking relationship between publisher/subscriber.
 * @author asingh
 *
 */
public class Observer {
	
	// Update method that gets called from publishers.
	static abstract class StockObserver {
		protected StockRecorder stockRecorder;
		public abstract void update(float value);
	}
	
	// Observer or Subscriber connected to receive updates. 
	static class ApplePadObserver extends StockObserver {
		private float value;
		ApplePadObserver(StockRecorder stockRecorder) {
			this.stockRecorder = stockRecorder;
			stockRecorder.register(this);
		}
		public void update(float value) {
			this.value = value;
			System.out.println("New tracked value on Pad " + this.value);
		}
	}
	
	// Observer or Subscriber connected to receive updates. 
	static class ApplePhoneObserver extends StockObserver {
		private float value;
		public void update(float value) {
			this.value = value;
			System.out.println("New tracked value on Phone " + this.value);
		}
		public ApplePhoneObserver(StockRecorder stockRecorder) {
			this.stockRecorder = stockRecorder;
			stockRecorder.register(this);
		}
	}
	
	// Publisher invoking updates on Subscribers.
	static class StockRecorder {
		private List<StockObserver> observers = new ArrayList<>();
		private float value;
		public void register(StockObserver observer) {
			observers.add(observer);
		}
		public void unRegister(StockObserver observer) {
			observers.remove(observer);
		}
		public void notifyAllObservers() {
			for (StockObserver observer : observers) {
				observer.update(value);
			}
		}
		public float getValue() {
			return value;
		}
		public void setValue(float value) {
			this.value = value;
			notifyAllObservers();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Observer design pattern example");
		StockRecorder stockRecorder = new StockRecorder();
		ApplePadObserver applePadObserver = new ApplePadObserver(stockRecorder);
		ApplePhoneObserver applePhoneObserver = new ApplePhoneObserver(stockRecorder);
		stockRecorder.setValue(1.0f);
		stockRecorder.setValue(2.0f);
		System.out.println("Unregistering phone");
		stockRecorder.unRegister(applePhoneObserver);
		stockRecorder.setValue(3.0f);
		System.out.println("Unregistering pad");
		stockRecorder.unRegister(applePadObserver);
		stockRecorder.setValue(5.0f);
	}
}
