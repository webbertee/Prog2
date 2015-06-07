package de.hsa.sharegame.shares.exceptions;

public class UnknownShareException extends RuntimeException {
	private final String shareName;
	public UnknownShareException(String shareName) {
		super(shareName);
		this.shareName = shareName;
	}
	public String getShareName() {
		return shareName;
	}
	
}
