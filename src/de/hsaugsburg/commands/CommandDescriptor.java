package de.hsaugsburg.commands;


public class CommandDescriptor {
	private CommandTypeInfo CommandType;
	private Object[] params;
	private boolean valid;
	
	//Commandscanner verwaltet die Daten des commanddescriptors->doof
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public CommandTypeInfo getCommandType() {
		return CommandType;
	}
	public void setCommandType(CommandTypeInfo CommandType) {
		this.CommandType = CommandType;
	}
	public Object getParam(int i) {
		return params[i];
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
}
