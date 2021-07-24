package com.devnecs.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import com.devnecs.placeholder.time.TimeData;
import com.devnecs.utils.MappyHelper;
import com.devnecs.utils.MappyObject;

public class Cooldown extends Config{

	public List<HashMap<String, Integer>> commands;
	public String coding = "&a%time%&7(&6%shortname%&7)"; 
	public int complexity = 1;
	public String message = "%prefix% &cSorry, please wait %cooldown%&7 &cto use that command again!";
	
	
	public Cooldown(String name, double version) {
		super(name, version);
		
		 List<HashMap<String, Integer>> cooldown = new ArrayList<HashMap<String, Integer>>();
		
		 HashMap<String, Integer> item = new HashMap<String, Integer>();
		 
		 item.put("help", 3);
		 
		 cooldown.add(item);
		 
		 this.commands = cooldown;
		 
		 this.items.add(new ConfigItem("Cooldown.commands", commands));
		 this.items.add(new ConfigItem("Cooldown.display.coding", coding));
		 this.items.add(new ConfigItem("Cooldown.display.complexity", complexity));
		 this.items.add(new ConfigItem("Cooldown.display.message", message));
		 
		 this.items.add(new ConfigItem("Time.seconds.short_name", "s"));
		 this.items.add(new ConfigItem("Time.seconds.seconds", 1));
		 
		 this.items.add(new ConfigItem("Time.minutes.short_name", "m"));
		 this.items.add(new ConfigItem("Time.minutes.seconds", 60));
		 
		 this.items.add(new ConfigItem("Time.hours.short_name", "hr"));
		 this.items.add(new ConfigItem("Time.hours.seconds", 3600));
		 
		 this.items.add(new ConfigItem("Time.days.short_name", "d"));
		 this.items.add(new ConfigItem("Time.days.seconds", 86400));
		 
		 this.items.add(new ConfigItem("Time.weeks.short_name", "w"));
		 this.items.add(new ConfigItem("Time.weeks.seconds", 604800));
		 
		 this.items.add(new ConfigItem("Time.months.short_name", "mm"));
		 this.items.add(new ConfigItem("Time.months.seconds", 2419200));
		 
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.COOLDOWN;
	}
	
	@Override
	public void initialize() {
		this.coding = this.getConfiguration().getString("Cooldown.display.coding");
		this.complexity = this.getConfiguration().getInt("Cooldown.display.complexity");
		this.message = this.getConfiguration().getString("Cooldown.display.message");
	}
	
	public int getCooldown(String key) {
		
		List<Map<?, ?>> cooldowns = this.getConfiguration().getMapList("Cooldown.commands");
		
		MappyHelper helper = new MappyHelper(cooldowns);
		
		List<MappyObject> commands = helper.decode();
		
		List<MappyObject> command = commands.stream().filter(i -> i.getKey().toString().equalsIgnoreCase(key)).collect(Collectors.toList());
		
		if(command.isEmpty()) {
			return 0;
		}
		
		return Integer.valueOf(command.get(0).getValue().toString());
	}

	public List<TimeData> getTimeData(){
		
		ConfigurationSection section = this.getConfiguration().getConfigurationSection("Time");
		
		List<TimeData> data = new ArrayList<TimeData>();
		
		for(String name : section.getKeys(false)) {
		
			ConfigurationSection time = section.getConfigurationSection(name);
			
			String shortName = time.getString(".short_name");
			long seconds = time.getLong(".seconds");
			
			TimeData timeData = new TimeData(name, shortName, seconds);
			
			data.add(timeData);
			
		}
		
		return data;
	}

	
}
