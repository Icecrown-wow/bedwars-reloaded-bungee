package de.yannici.bedwars.bungee;

public enum Subchannel {
	UPDATE_REQUEST("update-request");
	
	private String name = null;
	
	Subchannel(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
