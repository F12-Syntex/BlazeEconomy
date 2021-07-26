
package com.devnecs.commands;

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
    		
        	for(OfflinePlayer user : Bukkit.getOfflinePlayers()) {
        		if(user.getName().equals(player.getName())) {
        	
        	    	double balance = Blaze.getInstance().economyHandler.getAccount(user).getBalance();
        	    
        	    	String message = Blaze.getInstance().configManager.messages.getMessage("user_balance");
        	    	
        	    	message = message.replace("%balance%", balance+"");
            		
        			MessageUtils.sendRawMessage(player, message);

                	return;
        			
        		}
        	}
        }
    	
    	String name = "";
    	
    	for(int i = 1; i < args.length; i++) {
    		name+=args[i] + " ";
    	}
    	
    	name = name.trim();
    	
    	for(OfflinePlayer user : Bukkit.getOfflinePlayers()) {
    		if(user.getName().equals(name)) {
    			
    	    	double balance = Blaze.getInstance().economyHandler.getAccount(user).getBalance();
    	    	String message = Blaze.getInstance().configManager.messages.getMessage("target_balance");
    	    	
    	    	message = message.replace("%balance%", balance+"");
    	    	message = message.replace("%user%", user.getName()+"");
    	    	
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
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			tabCompleter.createEntry(player.getName());
		}
		return tabCompleter;
	}
	

}