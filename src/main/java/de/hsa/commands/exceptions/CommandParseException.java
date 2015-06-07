package de.hsa.commands.exceptions;

import de.hsa.commands.CommandTypeInfo;

public class CommandParseException extends Exception{
	private final CommandTypeInfo commandType;

	public CommandParseException(CommandTypeInfo commandType) {
		this.commandType = commandType;
	}
	public String getCommand() {
		return commandType.getCommand();
	}
	public String getHelpText() {
		return commandType.getHelpText();
	}
	

}
