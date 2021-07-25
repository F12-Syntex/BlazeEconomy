
package com.devnecs.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;

public class Balance extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	double balance = economy.getBalance(player);
    	Blaze.getInstance().configManager.messages.send(player, "user_balance", "%balance%", (Math.round(balance*10.0)/10.0)+"");    	
    	
    }

    @Override

    public String name() {
        return "balance";
    }

    @Override
    public String info() {
        return "displays a users money.";
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
		
		List<SubCommand> commands = Blaze.getInstance().CommandManager.getCommands();
		
		for(SubCommand i : commands) {
			tabCompleter.createEntry(i.name());
		}
		
		return tabCompleter;
	}
	

}