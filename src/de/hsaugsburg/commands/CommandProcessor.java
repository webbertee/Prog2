package de.hsaugsburg.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsaugsburg.sharegame.commands.StockGameCommandType;
import de.hsaugsburg.sharegame.shares.exceptions.UnknownShareException;

public class CommandProcessor {

	private PrintWriter shellOut;
	private CommandScanner commandScanner;

	public CommandProcessor(InputStream inStream,
			PrintStream outStream, AccountManager am, Class<?>... commandClasses) {

		this.shellOut = new PrintWriter(outStream, true);

		//this.commandScanner = new CommandScanner(StockGameCommandType.values(),
		//		new BufferedReader(new InputStreamReader(inStream)));
		Map<String, CommandTypeInfo> commandTypes;
		this.commandScanner = new CommandScanner(commandTypes, new BufferedReader(new InputStreamReader(inStream)));
	}

	public void startLoop() {
		CommandDescriptor cd = new CommandDescriptor();

		while (true) { // the loop over all commands with one input
						// line for every command

			try {
				commandScanner.inputLine2CommandDescriptor(cd);
			} catch (IOException e) {
				shellOut.println("Reading the command Faied, try again");
				continue;
			}
			
			
			if (!cd.isValid()) {
				if (cd.getCommandType() == null) {
					shellOut.println("Unknown command. Type 'help' for a list of commands");
				} else {
					shellOut.println("Could not read command: " );
					shellOut.println(cd.getCommandType().getName() + " "
							+ cd.getCommandType().getHelpText());
				}
				continue;
			}
			
			
			try {
				String message = cd.execute(am);
				if(message != null)
					shellOut.println(message);
					
				if(cd.getCommandType().getExeResult() == ExeResult.EXIT) {
					break;
				} else if(cd.getCommandType().getExeResult() == ExeResult.HELP) {
					for(StockGameCommandType c : StockGameCommandType.values()) {
						shellOut.println(c.getName() + c.getHelpText());
					}
				}
				
			} catch (UnknownShareException e) {
				shellOut.println("Unknown share: " + e.getShareName());
			} catch (UnknownPlayerException e) {
				shellOut.println("Player " + e.getMessage()
						+ " does not exist.");
			} catch (NotEnoughMoneyException e) {
				shellOut.println("Not enough money, "
						+ e.getMissingMoney() + " missing.");
			} catch (PlayerAlreadyExistsException e) {
				shellOut.println("Player " + e.getMessage() + " already exists");
			}
		}
	}
}