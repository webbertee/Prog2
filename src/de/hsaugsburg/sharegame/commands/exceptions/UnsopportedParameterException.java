package de.hsaugsburg.sharegame.commands.exceptions;

@SuppressWarnings("serial")
public class UnsopportedParameterException extends RuntimeException {
	public UnsopportedParameterException(String name) {
		super(name);
	}
}
