package de.hsa.sharegame.assets.exceptions;

public class NotEnoughSharesException extends RuntimeException {
	private final int remaining;
	public NotEnoughSharesException(int remaining) {
		this.remaining = remaining;
	}
	
	public int getRemaining() {
		return remaining;
	}
}
