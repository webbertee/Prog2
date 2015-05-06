package de.hsaugsburg.commands;

public @interface AsCommand {
	String command();
	String helpText();
	String feedback();
}
