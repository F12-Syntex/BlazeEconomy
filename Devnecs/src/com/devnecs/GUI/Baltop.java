package com.devnecs.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.devnecs.economy.Account;
import com.devnecs.economy.EconomyHandler;
import com.devnecs.main.Blaze;
import com.devnecs.utils.ComponentBuilder;
import com.devnecs.utils.MessageUtils;

public class Baltop extends GUI{

	private Map<Integer, Runnable> execution = new HashMap<Integer, Runnable>();
	
	public Baltop(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Balance top";
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Blaze.getInstance().configManager.permissions.baltop;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.ENTITY_PLAYER_LEVELUP;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0f;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickInventory(InventoryClickEvent e) {
		if(this.execution.containsKey(e.getSlot())) {
			Bukkit.getScheduler().runTask(Blaze.getInstance(), this.execution.get(e.getSlot()));
		}
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
	public void Contents(Inventory inv) {
		
		EconomyHandler handler = Blaze.getInstance().economyHandler;
		
		List<Account> data = handler.sortedData(6);
		
		System.out.println(data.size());
		
		int index = 21;
		
		for(int i = 0; i < this.size(); i++) {
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.RED_STAINED_GLASS_PANE);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("");
			stack.setItemMeta(meta);
			inv.setItem(i, stack);
		}
		
		for(int i = index; i < 33; i++) {
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.BARRIER);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("");
			stack.setItemMeta(meta);
			inv.setItem(i, stack);
			if(i == 23) i+=6;
		}
		
		for(Account i : data) {
			
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) stack.getItemMeta();
			
			meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&6" + Bukkit.getOfflinePlayer(i.getKey()).getName()));
			meta.setLore(ComponentBuilder.createLore("&7Balance: &a" + i.getBalance() + "$", "Click me for a list of transactions."));
			meta.setOwningPlayer(Bukkit.getOfflinePlayer(i.getKey()));
			
			stack.setItemMeta(meta);
			
			inv.setItem(index, stack);
			
			execution.put(index, () -> {
				player.closeInventory();
				GUI gui = new TransactionList(player, i);
				gui.open();
			});
			
			index++;
			if(index == 24) index+=6;
		}
		
	}

}
