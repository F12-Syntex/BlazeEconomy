package com.devnecs.economy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.devnecs.database.MySql;
import com.devnecs.database.Sqlite;
import com.devnecs.main.Blaze;
import com.devnecs.storage.Storage;

public class EconomyHandler {

	private List<Account> accounts;
	
	public EconomyHandler() {}

	public void loadEconomy() {

		if(Blaze.getInstance().configManager.settings.storage == Storage.YAML) {
			this.setBalance(Blaze.getInstance().configManager.yaml_storage.accounts);	
			Bukkit.getLogger().info("Loaded YAML storage.");
			Bukkit.getLogger().info("Number of accounts loaded: " + this.getBalance().size());
		}
		if(Blaze.getInstance().configManager.settings.storage == Storage.MYSQL) {
			
			MySql mySql = Blaze.getInstance().handler.mysql;
			
			try {
				mySql.connect();
				this.setBalance(mySql.getAccounts());
				Bukkit.getLogger().info("Connected to MySQL data base.");
				Bukkit.getLogger().info("Number of accounts loaded: " + this.getBalance().size());
				
			} catch (SQLException e) {
				Bukkit.getLogger().info("Cannot connect to database, defaulting to yaml.");
				this.setBalance(Blaze.getInstance().configManager.yaml_storage.accounts);	
			}
				
		
		}
		
		if(Blaze.getInstance().configManager.settings.storage == Storage.SQLITE) {
		
			Sqlite mySql = Blaze.getInstance().handler.sqlite;
			
			try {
				mySql.connect();
				this.setBalance(mySql.getAccounts());
				Bukkit.getLogger().info("Connected to SQLite data base.");
				Bukkit.getLogger().info("Number of accounts loaded: " + this.getBalance().size());
				
			} catch (SQLException e) {
				Bukkit.getLogger().info("Cannot connect to database, defaulting to yaml.");
				this.setBalance(Blaze.getInstance().configManager.yaml_storage.accounts);	
			}
				
			
			
		}
		
	}
	
	public List<Account> getData(){
		return this.accounts;
	}
	
	public List<Account> sortedData(int size){
		final List<Account> data = this.getData();
		Collections.sort(data, new Comparator<Account>(){
		   public int compare(Account o1, Account o2){
		      return o1.getBalance() > o2.getBalance() ? -1 : 1;
		   }
		});
		
		if(size < 0 || size > data.size()) return data;
		
		return data.subList(0, size);
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
