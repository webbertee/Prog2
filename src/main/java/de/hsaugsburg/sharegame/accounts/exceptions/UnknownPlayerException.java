package de.hsaugsburg.sharegame.accounts.exceptions;

public class UnknownPlayerException extends RuntimeException {

	public UnknownPlayerException(String playerName) {
		super(playerName);
	}
}
