package de.hsaugsburg.sharegame.launchers;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.AccountManagerImpl;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.commands.StockGameCommandProcessor;
import de.hsaugsburg.sharegame.shares.RandomStockPriceProvider;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class LoggingTest {
	public static void main(String[] args) {
		Share[] shares = { new Share("Audi", 10000),
				new Share("SpaceX", 10500), new Share("Siemens", 20000),
				new Share("SAP", 12000) };

		
		StockPriceProvider provider;
		provider = new RandomStockPriceProvider(shares, 1000, 1, 1000);
		AccountManager am = new AccountManagerImpl(provider);
		
		
		StockGameCommandProcessor processor = new StockGameCommandProcessor(
				System.in, System.out, am, provider);
		
		am.addPlayer("player", 10000000);
		am.buyShare("player", "Audi", 1);
		am.buyShare("player", "Audi", 1000);
		
		
		processor.start();
	}
}
