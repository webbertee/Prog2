package de.hsaugsburg.commands;



public interface CommandTypeInfo {
	String getName();
	String getHelpText();
	Class<?>[] getParamTypes();
	String getMethod();
	String getMessage();
	ExeResult getExeResult();
	
	public static enum ExeResult {
		SUCCESS, HELP, EXIT;
	}
	
}
