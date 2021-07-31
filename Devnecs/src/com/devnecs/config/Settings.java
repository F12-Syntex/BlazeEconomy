package com.devnecs.config;

import com.devnecs.storage.Storage;

public class Settings extends Config{

	public Storage storage = Storage.YAML;;
	
	public Settings(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Settings.storage.type", this.storage.name()));
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {
		this.storage = Storage.valueOf(this.getConfiguration().getString("Settings.storage.type"));
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "";
	}
	
	public void update() {
		this.getConfiguration().set("Settings.storage.type", this.storage.name());
		this.save();
	}
	
}
