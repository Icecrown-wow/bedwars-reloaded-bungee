package de.yannici.bedwars.bungee.listeners;

import org.bukkit.event.Listener;

import de.yannici.bedwars.bungee.Main;

public class BaseListener implements Listener {
	
	public BaseListener() {
		Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}

}