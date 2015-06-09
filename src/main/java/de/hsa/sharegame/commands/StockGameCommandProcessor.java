package de.hsa.sharegame.commands;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import de.hsa.commands.AsCommand;
import de.hsa.commands.CommandProcessor;
import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsa.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsa.sharegame.assets.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.assets.exceptions.NotEnoughSharesException;
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
			output = getLangString("UnknownShareException") + e.getShareName();
	
		} catch (UnknownPlayerException e) {
			output = getLangString("UnknownPlayerException") + e.getMessage();
	
		} catch (NotEnoughMoneyException e) {
			output = getLangString("NotEnoughMoneyException") + e.getMissingMoney();
	
		} catch (PlayerAlreadyExistsException e) {
			output = getLangString("PlayerAlreadyExistsException") + e.getMessage();
		} catch (NotEnoughSharesException e) {
			output = getLangString("NotEnoughSharesException") + e.getRemaining();
		} catch (UnknownMIMETypeException e) {
			output = getLangString("UnknownMIMETypeException") + e.getMessage();
		}
		
		callConsoleOutputHandlers(new ConsoleOutputEvent(this, output));
		return output;
		
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
