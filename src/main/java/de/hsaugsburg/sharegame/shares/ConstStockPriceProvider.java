package de.hsaugsburg.sharegame.shares;

import de.hsaugsburg.sharegame.assets.Share;

public class ConstStockPriceProvider extends StockPriceProvider {

	public ConstStockPriceProvider(Share[] shares) {
		super(shares);
	}

	@Override
	protected void updateShareRate(Share share) {
		
	}

}
