package com.devnecs.GUI;

import org.bukkit.inventory.ItemStack;

public class SpecialItem {
	
	private ItemStack item;
	private Runnable execution;
	private int slot;
	
	public SpecialItem(ItemStack item, Runnable execution, int slot) {
		this.setItem(item);
		this.execution = execution;
		this.setSlot(slot);
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

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

}
