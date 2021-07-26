package com.devnecs.economy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.devnecs.main.Blaze;

public class EconomyHandler {

	private List<Account> accounts;
	
	public EconomyHandler() {}
	
	public void loadEconomy() {
		this.setBalance(Blaze.getInstance().configManager.storage.accounts);
	}
	
	public List<Account> getData(){
		return this.accounts;
	}

	public List<Account> getBalance() {
		return accounts;
	}

	public void setBalance(List<Account> balance) {
		this.accounts = balance;
	}
	
	public boolean hasAccount(UUID account) {
		
		for(Account data : this.accounts) {
			if(data.getKey().compareTo(account) == 0) return true;
		}
		
		return false;
	}
	
	public void registerUser(UUID user) {
		Account account = new Account(user, 0, new ArrayList<Transaction>());
		this.accounts.add(account);
	}
	
	public Account getAccount(OfflinePlayer user) {
		
		if(!hasAccount(user.getUniqueId())) {
			this.registerUser(user.getUniqueId());
		}
		
		for(Account data : this.accounts) {
			if(data.getKey().compareTo(user.getUniqueId()) == 0) return data;
		}
		
		return null;
	}
	
}
