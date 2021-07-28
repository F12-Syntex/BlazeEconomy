
package com.devnecs.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.GUI.Baltop;
import com.devnecs.GUI.GUI;
import com.devnecs.main.Blaze;

public class BalanceTop extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	GUI gui = new Baltop(player);
    	gui.open();
		return;    	
    }

    @Override

    public String name() {
        return "baltop";
    }

    @Override
    public String info() {
        return "displays the top 5 users balance.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Blaze.getInstance().configManager.permissions.baltop;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		return tabCompleter;
	}
	

}