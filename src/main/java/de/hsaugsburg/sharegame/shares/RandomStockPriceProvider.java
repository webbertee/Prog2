package de.hsaugsburg.sharegame.shares;

import java.util.Random;

import de.hsaugsburg.sharegame.assets.Share;



public class RandomStockPriceProvider extends StockPriceProvider {
	private long deviaton;
	private Random rndGen;
	
	
	
	public RandomStockPriceProvider(Share[] shares, long delay, long period, long deviation) {
		super(shares);
		this.deviaton = deviation;
		rndGen = new Random();
		super.startUpdate(delay, period);
	}
	

	@Override
	protected void updateShareRate(Share share) {
		//long rnd = share.getValue()  + Math.round(rndGen.nextGaussian()*deviaton);
		//long rnd = (share.getValue() + randomSign() * ((long) (share.getValue() * Math.random()*0.1)));
		long rnd = share.getValue() + 10*Math.round(Math.random() >= 0.5 ? +1 : -1);
		if(rnd > 0)
			share.setValue(rnd);
	}


//	private long randomSign() {
//		return (int) (Math.round(Math.random() * 2 -1));
//	}
}
