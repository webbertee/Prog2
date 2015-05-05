package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.agent.SimpleAgent;
import de.hsaugsburg.sharegame.assets.CashAccount;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.assets.ShareDepositAccount;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class Player {
	private CashAccount cashAccount;
	private ShareDepositAccount shareDepositAccount;
	private String name;
	private SimpleAgent bot;

	/**
	 * 
	 * @param name
	 */
	public Player(String name, long cash) {
		this.name = name;
		cashAccount = new CashAccount(name + "_cash", cash);
		shareDepositAccount = new ShareDepositAccount(name + "_shares");
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param share
	 * @param count
	 * @throws NotEnoughMoneyException 
	 */
	public void buyShare(Share share, int count) throws NotEnoughMoneyException {
		cashAccount.remove(share.getValue() * count);
		shareDepositAccount.addShare(share, count);
	}
	
	public void addBot(StockPriceProvider spp, long intervall) {
		if(this.bot == null)
			bot = new SimpleAgent(this, spp);
		
		bot = new SimpleAgent(this, spp);
		bot.start(intervall);
	}
	
	public void removeBot() {
		if(this.bot != null) 
			bot.stop();
	}

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public void sellShare(Share share, int count) {
		shareDepositAccount.removeShare(share, count);
		cashAccount.add(share.getValue() * count);
	}

	public long getCashValue() {
		return cashAccount.getValue();
	}

	public long getDepositValue() {
		return shareDepositAccount.getValue();
	}

	public int getShareCount(Share share) {
		return shareDepositAccount.getSharesCount(share);
	}

	public long getSharesValue(Share share) {
		return shareDepositAccount.getSharesValue(share);
	}

	public long getSharesBuyValue(Share share) {
		return shareDepositAccount.getSharesBuyValue(share);
	}

}