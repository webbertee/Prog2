package de.hsaugsburg.sharegame.accounts.exceptions;

public class UnknownShareException extends RuntimeException {

	public UnknownShareException(String shareName) {
		super(shareName);
	}
	
}
