package com.smart.concurrency.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.smart.concurrency.state.State;

public class Chopstick {

	private int id;
	private Lock lock;

	public Chopstick(int id) {
		this.id = id;
		// instantiate lock as a ReentrantLock
		this.lock = new ReentrantLock();
	}

	public boolean canPickUp(Philosopher philosopher, State state) throws InterruptedException {

		// try for 10ms to pick up chopstick
		if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			// display to console which chopstick was picked up (left/right)
			System.out.println(philosopher + " picked up " + state.toString() + " " + this);
			return true;
		}
		return false;
	}

	public void putDown(Philosopher philosopher, State state) {
		lock.unlock();
		System.out.println(philosopher + " put down " + this);
	}

	@Override
	public String toString() {
		return "Chopstick: " + id;
	}
}
