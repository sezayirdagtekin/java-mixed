package com;

public class ProducerConsumer {

	private static Object lock = new Object();

	private static int[] buffer;
	private static int count;

	public static void main(String[] args) throws InterruptedException {
		buffer = new int[10];
		count = 0;

		Producer producer = new Producer();
		Consumer consumer = new Consumer();

		Runnable produceTask = () -> {
			for (int i = 0; i < 50; i++) {
				producer.produce();
			}
			System.out.println("Done producing");
		};

		Runnable consumerTask = () -> {
			for (int i = 0; i < 50; i++) {
				consumer.consume();
			}
			System.out.println("Done consuming");
		};

		Thread consumerThread = new Thread(consumerTask);
		Thread producerThread = new Thread(produceTask);
		consumerThread.start();
		producerThread.start();

		consumerThread.join();
		producerThread.join();

		System.out.println("Data is  in buffer:" + count);

	}

	static boolean isEmpty(int[] buffer2) {
		return count == 0;
	}

	static boolean isFull(int[] buffer) {
		return count == buffer.length;
	}

	static class Producer {
		void produce() {
			synchronized (lock) {
				if (isFull(buffer)) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
				buffer[count++] = 1;
				lock.notify();
			}

		}

	}

	static class Consumer {
		
		void consume() {
			synchronized (lock) {
				if (isEmpty(buffer)) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
					}
				}
				buffer[--count] = 1;
				lock.notify();
			}

		}

	}
}
