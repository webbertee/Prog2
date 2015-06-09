package de.hsa.commands;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.hsa.commands.exceptions.UnsopportedParameterException;

public class CommandDescriptor {
	private CommandTypeInfo commandType;
	private Object[] params;

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
		String message = "";
		try {
			Object result = commandType.getMethod().invoke(commandType.getTarget(), params);
			if(result != null)  {
				if(commandType.getCurrencyFormat())
					message += NumberFormat.getCurrencyInstance().format(((Number) result).doubleValue()/100.0);
				else
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
