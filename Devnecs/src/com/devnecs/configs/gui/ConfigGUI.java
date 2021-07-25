package com.devnecs.configs.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.devnecs.GUI.GenerateItem;
import com.devnecs.GUI.PagedItem;
import com.devnecs.GUI.SpecialItem;
import com.devnecs.config.Config;
import com.devnecs.main.Blaze;
import com.devnecs.utils.MessageUtils;

public abstract class ConfigGUI implements Listener{

	protected Player player;
	protected Inventory inv;
	
	public int page;
	
	private Map<Integer, Runnable> execution = new HashMap<Integer, Runnable>();
	
	public ConfigGUI back;
	public ConfigGUI front;
	
	public Config config;
	
	public ConfigGUI(Player player, ConfigGUI back, ConfigGUI front, Config config) {
		this.page = 1;
		this.player = player;
		Blaze.instance.getServer().getPluginManager().registerEvents(this, Blaze.instance);
		this.inv = Bukkit.createInventory(player, size(), name());
		
		if(config != null) {
			this.setConfig(config);
		}
		
		if(back != null) {
			this.back = back;
		}
		
		if(front != null) {
			this.front = front;
		}
	}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId() != this.player.getUniqueId()) return;
		if(!e.getPlayer().hasPermission(permission())) {
			MessageUtils.sendRawMessage(player, Blaze.getInstance().configManager.messages.invalid_permission);
			e.setCancelled(true);
			return;
		}
		onOpenInventory(e);
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer().getUniqueId() != this.player.getUniqueId()) return;
		this.onCloseInventory(e);
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		
		if(e.getWhoClicked().getUniqueId() != this.player.getUniqueId()) return;
		
		e.setCancelled(!canTakeItems());
		
		if(e.getCurrentItem() == null) {
			return;
		}
		this.onClickInventory(e);
		
		if(e.getSlot() == 45) {
			if(this.back != null) {
				player.closeInventory();
				this.back.openWithNewRegister();
			}
		}
		
		if(e.getSlot() == 53) {
			if(this.front != null) {
				player.closeInventory();
				this.front.openWithNewRegister();
			}
		}
		
		int index = e.getSlot() + ((this.page-1) * 36);
		
		if(this.execution.containsKey(index) && e.getSlot() < 36) {
			this.execution.get(index).run();
		}
		
		for(SpecialItem i : this.SpecialContents()) {
			if(e.getSlot() == i.getSlot()) {
				i.getExecution().run();
			}
		}
		
	}
	
	public abstract String name();
	public abstract String permission();
	
	public abstract int size();
	
	public abstract Sound sound();
	public abstract float soundLevel();
	public abstract boolean canTakeItems();
	
	public abstract void onClickInventory(InventoryClickEvent e);
	public abstract void onOpenInventory(InventoryOpenEvent e);
	public abstract void onCloseInventory(InventoryCloseEvent e);
	public abstract List<PagedItem> Contents();
	public abstract List<SpecialItem> SpecialContents();
	
	public void open() {
		player.getWorld().playSound(player.getLocation(), sound(), soundLevel(), soundLevel());
		this.refresh();
		player.openInventory(inv);
	}

	public void openWithNewRegister() {
		Blaze.instance.getServer().getPluginManager().registerEvents(this, Blaze.instance);
		player.getWorld().playSound(player.getLocation(), sound(), soundLevel(), soundLevel());
		this.refresh();
		player.openInventory(inv);
	}

	
	
	public void refresh() {
		
		Bukkit.getServer().getScheduler().runTask(Blaze.getInstance(), new Runnable() {
		    public void run() {

				inv.clear();
				ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
				fillEmpty(blackPane);		
			
				final List<PagedItem> contents = Contents();
				final List<SpecialItem> special = SpecialContents();
				
				for(int i = 0; i < 36; i++) {
					int index = i + ((page-1) * 36);
					if(index < contents.size()) {
						PagedItem item = contents.get(index);
						inv.setItem(i, contents.get(index).getItem());
						execution.put(index, item.getExecution());
					}
				}
			
				for(int i = 36; i < 45; i++) {
					ItemStack emptyPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
					inv.setItem(i, emptyPane);
				}
				for(int i = 45; i < 54; i++) {

					boolean flag = false;
					for(SpecialItem o : special) {
						if(o.getSlot() == i) {
							inv.setItem(i, o.getItem());
							flag = true;
						}
					}

					if(!flag) {
						ItemStack emptyPane = new ItemStack(Material.AIR, 1);
						inv.setItem(i, emptyPane);	
					}
					
				}
		
				
				if(back != null) {
					ItemStack back = GenerateItem.getItem("&aBack", Material.GREEN_STAINED_GLASS_PANE, "&3Takes you back");
					inv.setItem(45, back);
				}else {
					ItemStack back = GenerateItem.getItem("&aBack", Material.RED_STAINED_GLASS_PANE, "&cYour at the first page.");
					inv.setItem(45, back);	
				}
				
				if(front != null) {
					ItemStack next = GenerateItem.getItem("&aNext", Material.GREEN_STAINED_GLASS_PANE, "&3Takes to the next page.");
					inv.setItem(53, next);		
				}else {
					ItemStack next = GenerateItem.getItem("&aNext", Material.RED_STAINED_GLASS_PANE, "&cThis is the final page!");
					inv.setItem(53, next);	
				}
		    }
		});
			
	}
	
	public void addItem(int index, ItemStack item) {
		inv.setItem(index, item);
	}
	
	public void fillEmpty(ItemStack stack) {
		for(int i = 0; i < this.size(); i++) {
			if(this.inv.getItem(i) == null) {
				this.inv.setItem(i, stack);
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Map<Integer, Runnable> getExecution() {
		return execution;
	}

	public void setExecution(Map<Integer, Runnable> execution) {
		this.execution = execution;
	}

	public ConfigGUI getBack() {
		return back;
	}

	public void setBack(ConfigGUI back) {
		this.back = back;
	}

	public ConfigGUI getFront() {
		return front;
	}

	public void setFront(ConfigGUI front) {
		this.front = front;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
	
}
