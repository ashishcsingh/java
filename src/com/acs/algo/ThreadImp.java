package com.acs.algo;

import java.util.LinkedList;
import java.util.List;

public class ThreadImp {
    static class ReentrantLock {
        private Thread lockingThread = null;
        private boolean isLocked = false;
        private int lockingCount = 0;
        public synchronized void lock() throws InterruptedException {
            Thread currentThread = Thread.currentThread();
            while(isLocked && currentThread != lockingThread) {
                wait();
            }
            isLocked = true;
            lockingThread = currentThread;
            ++lockingCount;
        }
        public synchronized void unlock() {
            Thread currentThread = Thread.currentThread();
            if (currentThread == lockingThread) {
                --lockingCount;
                if (lockingCount == 0) {
                    isLocked = false;
                    notify();
                }
            } else {
                throw new IllegalMonitorStateException();
            }
        }
    }
    
    public static class Holdtill {
    	private int capacity = 0;
    	public Holdtill(int capacity) {
    		this.capacity = capacity;
    	}
    	public synchronized void hold() throws InterruptedException {
    		--capacity;
    		System.out.println("Holding");
    		while (capacity > 0) {
    			wait();
    		}
    		if (capacity <= 0) {
    			notifyAll();
        		System.out.println("Unholding all");
    		}
    	}
    }
    
    static interface SafeList<T> {
    	public void append(T data);
    	public T get(int index);
    	public int size();
    }
    
    static class SafeListImpl <T> implements SafeList<T> {
    	private List<T> list = new LinkedList<>();
    	@Override
    	synchronized public void append(T data) {
    		list.add(data);
    	}
    	@Override
    	public T get(int index) {
    		return list.get(index);
    	}
    	@Override
    	public int size() {
    		return list.size();
    	}
    }
    
	public static void main(String[] args) {
		System.out.println("Start: Testing Thread related puzzles");
		ReentrantLock lock = new ReentrantLock();
		new Thread ( () -> {
			try {
				lock.lock();
				lock.lock();
	            System.out.println("double locked by thread");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				lock.unlock();
				 System.out.println("double unlocked by thread");
			}
		}).start();
	
	Holdtill ht = new Holdtill(4);
	for (int i = 0; i <5; i++) {
		new Thread( () -> {
			try {
				ht.hold();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	final int fixed = 10;
	SafeList<Integer> slist = new SafeListImpl<>();
	for (int i = 0; i <5; i++) {
		new Thread( () -> {
			slist.append(fixed);
		}).start();
	}
	// Wait for 1 ms
	try {
		Thread.sleep(1);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println(slist.size());	
	
	// Wait for clean output.
	// Choosing sleep over join to synchronize.
	try {
		Thread.sleep(1);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
		System.out.println("Done: Testing Thread related puzzles");
	}
}
