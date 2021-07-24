package com.devnecs.GUI;

import org.bukkit.inventory.ItemStack;

public class PagedItem {
	
	private ItemStack item;
	private Runnable execution;
	
	public PagedItem(ItemStack item, Runnable execution) {
		this.setItem(item);
		this.execution = execution;
	}
	
	public void run() {
		this.execution.run();
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public Runnable getExecution() {
		return execution;
	}

	public void setExecution(Runnable execution) {
		this.execution = execution;
	}

}
