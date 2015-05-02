package de.hsaugsburg.commands;

import java.io.BufferedReader;
import java.io.IOException;

import de.hsaugsburg.sharegame.commands.exceptions.UnsopportedParameterException;

public class CommandScanner {
	BufferedReader shellIn;
	CommandTypeInfo[] commTypes;


	public CommandScanner(CommandTypeInfo[] commTypes,
			BufferedReader shellIn) {
		this.commTypes = commTypes;
		this.shellIn = shellIn;
	}

	public void inputLine2CommandDescriptor(CommandDescriptor cDescriptor)
			throws IOException {
		// Initialize cDescritopr
		cDescriptor.setValid(false);
		cDescriptor.setCommandType(null);
		cDescriptor.setParams(null);

		// split command into parameters
		String[] input = shellIn.readLine().trim().split(" ");

		// find command type
		for (CommandTypeInfo t : commTypes) {
			if (t.getName().equalsIgnoreCase(input[0]))
				cDescriptor.setCommandType(t);
		}

		// Abroad if command was not found
		if (cDescriptor.getCommandType() == null) {
			return;
		}

		// Abroad if number of commands is invalid
		Class<?>[] paramTypes = cDescriptor.getCommandType().getParamTypes();
		if ((input.length - 1) != paramTypes.length) {
			return;
		}
		// Try to cast the object into the Param Types
		Object[] comParams = new Object[paramTypes.length];
		try {
			for (int i = 0; i < paramTypes.length; i++) {
				if (paramTypes[i] == String.class) {
					comParams[i] = input[i+1];
				} else if (paramTypes[i] == int.class) {
					comParams[i] = Integer.parseInt(input[i+1]);
				} else if(paramTypes[i] == long.class) {
					comParams[i] = Long.parseLong(input[i+1]);
				} else {
					throw new UnsopportedParameterException(paramTypes[i].getName());
				}
			}
			cDescriptor.setParams(comParams);
			cDescriptor.setValid(true);
		} catch (NumberFormatException e) {
			//invalid integer argument
			return;
		} 
	}
}
