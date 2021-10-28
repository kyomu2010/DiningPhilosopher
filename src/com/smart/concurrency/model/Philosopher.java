package com.smart.concurrency.model;

import java.util.Random;

import com.smart.concurrency.state.State;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private Random random;
	private int eatingCounter;
	private volatile boolean isFull = false; // store isFull var in main RAM to ensure it is visible (consistent state)
												// for all threads - if Philosopher is full stop thread

	public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
		this.random = new Random();
	}

	@Override
	public void run() {

		try {
			while (!isFull) {
				think();
				if (leftChopstick.canPickUp(this, State.LEFT)) {
					if (rightChopstick.canPickUp(this, State.RIGHT)) {
						eat();
						rightChopstick.putDown(this, State.RIGHT);
					}
					leftChopstick.putDown(this, State.LEFT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		Thread.sleep(random.nextInt(1000));
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating...");
		this.eatingCounter++;
		Thread.sleep(random.nextInt(1000));
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getEatingCounter() {
		return eatingCounter;
	}

	@Override
	public String toString() {
		return "Philosopher " + id;
	}

}
