package com;

public class Person {
	private Object lock = new Object();
	private long num;

	public Person(long num) {
		this.num = num;
	}

	public long getNum() {
		synchronized (lock) {
			return num;
		}
	}

	void incrementValue() {
		synchronized (lock) {
			num = num + 1;
		}

	}

}
