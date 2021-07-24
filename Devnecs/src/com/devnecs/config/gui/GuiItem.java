package com.devnecs.config.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class GuiItem {
	
	private ItemStack item;
	private List<Integer> position;
	private String runnableID;
	private Page page = new Page(1, true);
	private ItemStack empty;
	
	public Object data = new Object();
	
	public String posFormat;
	
	public GuiItem(ItemStack item, Object position, String runnableID) {
		this.item = item;
		this.posFormat = position.toString().trim();
	
		this.position = new ArrayList<Integer>();
		
		String pos = position.toString();
		
		for(String i : pos.split(", ")) {
			
			String var = i.trim();
			
			if(var.contains("-")) {
				int min = Integer.parseInt(var.split("-")[0]);
				int max = Integer.parseInt(var.split("-")[1]);
				for(int o = min; o <= max; o++) {
					this.position.add(Integer.valueOf(o));
				}
				continue;
			}
			
			this.position.add(Integer.valueOf(var));
		}
		
		this.runnableID = runnableID;
	}
	
	
	
	public static GuiItem constructPagedItem(ItemStack item, String position, String runnableID, Page page) {
		GuiItem obj = new GuiItem(item, position, runnableID);
		obj.setPage(page);
		return obj;
	}
	
	public static GuiItem construct(ItemStack item, String position, String runnableID) {
		GuiItem obj = new GuiItem(item, position, runnableID);
		return obj;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public List<Integer> getPosition() {
		return position;
	}

	public void setPosition(List<Integer> position) {
		this.position = position;
	}

	public String getRunnableID() {
		return runnableID;
	}

	public void setRunnableID(String runnableID) {
		this.runnableID = runnableID;
	}
	
	public boolean show() {
		return true;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ItemStack getEmpty() {
		return empty;
	}

	public void setEmpty(ItemStack empty) {
		this.empty = empty;
	}

}
