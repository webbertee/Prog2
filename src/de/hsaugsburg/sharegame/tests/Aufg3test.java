package de.hsaugsburg.sharegame.tests;

import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.RandomStockPriceProvider;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;
import de.hsaugsburg.sharegame.viewer.Viewer;

public class Aufg3test {
	public static void main(String[] args) {
		Share[] shares = {new Share("Audi", 10000), new Share("SpaceX", 10500), 
				new Share("Siemens", 20000), new Share("SAP", 12000)};
		
		StockPriceProvider provider;
		provider = new RandomStockPriceProvider(shares, 1000, 1000, 1000);
		//provider = new ConstStockPriceProvider(shares);
		new Viewer(provider).start();
	}
}
