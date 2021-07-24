package com.devnecs.config;

public class Settings extends Config{

	public int distance = 20;
	public int xpPerDamage = 10;
	public int pointsPerLevelUp = 1;
	public int revive = 1000;
	public int aggroRemoveDistance = 10;
	public int maxLevel = 30;
	public int maxRenameLength = 30;
	public boolean allowFromSpawnerMobs = false;
	
	public Settings(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Settings.pets.xp.earned.per_damage", xpPerDamage));
		this.items.add(new ConfigItem("Settings.pets.xp.maxLevel", maxLevel));
		this.items.add(new ConfigItem("Settings.pets.xp.allowFromSpawnerMobs", allowFromSpawnerMobs));
		this.items.add(new ConfigItem("Settings.pets.follow.teleport.distance", distance));
		this.items.add(new ConfigItem("Settings.pets.points.earned.perLevelUp", pointsPerLevelUp));
		this.items.add(new ConfigItem("Settings.pets.death.revive.cost", revive));
		this.items.add(new ConfigItem("Settings.pets.aggro.remove.distance", aggroRemoveDistance));
		this.items.add(new ConfigItem("Settings.pets.name.maxLength", maxRenameLength));
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SETTINGS;
	}
	
	@Override
	public void initialize() {
		this.distance = this.getConfiguration().getInt("Settings.pets.follow.teleport.distance");	
		this.xpPerDamage = this.getConfiguration().getInt("Settings.pets.xp.earned.per_damage");	
		this.pointsPerLevelUp = this.getConfiguration().getInt("Settings.pets.points.earned.perLevelUp");	
		this.revive = this.getConfiguration().getInt("Settings.pets.death.revive.cost");	
		this.aggroRemoveDistance = this.getConfiguration().getInt("Settings.pets.aggro.remove.distance");	
		this.maxLevel = this.getConfiguration().getInt("Settings.pets.xp.maxLevel");	
		this.maxRenameLength = this.getConfiguration().getInt("Settings.pets.name.maxLength");	
		this.allowFromSpawnerMobs = this.getConfiguration().getBoolean("Settings.pets.xp.allowFromSpawnerMobs");	
	}


	
}
