package com.devnecs.storage;

import com.devnecs.database.MySql;
import com.devnecs.database.Sqlite;
import com.devnecs.main.Blaze;

public class StorageHandler {
	
	public MySql mysql;
	public Sqlite sqlite;
	
	public StorageHandler() {
		this.mysql = new MySql();
		this.sqlite = new Sqlite();
	}
	
	public void update() {

		if(Blaze.getInstance().configManager.settings.storage == com.devnecs.storage.Storage.YAML) {
			Blaze.getInstance().configManager.yaml_storage.update();
		}
		if(Blaze.getInstance().configManager.settings.storage == com.devnecs.storage.Storage.MYSQL) {
			this.mysql.update();
		}
		if(Blaze.getInstance().configManager.settings.storage == com.devnecs.storage.Storage.SQLITE) {
			this.sqlite.update();
		}
	}
	
	public void close() {
		if(Blaze.getInstance().configManager.settings.storage == com.devnecs.storage.Storage.MYSQL) {
			this.mysql.disconnect();
		}
		if(Blaze.getInstance().configManager.settings.storage == com.devnecs.storage.Storage.SQLITE) {
			this.sqlite.disconnect();
		}
	}
	
}
