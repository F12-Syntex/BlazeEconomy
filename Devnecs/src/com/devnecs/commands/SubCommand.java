package com.devnecs.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;

import net.milkbowl.vault.economy.Economy;

public abstract class SubCommand {
	
	protected Economy economy = Blaze.getInstance().econ;
	
    public SubCommand() {
    
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String name();
   
    public abstract String permission();

    public abstract String info();

    public abstract String[] aliases();
    
    public abstract AutoComplete autoComplete(CommandSender sender);

}
