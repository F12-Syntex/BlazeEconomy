
package com.devnecs.commands;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.database.MySql;
import com.devnecs.database.Sqlite;
import com.devnecs.main.Blaze;
import com.devnecs.utils.MessageUtils;

public class Storage extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	System.out.println(args.length);
    	
    	if((args.length != 3 && args.length != 2)) {
    		MessageUtils.sendRawMessage(player, "&c/blaze storage {set/view} {yaml/mysql/sqlite}");
    		return;
        }

    	if(args[1].equalsIgnoreCase("set")) {
    		
    		if(args[2].isEmpty()) {
    			MessageUtils.sendRawMessage(player, "&c/blaze storage {set/view} {yaml/mysql/sqlite}");
    			return;
    		}
    		
			com.devnecs.storage.Storage prev = Blaze.getInstance().configManager.settings.storage;
			Bukkit.getLogger().info("Attempting to change storage data from " + prev.name() + " to " + args[2]);

			
			if(prev.name().equals(args[2])) {
	    		MessageUtils.sendMessage(player, "&7The current storage type is already &a" + Blaze.getInstance().configManager.settings.storage.name());
	    		return;
			}
			
			if(com.devnecs.storage.Storage.YAML.name().equalsIgnoreCase(args[2])) {
				
				Blaze.getInstance().handler.update();
				
				MessageUtils.sendMessage(player, "&aSwitched to yaml storage.");
				
				Blaze.getInstance().configManager.settings.storage = com.devnecs.storage.Storage.YAML;
				Blaze.getInstance().configManager.settings.update();
				

				Blaze.getInstance().economyHandler.loadEconomy();
				
				return;
			}
			
			if(com.devnecs.storage.Storage.MYSQL.name().equalsIgnoreCase(args[2])) {
				
				Blaze.getInstance().handler.update();
				
				MySql mySql = Blaze.getInstance().handler.mysql;
				try {
					
					mySql.connect();
					MessageUtils.sendMessage(player, "&aDatabase connected!");
				
					Blaze.getInstance().configManager.settings.storage = com.devnecs.storage.Storage.MYSQL;
					Blaze.getInstance().configManager.settings.update();
				

					Blaze.getInstance().economyHandler.loadEconomy();
				
				} catch (SQLException e) {
					MessageUtils.sendMessage(player, "&cUnable to connect to database. " + e.getLocalizedMessage());
				}
				
				return;
			} 
			
			if(com.devnecs.storage.Storage.SQLITE.name().equalsIgnoreCase(args[2])) {
				Blaze.getInstance().handler.update();
				
				Sqlite mySql = Blaze.getInstance().handler.sqlite;
				try {
					
					mySql.connect();
					MessageUtils.sendMessage(player, "&aDatabase connected!");
				
					Blaze.getInstance().configManager.settings.storage = com.devnecs.storage.Storage.SQLITE;
					Blaze.getInstance().configManager.settings.update();
				

					Blaze.getInstance().economyHandler.loadEconomy();
				
				} catch (SQLException e) {
					MessageUtils.sendMessage(player, "&cUnable to connect to database. " + e.getLocalizedMessage());
				}
				
				return;
			}
    		
    		MessageUtils.sendRawMessage(player, "&c/blaze storage {set/view} {yaml/mysql/sqlite}");
    		return;
    	
    	}else if(args[1].equalsIgnoreCase("view")) {
    		MessageUtils.sendMessage(player, "&7The current storage type is &a" + Blaze.getInstance().configManager.settings.storage.name());
    		return;
    	}
    	
    	
		MessageUtils.sendRawMessage(player, "&c/blaze storage {set/view} {yaml/mysql/sqlite}");
		return;
		
    }

    @Override

    public String name() {
        return "storage";
    }

    @Override
    public String info() {
        return "storage data.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Blaze.getInstance().configManager.permissions.admin;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		for(com.devnecs.storage.Storage i : com.devnecs.storage.Storage.values()) {
			tabCompleter.createEntry("set." + i.name());
		}
		tabCompleter.createEntry("view");
		return tabCompleter;
	}
	

}