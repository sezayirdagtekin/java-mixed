package com;

import java.io.IOException;

public class BasicThread {
	public static void main(String[] args) throws IOException {
		
		Runnable runnable = () -> {
			System.out.println("I am rruning current thread name: " + Thread.currentThread().getName());

		};

		Thread thread = new Thread(runnable);
		thread.setName("Sezo");
		 thread.start();

	}

}
