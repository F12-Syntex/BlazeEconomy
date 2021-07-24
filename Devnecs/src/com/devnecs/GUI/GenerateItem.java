package com.devnecs.GUI;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.devnecs.utils.ComponentBuilder;
import com.devnecs.utils.MessageUtils;

public class GenerateItem {

	public static ItemStack getItem(String name, Material item) {
		
		ItemStack block = new ItemStack(item);
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	public static ItemStack getItem(String name, ItemStack block) {
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}

	public static ItemStack getItem(String name, Material item, String lore) {
		
		ItemStack block = new ItemStack(item);
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setLore(Arrays.asList(MessageUtils.translateAlternateColorCodes(lore)));
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	public static ItemStack getItem(String name, Material item, String lore, String localName) {
		
		ItemStack block = new ItemStack(item);
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setLore(Arrays.asList(MessageUtils.translateAlternateColorCodes(lore)));
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	public static ItemStack getItem(String name, Material item, String lore, int localName) {
		
		ItemStack block = new ItemStack(item);
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setLore(Arrays.asList(MessageUtils.translateAlternateColorCodes(lore)));
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	
	public static ItemStack getItem(String name, ItemStack block, String lore) {
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setLore(Arrays.asList(MessageUtils.translateAlternateColorCodes(lore)));
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	
	public static ItemStack getItem(String name, ItemStack block, List<String> lore) {
		
		ItemMeta meta = block.getItemMeta();
		
		meta.setLore(ComponentBuilder.createLore(lore));
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	public static ItemStack getItem(String name, Material item, boolean Enchanted) {
		
		ItemStack block = new ItemStack(item);
		
		ItemMeta meta = block.getItemMeta();
		
		if(Enchanted) block.addEnchantment(Enchantment.DURABILITY, 10);
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}
	
	public static ItemStack getItem(String name, ItemStack block, boolean Enchanted) {
		
		ItemMeta meta = block.getItemMeta();
		
		if(Enchanted) block.addEnchantment(Enchantment.DURABILITY, 10);
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name));
		
		block.setItemMeta(meta);
		
		return block;
		
	}

    
}
