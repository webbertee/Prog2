package de.hsaugsburg.sharegame.shares;

import java.util.Random;

import de.hsaugsburg.sharegame.assets.Share;



public class RandomStockPriceProvider extends StockPriceProvider {
	private float factor;
	private Random rndGen;
	
	
	/**
	 * @param deviation standard deviation of stockprice change in percent
	 */
	public RandomStockPriceProvider(Share[] shares, long delay, long period, float deviation) {
		super(shares);
		factor = deviation / 100;
		rndGen = new Random();
		super.startUpdate(delay, period);
	}
	

	@Override
	protected void updateShareRate(Share share) {
		double rnd = share.getValue() * (1 + rndGen.nextGaussian()  * factor);
		share.setValue(Math.round(rnd));
	}
}
