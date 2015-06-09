package de.hsa.sharegame.accounts;

import java.io.Writer;
import java.util.Date;

import de.hsa.sharegame.agent.SimpleAgent;
import de.hsa.sharegame.assets.CashAccount;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.assets.ShareDepositAccount;
import de.hsa.sharegame.assets.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.shares.StockPriceProvider;
import de.hsa.sharegame.transactions.TransactionHistory;
import de.hsa.sharegame.transactions.Transaction.Type;

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
	
	public void getHistoryByTime(Writer writer, String MIMEType) {
		history.listByTime(writer, MIMEType);
	}
	
	public void getHistoryByShares(Writer writer, String MIMEType) {
		history.listByShares(writer, MIMEType);
	}
	
	public void getShareHistory(Writer writer, String MIMEType, String name) {
		history.listShare(writer, MIMEType, name);
	}

}