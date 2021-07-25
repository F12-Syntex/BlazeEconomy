package com.devnecs.configs.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.devnecs.GUI.GenerateItem;
import com.devnecs.GUI.PagedItem;
import com.devnecs.GUI.SpecialItem;
import com.devnecs.config.Config;
import com.devnecs.main.Blaze;
import com.devnecs.utils.ComponentBuilder;
import com.devnecs.utils.MessageUtils;

public class ConfigSpecific extends ConfigGUI {

	private Config config;

	public ConfigSpecific(Player player, Config config, ConfigGUI back, ConfigGUI front) {
		super(player, back, front, config);
		// TODO Auto-generated constructor stub
		this.setConfig(config);
		
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
		return Blaze.getInstance().configManager.permissions.basic;
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
		
		this.config.getConfiguration().getKeys(false).forEach(i -> {
		
			
			if(i.equals("identity")){
				ItemStack object = new ItemStack(Material.valueOf(Blaze.getInstance().configManager.configs.Sections));
				
				ItemStack item = GenerateItem.getItem("&7" + i, object, ComponentBuilder.createLore("&cYou may not modify this section."));
				
				items.add(new PagedItem(item, () -> {
					MessageUtils.inform(player, " &cYou may not modify this section.");
				}));
				
			}else {
				
				ItemStack object = new ItemStack(Material.valueOf(Blaze.getInstance().configManager.configs.Sections));
				
				List<String> lore = new ArrayList<String>();
				
				for(String o : this.config.getConfiguration().getConfigurationSection(i).getKeys(false)) {
					lore.add("&7 - &6" + o);	
				}
				
				ItemStack item = GenerateItem.getItem("&7" + i, object, lore);
				
				items.add(new PagedItem(item, () -> {
					player.closeInventory();
					
					ConfigGUI gui = new ConfigSection(player, this.config.getConfiguration().getConfigurationSection(i), this, front, config);
					gui.open();
				}));
				
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

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}


}
