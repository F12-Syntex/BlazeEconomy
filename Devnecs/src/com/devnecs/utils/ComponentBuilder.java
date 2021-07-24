package com.devnecs.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ComponentBuilder {

	public static List<String> createLore(String... i) {
		List<String> lore = new ArrayList<String>();
		for(String o : i.clone()) {
			lore.add(MessageUtils.translateAlternateColorCodes(o));
		}
		return lore;
	}
	
	public static List<String> createLore(List<String> i) {
		List<String> lore = new ArrayList<String>();
		for(String o : i) {
			lore.add(MessageUtils.translateAlternateColorCodes(o));
		}
		return lore;
	}
	
	public static String build(String[] array) {
		StringBuilder builder = new StringBuilder();
		for(String i : array) {
			builder.append(i + " ");
		}
		return builder.toString().trim();
	}
	
	public static String build(String[] array, int startIndex) {
		StringBuilder builder = new StringBuilder();
		for(int i = startIndex; i < array.length; i++) {
			builder.append(array[i] + " ");
		}
		return builder.toString().trim();
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material item, DyeColor colour, String name, String... lore) {
		ItemStack empty = new ItemStack(item, 1, colour.getDyeData());
		ItemMeta meta = empty.getItemMeta();
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		meta.setLore(ComponentBuilder.createLore(lore));
		empty.setItemMeta(meta);
		return empty;
	}
	
	public static List<ItemStack> removeNull(List<ItemStack> stack){
		for(int i = 0; i < stack.size(); i++) {
			if(stack.get(i) == null) {
				stack.set(i, new ItemStack(Material.AIR));
			}
		}
		return stack;
	}
	
}
