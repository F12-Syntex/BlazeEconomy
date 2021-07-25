
package com.devnecs.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.config.Config;
import com.devnecs.configs.gui.ConfigGUI;
import com.devnecs.configs.gui.ConfigSpecific;
import com.devnecs.configs.gui.ConfigsView;
import com.devnecs.main.Blaze;
import com.devnecs.utils.MessageUtils;

public class Configure extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	if(args.length == 1) {
        	ConfigsView gui = new ConfigsView(player, null, null);
        	gui.open();	
        	return;
    	}
    	
    	ArrayList<Config> config = Blaze.getInstance().configManager.config;
    	
    	for(Config i : config) {
    		if(i.getName().equalsIgnoreCase(args[1])) {
    			player.closeInventory();
				ConfigGUI gui = new ConfigSpecific(player, i, null, null);
				gui.open();
				return;
    		}
    	}
    	
    	MessageUtils.sendRawMessage(player, Blaze.getInstance().configManager.messages.invalid_configure_command.replace("%config%", args[1]));
    	
    }

    @Override

    public String name() {
        return "configure";
    }

    @Override
    public String info() {
        return "Modify configs in game!";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  Blaze.getInstance().configManager.permissions.configure;
	}

	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		
		for(Config i : Blaze.getInstance().configManager.config) {
			tabCompleter.createEntry(i.getName());
		}
		
		return tabCompleter;
	}

	

}