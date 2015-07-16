package de.yannici.bedwars.bungee.join.signs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.yannici.bedwars.bungee.Main;
import de.yannici.bedwars.bungee.Subchannel;
import de.yannici.bedwars.bungee.join.ServerInformation;

public class JoinSign implements ServerInformation {

	private String gameName = null;
	private String serverName = null;
	private Location signLocation = null;
	private SignUpdateInformation information = null;

	public JoinSign(Location sign, String game, String server) {
		this.signLocation = sign;
		this.gameName = game;
		this.serverName = server;
	}

	public void updateSign(SignUpdateInformation information) {
		this.information = information;
		Sign sign = (Sign) this.signLocation.getBlock().getState();
		
		String[] signLines = this.getSignLines();
		for (int i = 0; i < signLines.length; i++) {
		    sign.setLine(i, signLines[i]);
		}
		
		sign.update(true, true);
	}
	
	public SignUpdateInformation getLastUpdateInformation() {
		return this.information;
	}

	private String[] getSignLines() {
		String[] sign = new String[4];
		sign[0] = this.information.getFirstLine();
		sign[1] = this.information.getSecondLine();
		sign[2] = this.information.getThirdLine();
		sign[3] = this.information.getFourthLine();

		return sign;
	}
	
	public Sign getSign() {
	    BlockState state = this.signLocation.getBlock().getState();
	    
	    if(!(state instanceof Sign)) {
	        return null;
	    }
	    
	    return (Sign)state;
	}

	public void sendUpdateRequest() {
		Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if(player == null) return;
		
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Forward");
			out.writeUTF(Subchannel.UPDATE_REQUEST.toString());
			out.writeUTF(this.gameName);
			out.writeUTF(this.serverName);

			Main.getInstance().getServer().getMessenger()
				.dispatchIncomingMessage(player, Main.BUNGEE_CHANNEL_NAME, out.toByteArray());
		} catch(Exception ex) {
			if(Main.DEBUGGING) {
				Main.getInstance().getLogger().info("Error on sending update request for sign: " + ex.getMessage());
			}
		}
	}

	@Override
	public String getGameName() {
		return this.gameName;
	}

	@Override
	public String getServerName() {
		return this.serverName;
	}

}
