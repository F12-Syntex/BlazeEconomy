package com.devnecs.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.devnecs.utils.Communication;

public class InputHandler extends SubEvent{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "input handler";
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "handles input for components";
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(Communication.players.containsKey(e.getPlayer())) {
			Communication.players.get(e.getPlayer()).onRecieve(e.getMessage());
			Communication.players.remove(e.getPlayer());
			e.setCancelled(true);
		}
	}

}
