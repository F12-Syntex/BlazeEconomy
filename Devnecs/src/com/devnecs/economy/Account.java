package com.devnecs.economy;

import java.util.UUID;

public class Account {

	private UUID key;
	private double balance;
	
	public Account(UUID key, double balance) {
		this.key = key;
		this.balance = balance;
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void add(double amount) {
		this.balance += amount;
	}
	
	public void take(double amount) {
		this.balance -= amount;
	}
	
	public void set(double amount) {
		this.balance = amount;
	}
	
}
