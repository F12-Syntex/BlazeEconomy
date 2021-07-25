package com.devnecs.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;
import com.devnecs.placeholder.time.TimeFormater;
import com.devnecs.utils.MessageUtils;

public class TagFactory{

	public String item = "";
	public String name = "";
	
	private Map<String, String> mapping = new HashMap<String, String>();
	
	public int cooldown = 0;

	private Player player;
	
	public List<String> lore = new ArrayList<String>();
	
	public static TagFactory instance(String i) {
		TagFactory factory = new TagFactory(i);
		return factory;
	}
	
	public static TagFactory instance(List<String> i) {
		TagFactory factory = new TagFactory(i);
		return factory;
	}
	
	public TagFactory(String item) {
		this.item = item;
	}
	
	public static TagFactory instance(Player player) {
		TagFactory factory = new TagFactory(player.getDisplayName());
		factory.setPlayer(player);
		return factory;
	}
	
	public void addMapping(String key, String value) {
		this.mapping.put(key, value);
	}
	
	public void removeMapping(String key) {
		this.mapping.remove(key);
	}
	
	public String playerParse(String parser) {
		
		TimeFormater formater = new TimeFormater();
		
		parser = parser.replace("%cooldown%", formater.parse(this.cooldown)+"");
		parser = parser.replace("%prefix%", Blaze.getInstance().configManager.messages.prefix);
		parser = parser.replace("%version%", "1.0");
		parser = parser.replace("%user%", this.name);
		
		for(String key : this.mapping.keySet()) {
			parser = parser.replace(key, this.mapping.get(key));
		}
		
		return parser;
	}
	
	public TagFactory(List<String> lore) {
		this.lore = lore;
	}
	
	public String parse() {
		return this.parse(this.item);
	}
	
	public List<String> listParse(){
		
		List<String> builder = new ArrayList<String>();
		
		this.lore.forEach(i -> {
			
			builder.add(parse(i));
			
		});
		
		return builder;
	}
	
	private String parse(String parser) {
	
		TimeFormater formater = new TimeFormater();
		
		parser = parser.replace("%cooldown%", formater.parse(this.cooldown)+"");
		parser = parser.replace("%prefix%", Blaze.getInstance().configManager.messages.prefix);
		parser = parser.replace("%version%", "1.0");
		parser = parser.replace("%user%", this.name);
		

		
		return MessageUtils.translateAlternateColorCodes(parser);
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public TagFactory setName(String name) {
		this.name = name;
		return this;
	}

	public List<String> getLore() {
		return lore;
	}

	public TagFactory setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
