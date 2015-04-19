package de.hsaugsburg.sharegame.shares;

import de.hsaugsburg.sharegame.assets.Share;

public class ConstStockPriceProvider extends StockPriceProvider {

	public ConstStockPriceProvider(Share[] shares) {
		super(shares);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateShareRate(Share share) {
		//ShareRate constant, nothing to do
		
	}

}
