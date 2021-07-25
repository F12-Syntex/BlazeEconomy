package com.devnecs.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.devnecs.main.Blaze;

public class EventHandler {

    public List<SubEvent> events = new ArrayList<SubEvent>();
	
    private Plugin plugin = Blaze.instance;
    
	public void setup() {
		this.events.add(new InputHandler());
		this.events.forEach(i -> plugin.getServer().getPluginManager().registerEvents(i, plugin));
	}
	
}
