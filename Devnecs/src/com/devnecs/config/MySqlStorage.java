package com.devnecs.config;

import java.util.ArrayList;
import java.util.List;

import com.devnecs.economy.Account;

public class MySqlStorage extends Config{

	public com.devnecs.storage.Storage storage = com.devnecs.storage.Storage.MYSQL;
	
	public String host = "localhost";
	public String port = "3306";
	public String database = "devnecs";
	public String username = "root";
	public String password = "";
	
	public MySqlStorage(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Settings.storage.type", this.storage.name()));
		this.items.add(new ConfigItem("Settings.credentials.host", this.host));
		this.items.add(new ConfigItem("Settings.credentials.port", this.port));
		this.items.add(new ConfigItem("Settings.credentials.database", this.database));
		this.items.add(new ConfigItem("Settings.credentials.username", this.username));
		this.items.add(new ConfigItem("Settings.credentials.password", this.password));
	
	}
	
	public List<Account> accounts = new ArrayList<Account>();

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {
	
		this.host = this.getConfiguration().getString("Settings.credentials.host");
		this.port = this.getConfiguration().getString("Settings.credentials.port");
		this.database = this.getConfiguration().getString("Settings.credentials.database");
		this.username = this.getConfiguration().getString("Settings.credentials.username");
		this.password = this.getConfiguration().getString("Settings.credentials.password");
		
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "storage";
	}

	
}
