package com.devnecs.economy;

import java.util.List;
import java.util.UUID;

import com.devnecs.main.Blaze;

public class Account {

	private UUID key;
	private double balance;
	private List<Transaction> transations;
	
	public Account(UUID key, double balance, List<Transaction> transactions) {
		this.key = key;
		this.balance = balance;
		this.transations = transactions;
	}

	public void addTransaction(Transaction transaction) {
		this.transations.add(transaction);
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
	
	public void add(UUID sender, double amount) {
		this.balance += amount;
		this.addTransaction(Transaction.generate(amount, sender, TransactionType.ADMIN_SEND));
		Blaze.getInstance().configManager.storage.update();
	}
	
	public void take(UUID sender, double amount) {
		this.balance -= amount;
		this.addTransaction(Transaction.generate(amount, sender, TransactionType.ADMIN_TAKE));
		Blaze.getInstance().configManager.storage.update();
	}
	
	public void set(UUID sender, double amount) {
		this.balance = amount;
		this.addTransaction(Transaction.generate(amount, sender, TransactionType.ADMIN_SET));
		Blaze.getInstance().configManager.storage.update();
	}

	public List<Transaction> getTransations() {
		return transations;
	}

	public void setTransations(List<Transaction> transations) {
		this.transations = transations;
	}
	
}
