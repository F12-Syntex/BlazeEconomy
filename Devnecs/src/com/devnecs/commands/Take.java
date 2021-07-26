
package com.devnecs.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;
import com.devnecs.utils.MessageUtils;
import com.devnecs.utils.Numbers;

public class Take extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	if(args.length != 3) {
    		MessageUtils.sendMessage(player, "&c/blaze give {user} {amount}");
    		return;
        }
    	
    	String name = args[1];
    	 
    	if(!Numbers.isNumber(args[2])) {
    		MessageUtils.sendMessage(player, "&c/blaze take {user} {amount}");
    		return;
        }
    	
    	
    	for(OfflinePlayer user : Bukkit.getOfflinePlayers()) {
    		if(user.getName().equals(name)) {
    	    	int amount = Integer.parseInt(args[2]);
    	    	
    	    	String message = Blaze.getInstance().configManager.messages.getMessage("target_take");
    	    	
    	    	Blaze.getInstance().economyHandler.getAccount(user).take(player.getUniqueId(), amount);
    	    	
    	    	message = message.replace("%balance%", amount+"");
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
        return "take";
    }

    @Override
    public String info() {
        return "gives a users money.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Blaze.getInstance().configManager.permissions.give;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			tabCompleter.createEntry(player.getName() + ".1000");
			tabCompleter.createEntry(player.getName() + ".10000");
		}
		return tabCompleter;
	}
	

}