package com.devnecs.main;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.devnecs.config.ConfigManager;
import com.devnecs.cooldown.CooldownManager;
import com.devnecs.cooldown.CooldownTick;
import com.devnecs.events.EventHandler;


public class Base extends JavaPlugin implements Listener{


    public static Base instance;
    public com.devnecs.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public File ParentFolder;
	
	@Override
	public void onEnable(){
		
		ParentFolder = getDataFolder();
	    instance = this;
		
	    configManager = new ConfigManager();
	    configManager.setup(this);
	    
	    this.reload();
	    
	    eventHandler = new EventHandler();
	    eventHandler.setup();
	    
	    this.cooldownManager = new CooldownManager();

	    this.cooldownTick = new CooldownTick();
	    this.cooldownTick.schedule();
	    
	    this.CommandManager = new com.devnecs.commands.CommandManager();
	    this.CommandManager.setup(this);

	}
	
	
	@Override
	public void onDisable(){
		this.eventHandler = null;
		HandlerList.getRegisteredListeners(instance);
	}
	
	public static void Log(String msg){
		  Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c" + Base.getInstance().getName() + "&7] &c(&7LOG&c): " + msg));
	}
	

	public void reload() {
		this.configManager = new ConfigManager();
		this.configManager.setup(this);
	}
		

	public static Base getInstance() {
		return instance;
	}
		
	
}
