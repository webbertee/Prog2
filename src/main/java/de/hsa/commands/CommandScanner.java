package de.hsa.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import de.hsa.commands.exceptions.CommandNotFoundException;
import de.hsa.commands.exceptions.CommandParseException;
import de.hsa.commands.exceptions.UnsopportedParameterException;

public class CommandScanner {
	private BufferedReader shellIn;
	private Map<String, CommandTypeInfo> commTypes;


	public CommandScanner(Map<String, CommandTypeInfo> commTypes, BufferedReader shellIn) {
		this.commTypes = commTypes;
		this.shellIn = shellIn;
	}

	public void inputLine2CommandDescriptor(CommandDescriptor cDescriptor)
			throws CommandParseException, IOException, CommandNotFoundException {
		// split command into parameters
		String[] input;
		
		input = shellIn.readLine().trim().split(" ");


		cDescriptor.setCommandType(commTypes.get(input[0]));
		// Abroad if command was not found
		if (cDescriptor.getCommandType() == null)
			throw new CommandNotFoundException();

		
		// Abroad if number of commands is invalid
		Class<?>[] argTypes = cDescriptor.getCommandType().getArgTypes();
		if ((input.length - 1) != argTypes.length) {
			throw new CommandParseException(cDescriptor.getCommandType());
		}
		
		
		// Try to cast the object into the Param Types
		Object[] comParams = new Object[argTypes.length];
		try {
			for (int i = 0; i < argTypes.length; i++) {
				if (argTypes[i] == String.class) {
					comParams[i] = input[i+1];
				} else if (argTypes[i] == int.class) {
					comParams[i] = Integer.parseInt(input[i+1]);
				} else if(argTypes[i] == long.class) {
					comParams[i] = Long.parseLong(input[i+1]);
				} else {
					throw new UnsopportedParameterException(argTypes[i].getName());
				}
			}
			cDescriptor.setParams(comParams);
		} catch (NumberFormatException e) {
			throw new CommandParseException(cDescriptor.getCommandType());
		} 
	}

}
