package de.hsa.commands;

import java.lang.reflect.Method;

public class CommandType implements CommandTypeInfo {
	private final String command;
	private final Object target;
	private final Method method;
	private final Class<?>[] argTypes;
	private final String helpText;
	private final String feedback;
	private final boolean currencyFormat;
	
	public CommandType(String command, Object target, Method method,
			Class<?>[] argTypes, String helpText, String feedback, boolean currencyFormat) {
		this.command = command;
		this.target = target;
		this.method = method;
		this.argTypes = argTypes;
		this.helpText = helpText;
		this.feedback = feedback;
		this.currencyFormat = currencyFormat;
	}

	public String getCommand() {
		return command;
	}

	public Object getTarget() {
		return target;
	}

	public Method getMethod() {
		return method;
	}

	public Class<?>[] getArgTypes() {
		return argTypes;
	}

	public String getHelpText() {
		return helpText;
	}

	public String getFeedback() {
		return feedback;
	}

	@Override
	public boolean getCurrencyFormat() {
		return currencyFormat;
	}
}
