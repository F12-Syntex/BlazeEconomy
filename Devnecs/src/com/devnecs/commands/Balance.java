
package com.devnecs.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;
import com.devnecs.utils.MessageUtils;

public class Balance extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	if(args.length == 1) {
        	double balance = Blaze.getInstance().economyHandler.getAmount(player.getUniqueId());
    		Blaze.getInstance().configManager.messages.send(player, "user_balance", "%balance%", (Math.round(balance*10.0)/10.0)+"");    
    		return;
        }
    	
    	String name = "";
    	
    	for(int i = 1; i < args.length; i++) {
    		name+=args[i] + " ";
    	}
    	
    	name = name.trim();
    	
    	for(OfflinePlayer user : Bukkit.getOfflinePlayers()) {
    		if(user.getName().equalsIgnoreCase(name)) {

    	    	double balance = Blaze.getInstance().economyHandler.getAmount(user.getUniqueId());
    	    	
    	    	Map<String, String> replace = new HashMap<String, String>();
    	    	replace.put("%balance%", balance+"");
    	    	replace.put("%user%", name+"");
    	    	
    	    	String message = Blaze.getInstance().configManager.messages.getMessage("target_balance");
    	    	
    	    	message = message.replace("%balance%", balance+"");
    	    	message = message.replace("%user%", name+"");
    	    	
    			MessageUtils.sendRawMessage(player, message); 
    			
    			return;
    		
    		}
    	}
    	
    	MessageUtils.sendMessage(player, "&c" + name + "&a does not exist!"); 
		return;
    
    	
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
		return  Blaze.getInstance().configManager.permissions.balance;
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