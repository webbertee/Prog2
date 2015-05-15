package de.hsaugsburg.commands;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import de.hsaugsburg.commands.exceptions.CommandNameException;
import de.hsaugsburg.commands.exceptions.CommandParseException;

public class CommandProcessor {

	private PrintWriter shellOut;
	private CommandScanner commandScanner;
	Map<String, CommandTypeInfo> commandTypes;

	public CommandProcessor(InputStream inStream, PrintStream outStream,
			Object... commandClasses) {

		this.shellOut = new PrintWriter(outStream, true);

		commandTypes = new HashMap<String, CommandTypeInfo>();
		addCommandTypes(this);
		addCommandTypes(commandClasses);
		this.commandScanner = new CommandScanner(commandTypes,
				new BufferedReader(new InputStreamReader(inStream)));
	}

	@AsCommand(command = "help", feedback = "List of Avalable commands:", helpText = " * show list of avalable commands")
	public String getHelp() {
		String n = System.getProperty("line.separator");
		StringBuilder out = new StringBuilder();
		out.append(n);
		commandTypes.forEach((str, type) -> out.append(str + " "
				+ type.getHelpText() + n));

		return out.toString();
	}

	private void addCommandTypes(Object... commandClasses) {

		for (Object obj : commandClasses) {
			for (Method m : obj.getClass().getMethods()) {
				for (AsCommand com : m.getAnnotationsByType(AsCommand.class)) {
					if (commandTypes.get(com.command()) != null)
						throw new CommandNameException(
								"Commandname already taken");
					commandTypes.put(
							com.command(),
							new CommandType(com.command(), obj, m, m
									.getParameterTypes(), com.helpText(), com
									.feedback(), com.currencyFormat()));
				}
			}

		}
	}

	public void readCommand() {
		CommandDescriptor cd = new CommandDescriptor();
		try {
			commandScanner.inputLine2CommandDescriptor(cd);
		} catch (CommandParseException e) {
			shellOut.println(e.getMessage());
			return;
		}
		shellOut.println(cd.execute());
	}
}