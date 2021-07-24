package com.devnecs.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
	
    public SubCommand() {
    
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String name();
   
    public abstract String permission();

    public abstract String info();

    public abstract String[] aliases();
    
    public abstract AutoComplete autoComplete(CommandSender sender);

}
