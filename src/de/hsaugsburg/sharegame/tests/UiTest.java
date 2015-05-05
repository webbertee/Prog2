package de.hsaugsburg.sharegame.tests;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.AccountManagerImpl;
import de.hsaugsburg.sharegame.agent.SimpleAgent;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.commands.StockGameCommandProcessor;
import de.hsaugsburg.sharegame.shares.ConstStockPriceProvider;
import de.hsaugsburg.sharegame.shares.RandomStockPriceProvider;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;
import de.hsaugsburg.sharegame.viewer.PlayerViewer;
import de.hsaugsburg.sharegame.viewer.Viewer;

public class UiTest {
	public static void main(String[] args) {
		Share[] shares = { new Share("Audi", 10000),
				new Share("SpaceX", 10500), new Share("Siemens", 20000),
				new Share("SAP", 12000)};
		
		StockPriceProvider provider;
		provider = new RandomStockPriceProvider(shares, 1000, 1, 1000);
		//provider = new ConstStockPriceProvider(shares);
		AccountManager am = new AccountManagerImpl(provider);
		
		StockGameCommandProcessor processor = new StockGameCommandProcessor(
				System.in, System.out, am);
		
		new Viewer(provider).start();
		
		am.addPlayer("bot", 1000000);
		new PlayerViewer("bot", am, provider).start();
		processor.startLoop();
		System.exit(0);
	}
}
