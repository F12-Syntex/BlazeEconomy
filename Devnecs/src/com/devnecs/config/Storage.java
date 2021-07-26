package com.devnecs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.devnecs.economy.Account;
import com.devnecs.economy.EconomyHandler;
import com.devnecs.economy.Transaction;
import com.devnecs.economy.TransactionType;
import com.devnecs.main.Blaze;

public class Storage extends Config{

	public com.devnecs.storage.Storage storage = com.devnecs.storage.Storage.YAML;
	
	public Storage(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Settings.storage.type", this.storage.name()));
	}
	
	public List<Account> accounts = new ArrayList<Account>();

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {
	
		ConfigurationSection section = this.configuration.getConfigurationSection("Accounts");
		
		if(!this.configuration.isConfigurationSection("Accounts")) return;
		
		for(String users : section.getKeys(false)) {
			
			ConfigurationSection account = section.getConfigurationSection(users);
			
			UUID uuid = UUID.fromString(users);
			
			double balance = account.getDouble("Balance");
			
			ConfigurationSection transactions = account.getConfigurationSection("Transactions");
			
			List<Transaction> transactionData = new ArrayList<Transaction>();
			
			if(account.isConfigurationSection("Transactions")) {
				for(String key : transactions.getKeys(false)) {
					
					ConfigurationSection transaction = transactions.getConfigurationSection(key);
					
					TransactionType type = TransactionType.valueOf(transaction.getString("type"));
					double amount = transaction.getDouble("amount");
					UUID user = UUID.fromString(transaction.getString("user"));
					long time = Long.valueOf(transaction.getString("time"));
					
					Transaction singleTransaction = new Transaction(time, amount, user, type);
					
					transactionData.add(singleTransaction);
					
				}	
			}
			
			Account data = new Account(uuid, balance, transactionData);
			accounts.add(data);
		}
		
	}
	
	public void update() {
		
		EconomyHandler handler = Blaze.getInstance().economyHandler;
		final List<Account> accounts = handler.getData();
		
		FileConfiguration configuration = this.getConfiguration();
		
		for(Account account : accounts) {
			
			configuration.set("Accounts." + account.getKey().toString() + ".Balance", account.getBalance());

			int identification = 1;
			
			for(Transaction transaction : account.getTransations()) {
				
				configuration.set("Accounts." + account.getKey().toString() + ".Transactions." + identification + ".type", transaction.getTransactionType().name());
				configuration.set("Accounts." + account.getKey().toString() + ".Transactions." + identification + ".amount", transaction.getAmount());
				configuration.set("Accounts." + account.getKey().toString() + ".Transactions." + identification + ".user", transaction.getSender().toString());
				configuration.set("Accounts." + account.getKey().toString() + ".Transactions." + identification + ".time", transaction.getDate());
				
				identification++;
			}
			
		}
		
		this.save();
		
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "storage";
	}

	
}
