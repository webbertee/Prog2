package de.hsaugsburg.sharegame.shares.exceptions;

public class UnknownShareException extends RuntimeException {

	public UnknownShareException(String shareName) {
		super(shareName);
	}
	
}
