package com.smart.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.smart.concurrency.constants.Constants;
import com.smart.concurrency.model.Chopstick;
import com.smart.concurrency.model.Philosopher;

public class App {

	public static void main(String[] args) {

		ExecutorService executorService = null;
		Philosopher[] philosophers = null;

		try {
			philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
			Chopstick[] chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

			for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
				chopsticks[i] = new Chopstick(i);
			}

			// instantiate thread pool to match number of Philosophers
			executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);

			for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
				// for Philosophers sitting around a table then for a
				// given Philosopher, if i = 1, the left chopstick will also be 1, the right
				// chopstick will be 2 (1+1 % number_of_chopsticks)
				// if i = 2, the left chopstick will also be 2, the right chopstick will be 3
				// etc.
				philosophers[i] = new Philosopher(i, chopsticks[i],
						chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
				executorService.execute(philosophers[i]);
			}

			try {
				Thread.sleep(Constants.SIMULATION_RUNNING_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// stop threads
			for (Philosopher p : philosophers) {
				p.setFull(true);
			}

		} finally {
			executorService.shutdown();
			// wait until all thread tasks have completed
			while (!executorService.isTerminated()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (Philosopher p : philosophers) {
				System.out.println(p + " eats " + p.getEatingCounter());
			}
		}
	}

}
