package de.hsa.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.hsa.commands.exceptions.CommandNameException;
import de.hsa.commands.exceptions.CommandNotFoundException;
import de.hsa.commands.exceptions.CommandParseException;

public class CommandProcessor {

	private PrintWriter shellOut;
	private CommandScanner commandScanner;
	private ResourceBundle lang;
	Map<String, CommandTypeInfo> commandTypes;

	public CommandProcessor(InputStream inStream, PrintStream outStream, ResourceBundle lang,
			Object... commandClasses) {
		
		this.lang = lang;
		this.shellOut = new PrintWriter(outStream, true);

		commandTypes = new HashMap<String, CommandTypeInfo>();
		addCommandTypes(this);
		addCommandTypes(commandClasses);
		this.commandScanner = new CommandScanner(commandTypes,
				new BufferedReader(new InputStreamReader(inStream)));
	}

	@AsCommand(command = "help", feedback = "getHelpFeedback", helpText = "getHelpHelp")
	public String getHelp() {
		String n = System.getProperty("line.separator");
		StringBuilder out = new StringBuilder();
		out.append(n);
		commandTypes.forEach((str, type) -> out.append(str + " "
				+ getLangString(type.getHelpText()) + n));
		out.setLength(out.length() - n.length());
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
			shellOut.print(e.getCommand() + " ");
			printLangString(e.getHelpText());
			return;
			
		} catch (IOException e) {
			printLangString("commandIOException");
			e.printStackTrace();
			return;
		} catch (CommandNotFoundException e) {
			printLangString("commandNotFound");
			return;
		}
		shellOut.print(getLangString(cd.getCommandType().getFeedback()));
		shellOut.println(cd.execute());
	}
	
	public void printLangString(String key) {
		shellOut.println(getLangString(key));
	}
	
	public String getLangString(String key) {
		if (lang != null) {
			try {
				return lang.getString(key);
			} catch (MissingResourceException e) {}
		}
		return key;
	}
}