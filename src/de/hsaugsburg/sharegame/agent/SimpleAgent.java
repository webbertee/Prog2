package de.hsaugsburg.sharegame.agent;

import java.util.HashMap;
import java.util.TimerTask;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;
import de.hsaugsburg.sharegame.timer.SingleTimer;

public class SimpleAgent {
	private String name;
	private AccountManager am;
	private StockPriceProvider spp;
	private long intervall;
	private HashMap<String, Long> priceMap;
	private final int BUYCOUNT = 10;

	public SimpleAgent(String name, long cash, AccountManager am,
			StockPriceProvider spp, long intervall) {
		this.name = name;
		this.am = am;
		this.spp = spp;
		this.intervall = intervall;
		priceMap = new HashMap<String, Long>();
		for (String s : spp.getShareNames())
			priceMap.put(s, spp.getShareValue(s));
		
		am.addPlayer(name, cash);

	}

	public void start() {
		SingleTimer.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				long price;
				int count;
				for (String s : spp.getShareNames()) {
					// get Value and amount
					price = spp.getShareValue(s);
					count = am.getPlayerSharesCount(name, s);

					// if price has risen -> sell; else buy
					if (price > priceMap.get(s)) {
						if (count > 0) {
							am.sellShare(name, s, count);
						}
					} else {
						if (am.getPlayerCashValue(name) >= BUYCOUNT * price) {
							am.buyShare(name, s, BUYCOUNT);
						}
					}

					// save new values
					priceMap.put(s, spp.getShareValue(s));
				}
			}

		}, 0);
	}

}
