package de.hsaugsburg.ShareGame.AccountManagement.Exceptions;

public class UnknownPlayerException extends RuntimeException {

	public UnknownPlayerException(String playerName) {
		super(playerName);
	}
}
