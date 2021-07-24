
package com.devnecs.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Base;

public class Reload extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    
    	if(args.length == 1) {
        	Base.getInstance().reload();
        	Base.getInstance().configManager.messages.send(player, "plugin_reload");
        	return;
    	}
    
    }

    @Override

    public String name() {
        return "reload";
    }

    @Override
    public String info() {
        return "reloads the plugin.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return Base.getInstance().configManager.permissions.reload;	
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		return tabCompleter;
	}
	

}