package de.hsa.sharegame.assets;
public abstract class Asset {

	private String name;

	public abstract long getValue();


	public Asset(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}