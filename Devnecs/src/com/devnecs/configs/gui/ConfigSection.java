package com.devnecs.configs.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.devnecs.GUI.GenerateItem;
import com.devnecs.GUI.PagedItem;
import com.devnecs.GUI.SpecialItem;
import com.devnecs.config.Config;
import com.devnecs.main.Base;
import com.devnecs.utils.Communication;
import com.devnecs.utils.ComponentBuilder;
import com.devnecs.utils.Input;
import com.devnecs.utils.MessageUtils;
import com.devnecs.utils.Numbers;

public class ConfigSection extends ConfigGUI {

	private ConfigurationSection configSection;
	
	public ConfigSection(Player player, ConfigurationSection config, ConfigGUI back, ConfigGUI front, Config configuration) {
		super(player, back, front, configuration);
		// TODO Auto-generated constructor stub
		this.configSection = config;
		
		
		if(back != null) {
			back.setFront(this);
		}
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Configs";
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Base.getInstance().configManager.permissions.basic;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_LEVER_CLICK;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PagedItem> Contents() {

		List<PagedItem> items = new ArrayList<PagedItem>();
		
		this.configSection.getKeys(false).forEach(i -> {

			ItemStack section = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.Sections));
			ItemStack ints = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.Numbers));
			ItemStack text = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.Text));
			ItemStack booleanON = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.BooleanON));
			ItemStack booleanOFF = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.BooleanOFF));
			ItemStack lists = new ItemStack(Material.valueOf(Base.getInstance().configManager.configs.Lists));
			
			
			if(this.configSection.isConfigurationSection(i)) {
				
				List<String> lore = new ArrayList<String>();
				
				for(String o : this.configSection.getConfigurationSection(i).getKeys(false)) {
					lore.add("&7 - &6" + o);	
				}
				
				ItemStack item = GenerateItem.getItem("&7" + i, section, lore);
				
				items.add(new PagedItem(item, () -> {
					player.closeInventory();

					back.setFront(this);
					
					ConfigGUI gui = new ConfigSection(player, this.configSection.getConfigurationSection(i), this, front, config);
					gui.open();	
					
				}));
				
			}else {
				
				Object obj = this.configSection.get(i);
				
				ItemStack item = GenerateItem.getItem("&7" + i, section, ComponentBuilder.createLore("&5This item cannot be modified!"));
				Runnable execution = () -> {
					
				};
				
				if(obj instanceof String) {
					item = GenerateItem.getItem("&7" + i, text, ComponentBuilder.createLore("&6Value: &e" + obj.toString(), "&cClick here to change this value."));
					execution = () -> {
						
						player.closeInventory();
						MessageUtils.inform(player, " &cPlease type in " + "&7" + i + "'s&c new value");
						
						back.setFront(this);
						ConfigGUI gui = new ConfigSection(player, configSection, back, front, config);
						
						Communication.applyInput(player, new Input() {
							
							@Override
							public void onRecieve(String o) {
								
								config.getConfiguration().set(configSection.getCurrentPath() + "." + i, o);
								config.save();
								
								Base.getInstance().reload();
								
								Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
									@Override
									public Object call() throws Exception {
										gui.open();	 
										return null;
									}
								});
								
								MessageUtils.inform(player, " &cYou have set &7" + i + "&c to &7" + o + "&c.");
								
							}
						}, 30000);
						
					};
				}
				
				if(obj instanceof Boolean) {
					boolean value = this.configSection.getBoolean(i);
					if(value) {
						item = GenerateItem.getItem("&7" + i, booleanON, ComponentBuilder.createLore("&6Click here to change this value to &efalse."));	
						execution = () -> {
							player.closeInventory();
							config.getConfiguration().set(configSection.getCurrentPath() + "." + i, false);
							config.save();
							Base.getInstance().reload();
							back.setFront(this);
							ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
							
							gui.open();	
						};
					}else {
						item = GenerateItem.getItem("&7" + i, booleanOFF, ComponentBuilder.createLore("&6Click here to change this value to &etrue."));	
						execution = () -> {
							player.closeInventory();
							config.getConfiguration().set(configSection.getCurrentPath() + "." + i, true);
							config.save();
							Base.getInstance().reload();
							back.setFront(this);
							ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
							
							gui.open();	
						};
					}
				}
				
				if(obj instanceof Integer) {
					item = GenerateItem.getItem("&7" + i, ints, ComponentBuilder.createLore("&6Value: &e" + obj.toString(), "&cClick here to change this value."));
					execution = () -> {
						
						player.closeInventory();
						MessageUtils.inform(player, " &cPlease type in " + "&7" + i + "'s&c new value");
						
						back.setFront(this);
						ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
						
						
						Communication.applyInput(player, new Input() {
							
							@Override
							public void onRecieve(String o) {
								
								if(!Numbers.isNumber(o)) {
									MessageUtils.inform(player, " &cThat's not a number!");
									Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
										@Override
										public Object call() throws Exception {
											gui.open();
											return null;
										}
									});		
									return;
								}
								
								config.getConfiguration().set(configSection.getCurrentPath() + "." + i, Integer.parseInt(o));
								config.save();
								
								Base.getInstance().reload();
								Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
									@Override
									public Object call() throws Exception {
										gui.open();	 
										return null;
									}
								});
								
								MessageUtils.inform(player, " &cYou have set &7" + i + "&c to &7" + o + "&c.");
								
							}
						}, 30000);
						
					};
				}
				
				if(obj instanceof List) {
					
					List<String> lore = ComponentBuilder.createLore("&6Click here to change these values.");
					
					List<?> object = this.configSection.getList(i);					
					
					for(Object o : object) {
						lore.add("&7 - " + "&6" + o.toString());
					}
					
						item = GenerateItem.getItem("&7" + i, lists, lore);	
						
						execution = () -> {
						
							
						};
				}
				
				
				items.add(new PagedItem(item, execution));
				
			}

		});
		
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		// TODO Auto-generated method stub
		return new ArrayList<SpecialItem>();
	}



}
