package de.hsaugsburg.sharegame.commands;

public interface CommandTypeInfo {
	String getName();
	String getHelpText();
	Class<?>[] getParamTypes();
}
