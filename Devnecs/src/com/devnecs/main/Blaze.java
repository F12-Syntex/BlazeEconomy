package com.devnecs.main;
import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.devnecs.config.ConfigManager;
import com.devnecs.cooldown.CooldownManager;
import com.devnecs.cooldown.CooldownTick;
import com.devnecs.economy.EconomyHandler;
import com.devnecs.events.EventHandler;
import com.devnecs.storage.StorageHandler;

public class Blaze extends JavaPlugin implements Listener{


    public static Blaze instance;
    public com.devnecs.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public File ParentFolder;
	
	public StorageHandler handler;
	
    private static final Logger log = Logger.getLogger("Minecraft");
	
    public EconomyHandler economyHandler;
    
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

	    this.handler = new StorageHandler();
	    
	    this.economyHandler = new EconomyHandler();
	    this.economyHandler.loadEconomy();
    
	}
	
	
	@Override
	public void onDisable(){
		this.eventHandler = null;
		HandlerList.getRegisteredListeners(instance);
		log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
		this.handler.update();
		this.handler.close();
	}
	
	public static void Log(String msg){
		  Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c" + Blaze.getInstance().getName() + "&7] &c(&7LOG&c): " + msg));
	}
	

	public void reload() {
		this.configManager = new ConfigManager();
		this.configManager.setup(this);
	}
		

	public static Blaze getInstance() {
		return instance;
	}
	
	/*
  private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        
        return econ != null;
    }
	 */

}
