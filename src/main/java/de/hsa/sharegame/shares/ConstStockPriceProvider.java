package de.hsa.sharegame.shares;

import de.hsa.sharegame.assets.Share;

public class ConstStockPriceProvider extends StockPriceProvider {

	public ConstStockPriceProvider(Share[] shares) {
		super(shares);
	}

	@Override
	protected void updateShareRate(Share share) {
		
	}

}
