package de.hsaugsburg.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import de.hsaugsburg.commands.exceptions.UnsopportedParameterException;

public class CommandDescriptor {
	private CommandTypeInfo commandType;
	private Object[] params;
	private boolean valid;

	// Commandscanner verwaltet die Daten des commanddescriptors->design?

	public CommandTypeInfo getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandTypeInfo CommandType) {
		this.commandType = CommandType;
	}

	public Object getParam(int i) {
		return params[i];
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String execute() {
		String message = commandType.getFeedback();
		try {
			Object result = commandType.getMethod().invoke(commandType.getTarget(), params);
			if(result != null)  {
				if(!commandType.getCurrencyFormat().equals("")) {
					result = new DecimalFormat(commandType.getCurrencyFormat()).format(((Number) result).doubleValue()/100);
				}
				message += result;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			message = "An Internal Error occured";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			message = "An Internal Error occured";
		} catch (InvocationTargetException e) {
			if(e.getCause() instanceof RuntimeException)
				throw (RuntimeException)e.getCause();
			else
				throw new UnsopportedParameterException("Command can only throw Runtime Exceptions");
		}
		return message;
	}

}
