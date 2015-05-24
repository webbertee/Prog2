package de.hsaugsburg.commands.exceptions;

public class CommandParseException extends Exception{

	public CommandParseException(String helpText) {
		super(helpText);
	}

}
