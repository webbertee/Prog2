package de.hsa.commands;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.hsa.commands.exceptions.CommandNameException;
import de.hsa.commands.exceptions.CommandNotFoundException;
import de.hsa.commands.exceptions.CommandParseException;

public class CommandProcessor {

	private CommandScanner commandScanner;
	private ResourceBundle lang;
	Map<String, CommandTypeInfo> commandTypes;

	public CommandProcessor(ResourceBundle lang,Object... commandClasses) {
		
		this.lang = lang;

		commandTypes = new HashMap<String, CommandTypeInfo>();
		addCommandTypes(this);
		addCommandTypes(commandClasses);
		this.commandScanner = new CommandScanner(commandTypes);
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

	public String readCommand(String s) {
		CommandDescriptor cd = new CommandDescriptor();
		try {
			commandScanner.inputLine2CommandDescriptor(cd, s);
		} catch (CommandParseException e) {
			return e.getCommand() + " " + getLangString(e.getHelpText());
			
		} catch (IOException e) {
			return getLangString("commandIOException");
		} catch (CommandNotFoundException e) {
			return getLangString("commandNotFound");
		}
		return getLangString(cd.getCommandType().getFeedback()) + cd.execute();
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