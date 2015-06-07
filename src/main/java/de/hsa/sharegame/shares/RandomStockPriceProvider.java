package de.hsa.sharegame.shares;

import java.util.Random;

import de.hsa.sharegame.assets.Share;



public class RandomStockPriceProvider extends StockPriceProvider {
	
	
	
	public RandomStockPriceProvider(Share[] shares, long period) {
		super(shares);
		super.startUpdate(period, period);
	}
	

	@Override
	protected void updateShareRate(Share share) {
		long rnd = share.getValue() + Math.round(100*Math.random()) * randomSign();
		if(rnd > 0)
			share.setValue(rnd);
	}


	private long randomSign() {
		return (int) (Math.round(Math.random() * 2 -1));
	}
}
