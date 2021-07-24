package com.devnecs.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.devnecs.main.Base;

public class CooldownUser {
	
	private UUID uuid;
	private Map<String, Integer> cooldown;
	private int clicksParSecond;
	
	public CooldownUser(UUID uuid) {
		this.uuid = uuid;
		this.cooldown = new HashMap<String, Integer>();
		this.setClicksParSecond(0);
	}
	
	public Integer getTime(String key) {
		if(this.cooldown.containsKey(key)) {
			return this.cooldown.get(key);
		}else {
			int timer = Base.getInstance().configManager.cooldown.getCooldown(key);
			this.cooldown.put(key, timer);
			return 0;			
		}
	}
	
	public void reset(String key) {
		this.cooldown.put(key, Base.getInstance().configManager.cooldown.getCooldown(key));
	}
	public void reset(String key, int seconds) {
		this.cooldown.put(key, seconds);
	}
	
	public void tick() {
		this.cooldown.keySet().forEach(i -> {
			
			int newValue = this.cooldown.get(i) - 1;
			
			if(newValue >= 0) {
				this.cooldown.put(i, newValue);
			}
			
			this.setClicksParSecond(0);
	
		});
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Map<String, Integer> getCooldown() {
		return cooldown;
	}

	public void setCooldown(Map<String, Integer> cooldown) {
		this.cooldown = cooldown;
	}

	public int getClicksParSecond() {
		return clicksParSecond;
	}

	public void setClicksParSecond(int clicksParSecond) {
		this.clicksParSecond = clicksParSecond;
	}

}
