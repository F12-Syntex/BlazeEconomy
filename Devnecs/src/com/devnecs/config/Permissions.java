package com.devnecs.config;

public class Permissions extends Config{

	public String shop = "bukkit.command.help";
	public String basic = "bukkit.command.help";
	public String admin = "blaze.admin";
	public String bypass = "blaze.timer.bypass";
	public String reload = "blaze.admin.reload";
	public String configure = "blaze.admin.configure";
	public String defaultShop = "blaze.admin.default.shop";
	public String inventory = "bukkit.command.help";
	public String set = "blaze.admin.set";
	
	public String balance = "bukkit.command.help";
	public String give = "blaze.admin.give";
	public String take = "blaze.admin.take";
	
	public Permissions(String name, double version) {
		super(name, version);

		this.items.add(new ConfigItem("Permissions.everyone.basic", basic));
		this.items.add(new ConfigItem("Permissions.everyone.shop", shop));
		this.items.add(new ConfigItem("Permissions.everyone.inventory", inventory));
		this.items.add(new ConfigItem("Permissions.everyone.balance", balance));
		
		
		this.items.add(new ConfigItem("Permissions.administration.admin", admin));
		this.items.add(new ConfigItem("Permissions.administration.timer_bypass", bypass));
		this.items.add(new ConfigItem("Permissions.administration.reload", reload));
		this.items.add(new ConfigItem("Permissions.administration.configure", configure));
		this.items.add(new ConfigItem("Permissions.administration.defualt", defaultShop));
		this.items.add(new ConfigItem("Permissions.administration.set", set));
		this.items.add(new ConfigItem("Permissions.administration.give", give));
		this.items.add(new ConfigItem("Permissions.administration.take", take));
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.PERMISSIONS;
	}
	
	@Override
	public void initialize() {
		this.shop = this.getConfiguration().getString("Permissions.everyone.shop");
		this.basic = this.getConfiguration().getString("Permissions.everyone.basic");
		this.admin = this.getConfiguration().getString("Permissions.administration.admin");
		this.bypass = this.getConfiguration().getString("Permissions.administration.timer_bypass");
		this.reload = this.getConfiguration().getString("Permissions.administration.reload");
		this.configure = this.getConfiguration().getString("Permissions.administration.configure");
		this.defaultShop = this.getConfiguration().getString("Permissions.administration.defualt");
		this.inventory = this.getConfiguration().getString("Permissions.everyone.inventory");
		this.set = this.getConfiguration().getString("Permissions.administration.set");
		
		this.balance = this.getConfiguration().getString("Permissions.everyone.balance");
		this.give = this.getConfiguration().getString("Permissions.administration.give");
		this.take = this.getConfiguration().getString("Permissions.administration.take");
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return "";
	}

	
}
