package com.devnecs.GUI;

import org.bukkit.Bukkit;
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

import com.devnecs.config.Messages;
import com.devnecs.main.Base;
import com.devnecs.utils.MessageUtils;

public abstract class GUI implements Listener{

	protected Player player;
	protected Inventory inv;
	protected Messages messages;
	
	public GUI(Player player) {
		this.player = player;
		Base.instance.getServer().getPluginManager().registerEvents(this, Base.instance);
		inv = Bukkit.createInventory(this.player, size(), name());
		this.messages = Base.getInstance().configManager.messages;
	}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(!e.getPlayer().hasPermission(permission())) {
			MessageUtils.sendRawMessage(player, messages.invalid_permission);
			e.setCancelled(true);
			return;
		}
		onOpenInventory(e);
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		this.onCloseInventory(e);
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		if(e.getWhoClicked().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		e.setCancelled(!canTakeItems());
		if(e.getCurrentItem() == null) {
			return;
		}
		this.onClickInventory(e);
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
	public abstract void Contents(Inventory inv);
	
	public void open() {
		player.getWorld().playSound(player.getLocation(), sound(), soundLevel(), soundLevel());
	    Contents(inv);
	    player.openInventory(inv);
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
	
	
}
