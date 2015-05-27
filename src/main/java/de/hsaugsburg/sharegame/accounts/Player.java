package de.hsaugsburg.sharegame.accounts;

import java.util.Date;

import de.hsaugsburg.sharegame.accounts.Transaction.Type;
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
	private TransactionHistory history;


	public Player(String name, long cash) {
		this.name = name;
		cashAccount = new CashAccount(name + "_cash", cash);
		shareDepositAccount = new ShareDepositAccount(name + "_shares");
		history = new TransactionHistory(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void buyShare(Share share, int count) throws NotEnoughMoneyException {
		long amount = share.getValue() * count;
		cashAccount.remove(amount);
		shareDepositAccount.addShare(share, count);
		history.addTransaction(Type.DEBIT, amount, share.getName(), count,
				"market", new Date());
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


	public void sellShare(Share share, int count) {
		shareDepositAccount.removeShare(share, count);
		long amount = share.getValue() * count;
		cashAccount.add(amount);
		history.addTransaction(Type.CREDIT, amount, share.getName(), count,
				"market", new Date());
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
	
	public String getHistoryByTime() {
		return history.listByTime();
	}
	
	public String getHistoryByShares() {
		return history.listByShares();
	}
	
	public String getShareHistory(String name) {
		return history.listShare(name);
	}

}