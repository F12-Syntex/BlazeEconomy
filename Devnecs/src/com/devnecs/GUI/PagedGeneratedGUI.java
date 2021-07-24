package com.devnecs.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.devnecs.config.Messages;
import com.devnecs.config.gui.GUIConfig;
import com.devnecs.config.gui.GuiItem;
import com.devnecs.config.gui.Page;
import com.devnecs.main.Base;
import com.devnecs.utils.MessageUtils;

public abstract class PagedGeneratedGUI extends GenericConfigurableInterface implements Listener{

	public Player player;
	public Inventory inv;
	public Messages messages;
	
	protected List<GuiItem> inventoryItems = new ArrayList<GuiItem>();
	protected Map<String, Runnable> executables = new HashMap<String, Runnable>();
	protected Map<String, Object> placeholders = new HashMap<String, Object>();
	
	protected List<GuiItem> post_items = new ArrayList<GuiItem>();
	
	protected GUIConfig config;
	protected int page;
	
	
	public PagedGeneratedGUI(Player player) {
		this.player = player;
		this.messages = Base.getInstance().configManager.messages;
		this.config = Base.getInstance().configManager.guis.getConfig(this);
		this.inv = Bukkit.createInventory(this.player, size(), MessageUtils.translateAlternateColorCodes(this.config.title));
		this.page = 1;
	}
	
	public PagedGeneratedGUI copy() {
		return this;
	}
	
	public void updateInventory(int page) {
		PagedGeneratedGUI copy = copy();
		copy.page = page;
		copy.Contents(this.inv);
	}
	
	public PagedGeneratedGUI() {}
	
	public List<GuiItem> defaultPagedItem(){
		inventoryItems.add(GuiItem.constructPagedItem(GenerateItem.getItem(MessageUtils.translateAlternateColorCodes("&aNext page"), Material.PURPLE_STAINED_GLASS_PANE), (size()-1)+"", this.config() + ":next", Page.GET_ALL_PAGES()));
		inventoryItems.add(GuiItem.constructPagedItem(GenerateItem.getItem(MessageUtils.translateAlternateColorCodes("&cPrev page"), Material.PURPLE_STAINED_GLASS_PANE), (size()-9)+"", this.config() + ":back", Page.GET_ALL_PAGES()));
		
		return inventoryItems;
	}
	
	public Map<String, Runnable> defaultExecution() {
		
		this.executables.put(this.config() + ":next", () -> {
			this.front();
			this.updateInventory(page);
		});
		
		
		this.executables.put(this.config() + ":back", () -> {
			this.back();
			this.updateInventory(page);
		});
		
		return executables;
	}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(!e.getView().getTitle().equals(this.name())) return;
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
		if(!e.getView().getTitle().equals(this.name())) return;
		this.onCloseInventory(e);
		HandlerList.unregisterAll(this);
		
		player.getWorld().playSound(player.getLocation(), this.config.closeSound, (float) this.config.closeVolume, soundLevel());
		
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		
		if(e.getClickedInventory() == null) return;
		if(e.getWhoClicked().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(e.getClickedInventory().getType() == InventoryType.PLAYER) {
			e.setCancelled(true);
			return;
		}
		
		String title = MessageUtils.translateAlternateColorCodes(e.getView().getTitle());
		String view = MessageUtils.translateAlternateColorCodes(this.config.title);
		
		if(!title.equals(view)) return;
		
		e.setCancelled(!canTakeItems());
		if(e.getCurrentItem() == null) {
			return;
		}
		
		
		List<GuiItem> obj = this.config.interior;
		obj.addAll(this.post_items);
		
		for(GuiItem interior : obj) {
				
				GuiItem parent = interior;
				
				if(parent.getPosition().contains(e.getSlot()) && ((parent.getPage().getPage() == this.page) || (parent.getPage().isAllPaged()))) {

					for(GuiItem gui : this.post_items) {
						if(gui.getPosition().contains(e.getSlot()) && ((gui.getPage().getPage() == this.page) || (gui.getPage().isAllPaged()))) {
							parent = gui;
						}
					}

					String id = parent.getRunnableID();
					
					if(!id.contains(" ")) {
						id = id + " ";
					}
					
					boolean clickPrefix = false;
					
						for(String control : id.split(" ")) {
							
							if(control.contains(":")) {
								
								if(control.split(":")[0].equalsIgnoreCase(e.getClick().name())) {
									
									Runnable runnable = this.executables.get(control.trim());
									Bukkit.getScheduler().runTask(Base.getInstance(), runnable);
									clickPrefix = true;
									break;
								}
							}		
	
						}
	
					if(!clickPrefix) {
						System.out.println("Exe: " + parent.getRunnableID());
						Runnable runnable = this.executables.get(parent.getRunnableID());
						
						if(runnable == null) return;
						
						Bukkit.getScheduler().runTask(Base.getInstance(), runnable);
					}
					
				}
		}
		
		
	}
	
	public abstract String config();
	
	public abstract String name();
	public abstract String permission();
	
	public abstract int size();
	
	public abstract float soundLevel();
	public abstract boolean canTakeItems();
	
	public abstract void onOpenInventory(InventoryOpenEvent e);
	public abstract void onCloseInventory(InventoryCloseEvent e);
	
	public void open(int page) {
		
		this.page = page;
		
		this.player.closeInventory();
		Base.instance.getServer().getPluginManager().registerEvents(this, Base.instance);
		
		player.getWorld().playSound(player.getLocation(), this.config.openSound, (float) this.config.openVolume, soundLevel());
	    
		this.placeholders();
	
		this.post_items = this.postConfiguration();
		
		this.executables();
	
		this.Contents(inv);
	    
	    player.openInventory(inv);
	    
	    for(int i = 0; i > this.inv.getContents().length; i++) {
	    	if(this.inv.getContents()[i].getType() == Material.AIR) {
	    		this.inv.setItem(i, this.config.Empty);
	    	}
	    }
	}
	
	public void updatePlaceholders() {
		this.placeholders.clear();
		this.placeholders();
	}
	
	
	public void Contents(Inventory inv) {
		
		this.inv.clear();
		this.updatePlaceholders();
		
		List<GuiItem> items = this.config.interior;
		
		for(GuiItem i : items) {
			
			final ItemStack item = i.getItem().clone();
			
			if(!i.show()) {
				continue;
			}
			
			if(this.placeholders.isEmpty()) {
				for(int o : i.getPosition()) {
					
					System.out.println("trying to add item (" + item.getItemMeta().getDisplayName() + ") to page " + i.getPage().getPage() + " def is " + this.page );
					
					if((this.page == i.getPage().getPage()) || (i.getPage().isAllPaged())) {
						this.inv.setItem(o, i.getItem());
					}
				}
				continue;
			}
			
			ItemMeta meta = item.getItemMeta();
			
			for(String placeholder : this.placeholders.keySet()) {
				
				meta.setDisplayName(meta.getDisplayName().replace(placeholder, this.placeholders.get(placeholder).toString()));
				
				List<String> lore = meta.getLore();
				List<String> updatedLore = new ArrayList<String>();
				
				if(meta.hasLore()) {
					for(String o : lore) {
						updatedLore.add(o.replace(placeholder, this.placeholders.get(placeholder).toString()));
					}
					meta.setLore(updatedLore);
				}
				
			}
			
			
			item.setItemMeta(meta);
			
		}
		
		for(GuiItem gui : this.post_items) {
			for(int o : gui.getPosition()) {
				if(gui.getPosition().contains(o) && ((gui.getPage().getPage() == this.page) || (gui.getPage().isAllPaged()))) {
					this.inv.setItem(o, gui.getItem());
				}
			}
		}
		
	}
	

	public void front() {
		
		int maxPage = 1;
		
		for(GuiItem i : this.configuration()) {
			if(i.getPage().getPage() > maxPage) {
				System.out.println("max page: " + maxPage + " found by item: " + i.getRunnableID());
				maxPage = i.getPage().getPage();
			}
		}
		
		for(GuiItem i : this.postConfiguration()) {
			if(i.getPage().getPage() > maxPage) {
				System.out.println("max page: " + maxPage + " found by item: " + i.getRunnableID());
				maxPage = i.getPage().getPage();
			}
		}
	 
		if(this.page < maxPage) {
			page++;
		}
		
	}
	public void back() {
		if(this.page > 1) {
			page--;
		}
	}
	
	
	public void addItem(int index, ItemStack item) {
		inv.setItem(index, item);
	}
	
	public void fillEmpty(ItemStack stack) {
		for(int i = 0; i < this.size(); i++) {
			boolean add = true;
			for(GuiItem o : this.inventoryItems) {
				if(o.getPosition().contains(i)) {
					add = false;
				}
			}
			if(add) {
				this.inventoryItems.add(new GuiItem(stack, i, config() + ":empty"));
			}
		}
	}

}
