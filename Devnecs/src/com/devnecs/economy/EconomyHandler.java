package com.devnecs.economy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EconomyHandler {

	private List<Account> balance;
	
	public EconomyHandler() {}
	
	public void loadEconomy() {
		this.setBalance(new ArrayList<Account>());
	}

	public List<Account> getBalance() {
		return balance;
	}

	public void setBalance(List<Account> balance) {
		this.balance = balance;
	}
	
	public boolean hasAccount(UUID account) {
		return balance.stream().filter(i -> i.getKey().compareTo(account) == 0).count() > 0;
	}
	
	public void registerUser(UUID user) {
		Account account = new Account(user, 0);
		this.balance.add(account);
	}
	
	public double getAmount(UUID user) {
		
		if(!hasAccount(user)) {
			this.registerUser(user);
		}
		
		return balance.stream().filter(i -> i.getKey().compareTo(user) == 0).findFirst().get().getBalance();
	}
	
	public void addAmount(UUID user, double amount) {
	
		if(!hasAccount(user)) {
			this.registerUser(user);
		}
		
		balance.stream().filter(i -> i.getKey().compareTo(user) == 0).findFirst().get().add(amount);
	}
	
	public void takeAmount(UUID user, double amount) {
		
		if(!hasAccount(user)) {
			this.registerUser(user);
		}
		
		balance.stream().filter(i -> i.getKey().compareTo(user) == 0).findFirst().get().take(amount);
	}
	
	public void setAmount(UUID user, double amount) {
		
		if(!hasAccount(user)) {
			this.registerUser(user);
		}
		
		balance.stream().filter(i -> i.getKey().compareTo(user) == 0).findFirst().get().set(amount);
	}
	
}
