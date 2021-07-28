package com.devnecs.GUI;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.devnecs.economy.Account;
import com.devnecs.economy.Transaction;
import com.devnecs.main.Blaze;
import com.devnecs.placeholder.time.TimeFormater;
import com.devnecs.utils.ComponentBuilder;
import com.devnecs.utils.MessageUtils;

public class TransactionList extends GUI{

	private Account account;
	
	private int ID = 0;
	
	public TransactionList(Player player, Account i) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setAccount(i);
		
		
		ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Blaze.getInstance(), ()->{
			this.Contents(this.inv);
		}, 0, 20L);
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Transactions";
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		Bukkit.getScheduler().cancelTask(ID);
	}

	@Override
	public void Contents(Inventory inv) {
		
		List<Transaction> data = this.account.sortedTransaction(3);
		
		int index = 21;
		
		for(int i = 0; i < this.size(); i++) {
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.RED_STAINED_GLASS_PANE);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("");
			stack.setItemMeta(meta);
			inv.setItem(i, stack);
		}
		
		for(int i = index; i < 23; i++) {
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.BARRIER);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("");
			stack.setItemMeta(meta);
			inv.setItem(i, stack);
		}
		
		for(Transaction i : data) {
			
			org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(Material.PAPER);
			ItemMeta meta = stack.getItemMeta();
			
			meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&6" + i.getTransactionType().name()));
			
			TimeFormater formatter = new TimeFormater();
			
			int seconds = (int) ((System.currentTimeMillis() - i.getDate())/1000);
			
			String results = formatter.parse(seconds);
			
			meta.setLore(ComponentBuilder.createLore("&7Amount: &a" + i.getAmount() + "$", "&7Admin: &c" + Bukkit.getOfflinePlayer(i.getSender()).getName(), "&7Processed: &a" + results));
			
			stack.setItemMeta(meta);
			
			inv.setItem(index, stack);
			index++;
		}
		
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
