package com.devnecs.economy;

import java.util.UUID;

public class Transaction {
	
	private long date;
	private double amount;
	private UUID sender;
	private TransactionType transactionType;
	
	public Transaction(long date, double amount, UUID sender, TransactionType transactionType) {
		this.date = date;
		this.amount = amount;
		this.sender = sender;
		this.transactionType = transactionType;
	}
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public UUID getSender() {
		return sender;
	}
	public void setSender(UUID sender) {
		this.sender = sender;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public static Transaction generate(double amount, UUID sender, TransactionType transactionType) {
		Transaction transaction = new Transaction(System.currentTimeMillis(), amount, sender, transactionType);
		return transaction;
	}
	
}
