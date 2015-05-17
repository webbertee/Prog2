package de.hsaugsburg.sharegame.commands;

import java.io.InputStream;
import java.io.PrintStream;

import de.hsaugsburg.commands.AsCommand;
import de.hsaugsburg.commands.CommandProcessor;
import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsaugsburg.sharegame.assets.exceptions.RemoveShareException;
import de.hsaugsburg.sharegame.shares.StockPriceInfo;
import de.hsaugsburg.sharegame.shares.exceptions.UnknownShareException;
import de.hsaugsburg.sharegame.viewer.PlayerViewer;
import de.hsaugsburg.sharegame.viewer.StockViewer;

public class StockGameCommandProcessor {
	private CommandProcessor cProcessor;
	private PrintStream outStream;
	private AccountManager am;
	private StockPriceInfo spp;

	public StockGameCommandProcessor(InputStream inStream,
			PrintStream outStream, AccountManager am, StockPriceInfo spp) {
		cProcessor = new CommandProcessor(inStream, outStream, am, spp, this);
		this.outStream = outStream;
		this.am = am;
		this.spp = spp;

	}

	@AsCommand(command = "exit", feedback = "exiting...", helpText = "* stops the Stockgame")
	public void exit() {
		System.exit(0);
	}
	
	@AsCommand(command = "vpl", feedback = "Opeing View...", helpText = "<name> * opens viewer for a specific Player")
	public void showPlayerViewer(String name) {
		new PlayerViewer(name, am, spp).start();;
	}
	
	@AsCommand(command = "vst", feedback = "Opeing View...", helpText = "* opens viewer for stocks")
	public void showStocks() {
		new StockViewer(spp).start();;
	}

	public void start() {
		while (true) {
			try {
				cProcessor.readCommand();
			} catch (UnknownShareException e) {
				outStream.println("Unknown share: " + e.getShareName());

			} catch (UnknownPlayerException e) {
				outStream.println("Player " + e.getMessage()
						+ " does not exist.");

			} catch (NotEnoughMoneyException e) {
				outStream.println("Not enough money, " + e.getMissingMoney()
						+ " missing.");

			} catch (PlayerAlreadyExistsException e) {
				outStream.println("Player " + e.getMessage()
						+ " already exists");
			} catch (RemoveShareException e) {
				outStream.println(e.getMessage());
			}
		}
	}
}
