package com.devnecs.GUI;

import java.util.List;
import java.util.Map;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.devnecs.config.gui.GuiItem;

public abstract class GenericConfigurableInterface {

	public abstract String config();
	
	public abstract List<GuiItem> configuration();
	public abstract List<GuiItem> postConfiguration();
	public abstract Map<String, Runnable> executables();
	public abstract Map<String, Object> placeholders();
	
	public abstract String name();
	public abstract String permission();
	
	public abstract int size();
	
	public abstract float soundLevel();
	public abstract boolean canTakeItems();
	
	public abstract void onOpenInventory(InventoryOpenEvent e);
	public abstract void onCloseInventory(InventoryCloseEvent e);
	
}
