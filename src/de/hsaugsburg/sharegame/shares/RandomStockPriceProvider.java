package de.hsaugsburg.sharegame.shares;

import java.util.Random;

import de.hsaugsburg.sharegame.assets.Share;



public class RandomStockPriceProvider extends StockPriceProvider {
	private long deviaton;
	private Random rndGen;
	
	
	/**
	 * @param deviation standard deviation of change in stockprice in cent
	 */
	public RandomStockPriceProvider(Share[] shares, long delay, long period, long deviation) {
		super(shares);
		this.deviaton = deviation;
		rndGen = new Random();
		super.startUpdate(delay, period);
	}
	

	@Override
	protected void updateShareRate(Share share) {
		long rnd = share.getValue()  + Math.round(rndGen.nextGaussian()*deviaton);
		if(rnd > 0)
			share.setValue(rnd);
	}
}
