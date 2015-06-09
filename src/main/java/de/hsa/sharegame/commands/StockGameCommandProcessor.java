package de.hsa.sharegame.commands;

import java.util.ResourceBundle;
import java.util.Vector;

import de.hsa.commands.AsCommand;
import de.hsa.commands.CommandProcessor;
import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsa.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsa.sharegame.assets.exceptions.RemoveShareException;
import de.hsa.sharegame.events.ConsoleOutputEvent;
import de.hsa.sharegame.events.ConsoleOutputHandler;
import de.hsa.sharegame.gui.PlayerViewer;
import de.hsa.sharegame.gui.StockViewer;
import de.hsa.sharegame.shares.StockPriceInfo;
import de.hsa.sharegame.shares.exceptions.UnknownShareException;
import de.hsa.sharegame.transactions.UnknownMIMETypeException;

public class StockGameCommandProcessor {
	private CommandProcessor cProcessor;
	private AccountManager am;
	private StockPriceInfo spp;
	private ResourceBundle lang = ResourceBundle.getBundle("i18n/ConsoleBundle");
	private Vector<ConsoleOutputHandler> handlers = new Vector<ConsoleOutputHandler>();

	public StockGameCommandProcessor(AccountManager am, StockPriceInfo spp) {
		cProcessor = new CommandProcessor(lang, am, spp, this);
		this.am = am;
		this.spp = spp;

	}
	
	public void addConsoleOutputHandler(ConsoleOutputHandler e) {
		handlers.addElement(e);
	}
	
	public boolean removeConsoleOutputHander(ConsoleOutputHandler e) {
		return handlers.remove(e);
	}
	
	private void callConsoleOutputHandlers(ConsoleOutputEvent e)	 {
		handlers.forEach(h -> h.handle(e));
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

	public String readCommand(String s) {
		String output;
		try {
			output = cProcessor.readCommand(s);
		} catch (UnknownShareException e) {
			output = "Unknown share: " + e.getShareName();
	
		} catch (UnknownPlayerException e) {
			output = "Player " + e.getMessage()
					+ " does not exist.";
	
		} catch (NotEnoughMoneyException e) {
			output = "Not enough money, " + e.getMissingMoney()
					+ " missing.";
	
		} catch (PlayerAlreadyExistsException e) {
			output = "Player " + e.getMessage()
					+ " already exists";
		} catch (RemoveShareException e) {
			output = e.getMessage();
		} catch (UnknownMIMETypeException e) {
			output = "Unknown MIMEType: " + e.getMessage();
		}
		
		callConsoleOutputHandlers(new ConsoleOutputEvent(this, output));
		return output;
		
	}
}
