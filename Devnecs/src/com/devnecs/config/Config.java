package com.devnecs.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.devnecs.main.Blaze;

public abstract class Config {

	protected String name;
	protected double verison;
	protected FileConfiguration configuration;
	protected File config;
	protected Blaze base;
	
	public List<ConfigItem> items;
	
	public abstract Configuration configuration();
	
	public Config(String name, double version) {
		this.name = name;
		this.verison = version;
		this.base = Blaze.getInstance();
		this.items = new ArrayList<ConfigItem>();
	}

	public String getName() {
		return name;
	}

	public abstract String folder();
	
	public void setName(String name) {
		this.name = name;
	}

	public double getVerison() {
		return verison;
	}

	public void setVerison(double verison) {
		this.verison = verison;
	}

	public FileConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(FileConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public void setup() {
		
		if(this.folder().isEmpty()) {
			config = new File(this.base.getDataFolder().getAbsolutePath(), this.name + ".yml");	
		}else {
			File folder = new File(this.base.getDataFolder().getAbsolutePath(), this.folder());
			config = new File(folder, this.name + ".yml");	
		}
		
		
		
		
		this.configuration = YamlConfiguration.loadConfiguration(config);
		
		if(!config.exists()) {
		
			this.configuration.set("identity.version", this.verison);
			
			for(ConfigItem i : this.items) {
				this.configuration.set(i.getPath(), i.getData());
			}
		}
		
		
		this.save();
		
	}

	public void setDefault() {
		this.configuration.set("identity.version", this.verison);
		
		for(ConfigItem i : this.items) {
			this.configuration.set(i.getPath(), i.getData());
		}
	}
	
    public void save() {
		try {
			this.configuration.save(this.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public File getConfig() {
		return config;
	}

	public void setConfig(File config) {
		this.config = config;
	}
	
	public abstract void initialize();
	
	public File backup() {
		
		File depreciated = new File(this.base.getDataFolder(), "depreciated");
		File backups = new File(depreciated, this.name);

		Blaze.Log("Backing up...");
		
		if(backups.mkdirs() || backups.exists()) {

			Blaze.Log("created");
			
			if(!this.getConfig().exists()) return null;
			
				int files = backups.listFiles().length + 1;
				
				File backup = new File(backups, this.name + "(" + files + ")" + ".yml");
				
				final File tempConfig = this.getConfig();
				
				boolean success = this.getConfig().renameTo(backup);
				
				if(success) {
					Blaze.Log("&a" + this.name + ".yml has been backed up!");	
				}else {
					Blaze.Log("&cCouldnt backup!");
				}
			
				return tempConfig;
			
		}else if(!backups.exists()) {
			Blaze.Log("&cCouldnt create backup folder!");
		}
		
		return null;
		
	}
	
	
}
