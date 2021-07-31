package com.devnecs.config;

import java.util.ArrayList;
import java.util.List;

import com.devnecs.economy.Account;

public class SqlLiteStorage extends Config{

	
	public SqlLiteStorage(String name, double version) {
		super(name, version);
		this.extention = "db";
	}
	
	public List<Account> accounts = new ArrayList<Account>();

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "storage";
	}

	
}
