package de.hsa.sharegame.launchers;

import java.util.Locale;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.AccountManagerImpl;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.commands.StockGameCommandProcessor;
import de.hsa.sharegame.shares.ConstStockPriceProvider;
import de.hsa.sharegame.shares.HistoricalStockPriceProvider;
import de.hsa.sharegame.shares.RandomStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;

public class App {
	public static void main(String[] args) {
		// Share[] shares = {new Share("SpaceX", 10500), new Share("Boing",
		// 12000),new Share("Siemens", 20000),
		// new Share("SAP", 12000) , new Share("Audi", 10000)};
		//
		
		//Interpret console arguments
		boolean nogui = true;
		for(String s : args) {
			if(s.equals("nogui"))
				nogui = true;
			
			if(s.startsWith("lang=")) {
				Locale.setDefault(new Locale(s.substring("lang=".length())));
			}
		}
		
		StockPriceProvider provider;
		provider = new HistoricalStockPriceProvider(500);
		AccountManager am = new AccountManagerImpl(provider);
		StockGameCommandProcessor processor;
		
		if (nogui) {
			processor = new StockGameCommandProcessor(System.in, System.out,
					am, provider);
		processor.start();
			
		} else {
			
		}
	}
}
