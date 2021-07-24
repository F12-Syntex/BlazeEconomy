package com.devnecs.GUI;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.devnecs.config.gui.GuiItem;
import com.devnecs.main.Base;

public class TestGeneratedGui extends PagedGeneratedGUI{
	
	public TestGeneratedGui(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	public TestGeneratedGui() {}
	
	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "asd";
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Base.getInstance().configManager.permissions.admin;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
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
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GuiItem> configuration() {
		return this.inventoryItems;
	}

	@Override
	public List<GuiItem> postConfiguration() {
		// TODO Auto-generated method stub
		this.post_items.add(new GuiItem(GenerateItem.getItem("asd", Material.DIAMOND), 0, "nothing"));
		return this.post_items;
	}

	@Override
	public Map<String, Runnable> executables() {
		// TODO Auto-generated method stub
		return this.executables;
	}

	@Override
	public Map<String, Object> placeholders() {
		// TODO Auto-generated method stub
		return this.placeholders;
	}

}
