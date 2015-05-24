package de.hsaugsburg.commands;

import java.lang.reflect.Method;



public interface CommandTypeInfo {
	String getCommand();
	Object getTarget();
	Method getMethod();
	Class<?>[] getArgTypes();
	String getHelpText();
	String getFeedback();
	String getCurrencyFormat();
}
