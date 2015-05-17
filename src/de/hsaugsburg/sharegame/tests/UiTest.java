package de.hsaugsburg.sharegame.tests;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.AccountManagerImpl;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.commands.StockGameCommandProcessor;
import de.hsaugsburg.sharegame.shares.RandomStockPriceProvider;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class UiTest {
	public static void main(String[] args) {
		Share[] shares = {new Share("SpaceX", 10500), new Share("Boing", 12000),new Share("Siemens", 20000),
				new Share("SAP", 12000) , new Share("Audi", 10000)};
		
		StockPriceProvider provider;
		provider = new RandomStockPriceProvider(shares, 1000, 1, 1000);
		//provider = new ConstStockPriceProvider(shares);
		
		AccountManager am = new AccountManagerImpl(provider);
		
		StockGameCommandProcessor processor = new StockGameCommandProcessor(
				System.in, System.out, am, provider);
		processor.start();
	}
}
