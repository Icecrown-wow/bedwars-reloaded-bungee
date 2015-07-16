package de.yannici.bedwars.bungee.join.signs;

public class SignUpdateInformation  {
	private String firstLine = null;
	private String secondLine = null;
	private String thirdLine = null;
	private String fourthLine = null;
	
	public SignUpdateInformation(String firstLine,
			String secondLine, String thirdLine, String fourthLine) {
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.thirdLine = thirdLine;
		this.fourthLine = fourthLine;
	}
	
	public String getFirstLine() {
		return this.firstLine;
	}
	
	public String getSecondLine() {
		return this.secondLine;
	}
	
	public String getThirdLine() {
		return this.thirdLine;
	}
	
	public String getFourthLine() {
		return this.fourthLine;
	}

}
