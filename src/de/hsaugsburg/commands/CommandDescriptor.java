package de.hsaugsburg.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class CommandDescriptor {
	private CommandTypeInfo commandType;
	private Object[] params;
	private boolean valid;
	
	public static enum ExeResult {
		SUCCESS, HELP, EXIT;
	}

	//Commandscanner verwaltet die Daten des commanddescriptors->doof
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
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
	
	
	public ExeResult execute(Object obj)  {
		String method = commandType.getMethod();
		if(method == null) {
			return ExeResult.EXIT;
		} else if(method.equals("")) {
			return ExeResult.HELP;
		}
		Class<?> objClass = obj.getClass();
		try {
			Method exeMethod = objClass.getMethod(method, commandType.getParamTypes());
			exeMethod.invoke(obj, params);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if(cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			e.printStackTrace();
		}
		return ExeResult.SUCCESS;
	}

}
