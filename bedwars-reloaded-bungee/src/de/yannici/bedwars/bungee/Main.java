package de.yannici.bedwars.bungee;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.yannici.bedwars.bungee.join.signs.JoinSign;
import de.yannici.bedwars.bungee.join.signs.SignManager;
import de.yannici.bedwars.bungee.listeners.SignListener;

public class Main extends JavaPlugin implements PluginMessageListener {
	
	/**
	 * The plugin channel name used for bungeecord
	 */
	public static final String BUNGEE_CHANNEL_NAME = "BungeeCord";
	public static final String SIGN_FILE = "signs.yml";
	public static final boolean DEBUGGING = true;
	
	private static Main instance = null;
	
	private SignManager signManager = null;
	private BukkitTask updateTask = null;
	
	@Override
	public void onEnable() {
		Main.instance = this;
		
		// Save default config
		this.saveDefaultConfig();
		
		this.registerListeners();
		this.registerPluginChannel();
		
		this.signManager = new SignManager();
		this.signManager.loadSigns(new File(this.getDataFolder(), Main.SIGN_FILE));
		
		this.startUpdateTask();
	}
	
	public void onDisable() {
		try {
			this.updateTask.cancel();
		} catch(Exception ex) {
			// do nothing, just disable
		}
	}
	
	/**
	 * Returns the current sign manager
	 * @return SignManager The current sign manager
	 */
	public SignManager getSignManager() {
		return this.signManager;
	}
	
	/**
	 * Registers outgoing channel for sign join
	 * and incoming channel for sign updates
	 */
	private void registerPluginChannel() {
		this.getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), Main.BUNGEE_CHANNEL_NAME);
		this.getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), Main.BUNGEE_CHANNEL_NAME, this);
	}

	/**
	 * Registers all needed listeners (eg. Sign)
	 */
	private void registerListeners() {
		new SignListener();
	}
	
	private void startUpdateTask() {
		this.updateTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(JoinSign sign : Main.getInstance().getSignManager().getSigns()) {
					sign.sendUpdateRequest();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0L, (long) Math.round(20L*(Main.getInstance().getConfig().getDouble("update-interval", 10.0))));
	}

	/**
	 * The current plugin instance
	 * @return Main current plugin instance
	 */
	public static Main getInstance() {
		return Main.instance;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(!channel.equals(Main.BUNGEE_CHANNEL_NAME)) {
			return;
		}
	}

}
