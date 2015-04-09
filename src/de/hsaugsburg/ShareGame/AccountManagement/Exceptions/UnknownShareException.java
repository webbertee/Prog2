package de.hsaugsburg.ShareGame.AccountManagement.Exceptions;

public class UnknownShareException extends RuntimeException {

	public UnknownShareException(String shareName) {
		super(shareName);
	}
	
}
