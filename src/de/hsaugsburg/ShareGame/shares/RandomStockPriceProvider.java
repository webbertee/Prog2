package de.hsaugsburg.sharegame.shares;

import java.util.Random;

import de.hsaugsburg.sharegame.assets.Share;

public class RandomStockPriceProvider extends StockPriceProvider {
	private Random rndGen;
	public RandomStockPriceProvider(Share[] shares) {
		super(shares);
		rndGen = new Random();
	}
	
	@Override
	protected void updateShareRate(Share share) {
		double rnd = 1 + rndGen.nextGaussian() * 5;
		share.setValue(Math.round((share.getValue() * rnd)));
	}
}
