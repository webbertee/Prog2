package de.hsaugsburg.sharegame.shares;

import java.util.TimerTask;

import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.exceptions.UnknownShareException;
import de.hsaugsburg.sharegame.timer.SingleTimer;

/**
 * Important: Classes who extend this Class must call startUpdate in the constructor if
 * the rates should be updated periodically.
 */
public abstract class StockPriceProvider implements StockPriceInfo {
	private final Share[] shares;
	
	
	public StockPriceProvider(Share[] shares) {
		this.shares = shares;
	}

	@Override
	public long getShareValue(String name) {
		return getShare(name).getValue();
	}

	@Override
	public String getAllShares() {
		StringBuilder out = new StringBuilder();
		for(int i = 0; i < shares.length; i++) {
			out.append(shares[i].getName() + " " + shares[i].getValue());
			out.append(", ");
		}
		return out.toString();
	}
	
	protected void startUpdate(long delay, long period) {
		SingleTimer.getTimer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
               updateShareRates();
            }
        }, delay, period);
	}
	
	protected final void updateShareRates() {
		for(Share s : shares)
			updateShareRate(s);
	}
	
	protected abstract void updateShareRate(Share share);
	
	@Override
	public String[] getShareNames() {
		String[] out = new String[shares.length];
		for(int i = 0; i < out.length; i++)
			out[i] = shares[i].getName();
		return out;
			
	}
	private int getShareIndex(String share) {
		for(int i = 0; i < shares.length; i++) {
			if(shares[i].getName().equals(share))
				return i;
		}
		return -1;
	}
	
	public Share getShare(String name) {
		int shareI = getShareIndex(name);
		if(shareI < 0)
			throw new UnknownShareException(name);
		return shares[shareI];	
	}
}
