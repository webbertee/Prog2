package de.hsaugsburg.ShareGame.Assets;
public abstract class Asset {

	private String name;

	public abstract long getValue();

	/**
	 * 
	 * @param name
	 */
	public Asset(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}