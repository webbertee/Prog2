package de.hsaugsburg.commands;


public interface CommandTypeInfo {
	String getName();
	String getHelpText();
	Class<?>[] getParamTypes();
	String getMethod();
	
}
