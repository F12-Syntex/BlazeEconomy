package com.devnecs.utils;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.devnecs.main.Blaze;

public class MessageUtils {

	public static String translateAlternateColorCodes(String s) {
		return ChatColor.translateAlternateColorCodes('&', MessageUtils.convertColors(s));
	}
	
	public static String convertColors(String s) {
        Pattern pattern = Pattern.compile(Pattern.quote("{#") + "(.*?)" +  Pattern.quote("}"));
        Matcher match = pattern.matcher(s);
        String ns = s;
        while (match.find()) {
            String colorcode = match.group(1);
            ns = ns.replaceAll("\\{#" + colorcode + "\\}", MessageUtils.getColor("#"+colorcode).toString());
        }
 
        return ns;
}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static net.md_5.bungee.api.ChatColor getColor(String s) {
        try {
		Class c = net.md_5.bungee.api.ChatColor.class;
		Method m = c.getMethod("of", String.class);
        Object o = m.invoke(null, s);
        return (net.md_5.bungee.api.ChatColor)o;
 
        }catch (Exception e) {
            return net.md_5.bungee.api.ChatColor.WHITE;
        }
	}
    
	public static void sendRawMessage(Player player, String s) {
		player.sendMessage(MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void inform(Player player, String s) {
		player.sendMessage(Blaze.getInstance().configManager.messages.prefix + MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void sendMessage(Player player, String s) {
		player.sendMessage(Blaze.getInstance().configManager.messages.prefix + " " + MessageUtils.translateAlternateColorCodes(s));
	}

	public static void sendConsoleMessage(String msg){
		  Bukkit.getConsoleSender().sendMessage(MessageUtils.translateAlternateColorCodes(msg));
	}
	public static void sendConsoleMessage(String[] msg){
		for(int i = 0; i < msg.length; i++)
		System.out.println(MessageUtils.translateAlternateColorCodes(msg[i]));
	}
	public static void sendHelp(Player player) {
		 Blaze.getInstance().CommandManager.getCommands().forEach(i -> {
			 MessageUtils.sendRawMessage(player,Blaze.getInstance().configManager.messages.prefix + " " + "&6• &c" + i.name() + "&7 : &b" + i.info());
		 });
	}
}
