package com;

public class RaceCondition {

	public static void main(String[] args) throws InterruptedException {

		Person p = new Person(0l);

		Runnable runnable = () -> {
			for (int i = 0; i < 1_000; i++) {
				p.incrementValue();

			}
		};

		Thread[] thread = new Thread[10];
		for (int i = 0; i < thread.length; i++) {
			thread[i] = new Thread(runnable);
			thread[i].start();
		}

		for (int i = 0; i < thread.length; i++) {
			thread[i].join();
		}

		System.out.println(p.getNum());
	}

}
