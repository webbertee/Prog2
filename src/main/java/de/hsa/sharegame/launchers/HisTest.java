package de.hsa.sharegame.launchers;

import java.io.IOException;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.AccountManagerImpl;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.gui.StockViewer;
import de.hsa.sharegame.shares.HistoricalStockPriceProvider;
import de.hsa.sharegame.shares.RandomStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;

public class HisTest {
	public static void main(String[] args) throws IOException {		
		StockPriceProvider provider;
		provider = new HistoricalStockPriceProvider(50);
		//provider = new ConstStockPriceProvider(shares);
		
		//AccountManager am = new AccountManagerImpl(provider);
		
		new StockViewer(provider).start();
	}
}
