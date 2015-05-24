package de.hsaugsburg.sharegame.agent;

import java.util.TimerTask;

import de.hsaugsburg.sharegame.accounts.Player;
import de.hsaugsburg.sharegame.accounts.exceptions.BotAlreadyWorkingException;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;
import de.hsaugsburg.sharegame.timer.SingleTimer;

public class SimpleAgent {
	private final Player p;
	private final StockPriceProvider spp;
	private TimerTask task;



	public SimpleAgent(Player p, StockPriceProvider spp) {
		this.p = p;
		this.spp = spp;
	}
	
	public void stop() {
		if(task != null)
			task.cancel();
	}

	public void start(long intervall) {
		if(task != null) 
			throw new BotAlreadyWorkingException();
		
		task = new TimerTask() {

			@Override
			public void run() {
				String[] shares = spp.getShareNames();
				sellShares(shares);
				buyShares(shares);
			}

			private void sellShares(String[] shares) {
				Share share;
				int count;
				for(String shareName : shares) {
					share = spp.getShare(shareName);
					count = p.getShareCount(share);
					if(share.getValue() * count - p.getSharesBuyValue(share) > 0 && count > 0) {
						p.sellShare(share, count);
					}
				}
			}
			
			private void buyShares(String[] shares) {
				long perShare = p.getCashValue() / shares.length;
				int count;
				for(int i = 0; i < shares.length; i++) {
					count = (int) Math.floor(perShare / spp.getShareValue(shares[i]));
					if(count > 0)
						p.buyShare(spp.getShare(shares[i]), count);
				}
				
			}
		};
		SingleTimer.getTimer().scheduleAtFixedRate(task, 0, intervall);
	}


}
