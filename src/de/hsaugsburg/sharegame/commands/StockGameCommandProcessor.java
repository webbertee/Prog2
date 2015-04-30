package de.hsaugsburg.sharegame.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import de.hsaugsburg.commands.CommandDescriptor;
import de.hsaugsburg.commands.CommandScanner;
import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;
import de.hsaugsburg.sharegame.shares.exceptions.UnknownShareException;

public class StockGameCommandProcessor {
	private CommandScanner commandScanner;
	// private BufferedReader shellIn;
	private PrintWriter shellOut;
	private AccountManager am;
	private StockPriceProvider spp;

	public StockGameCommandProcessor(InputStream inStream,
			PrintStream outStream, AccountManager am, StockPriceProvider spp) {

		this.shellOut = new PrintWriter(outStream, true);

		this.commandScanner = new CommandScanner(StockGameCommandType.values(),
				new BufferedReader(new InputStreamReader(inStream)));

		this.spp = spp;
		this.am = am;
	}

	public void startLoop() {
		CommandDescriptor cd = new CommandDescriptor();

		commandLoop: while (true) { // the loop over all commands with one input
									// line for
									// every command

			try {
				commandScanner.inputLine2CommandDescriptor(cd);
			} catch (IOException e) {
				shellOut.println("Reading the command Faied, try again");
				continue;
			}

			StockGameCommandType commandType = (StockGameCommandType) cd
					.getCommandType();

			if (!cd.isValid()) {
				if (cd.getCommandType() == null) {
					shellOut.println("Unknown command. Type 'help' for a list of commands");
				} else {
					shellOut.println("Could not read command:");
					shellOut.println(cd.getCommandType().getName() + " "
							+ cd.getCommandType().getHelpText());
				}
				continue;
			}
			try {
				switch (commandType) {
				case EXIT:
					shellOut.println("Stopping...");
					break commandLoop;
				case HELP:
					shellOut.println("The followning commands are supported:");
					for (StockGameCommandType com : StockGameCommandType
							.values()) {
						shellOut.println(com.getName() + " "
								+ com.getHelpText());
					}
					break;
				case CREATEPLAYER:
					am.addPlayer((String) cd.getParam(0), (long) cd.getParam(1));
					shellOut.println("Added player " + (String) cd.getParam(0));
					break;
				case BUYSHARE:
					am.buyShare((String) cd.getParam(0),
							(String) cd.getParam(1), (int) cd.getParam(2));
					shellOut.println((String) cd.getParam(0)
							+ " successfully bought shares for "
							+ spp.getShareValue((String) cd.getParam(1)));

					break;
				case SELLSHARE:
					am.sellShare((String) cd.getParam(0),
							(String) cd.getParam(1), (int) cd.getParam(2));
					shellOut.println((String) cd.getParam(0)
							+ " successfully sold shares for "
							+ spp.getShareValue((String) cd.getParam(1)));
					break;
				case SHOWASSETVALUE:
					shellOut.println((String) cd.getParam(0)
							+ "'s assets are worth "
							+ am.getPlayerAssetValue((String) cd.getParam(0)));
					break;
				case SHOWCASH:
					shellOut.println((String) cd.getParam(0) + " has "
							+ am.getPlayerCashValue((String) cd.getParam(0))
							+ " in cash.");
					break;
				case SHOWSHARECOUNT:
					shellOut.println((String) cd.getParam(0)
							+ " has "
							+ am.getPlayerSharesBuyValue(
									(String) cd.getParam(0),
									(String) cd.getParam(1)) + " shares from "
							+ cd.getParam(1));
					break;
				case SHOWSALLSHARES:
					shellOut.println("These are the current shares and their rates");
					for (String share : spp.getShareNames()) {
						shellOut.println(share + ": "
								+ spp.getShareValue(share));
					}
					break;
				}
			} catch (UnknownShareException e) {
				shellOut.println("Unknown share: " + e.getShareName());
			} catch (UnknownPlayerException e) {
				shellOut.println("Player " + e.getLocalizedMessage()
						+ " has not enough money");
			} catch (NotEnoughMoneyException e) {
				shellOut.println("Not enough money, "
						+ e.getMissingMoney() + " missing.");
			} catch (PlayerAlreadyExistsException e) {
				shellOut.println("Player " + e.getMessage() + " already exists");
			}

		}
	}
}
