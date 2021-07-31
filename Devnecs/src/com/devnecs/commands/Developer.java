
package com.devnecs.commands;

import java.sql.SQLException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;

public class Developer extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	try {
			Blaze.getInstance().handler.sqlite.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @Override

    public String name() {
        return "developer";
    }

    @Override
    public String info() {
        return "developer command.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Blaze.getInstance().configManager.permissions.basic;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		return tabCompleter;
	}
	

}