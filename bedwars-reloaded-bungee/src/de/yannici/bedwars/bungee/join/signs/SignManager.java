package de.yannici.bedwars.bungee.join.signs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import de.yannici.bedwars.bungee.Main;

public class SignManager {
	
	private List<JoinSign> signs = null;
	private YamlConfiguration signConfig = null;
	
	public SignManager() {
		super();
		
		this.signs = new ArrayList<JoinSign>();
	}
	
	public List<JoinSign> getSigns() {
		return this.signs;
	}
	
	public void loadSigns(File file) {
		try {
			YamlConfiguration signYaml = new YamlConfiguration();
			
			if(!file.exists()) {
				file.createNewFile();
				
				this.signConfig = signYaml;
				this.signConfig.createSection("signs");
				this.signConfig.save(file);
				return;
			}
			
			this.signConfig = YamlConfiguration.loadConfiguration(file);
			this.loadSigns();
		} catch (IOException e) {
			if(Main.DEBUGGING) {
				Main.getInstance().getLogger().info("Error on loading signs: " + e.getMessage());
			}
		}
	}
	
	private void loadSigns() throws IOException {
		if(!this.signConfig.isList("signs")) {
			throw new IOException("The signs section has to be a list! Loading cancelled.");
		}
	}

}
