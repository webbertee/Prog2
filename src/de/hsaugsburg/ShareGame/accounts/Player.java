package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.assets.CashAccount;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.assets.ShareDepositAccount;

public class Player {
	private CashAccount cashAccount;
	private ShareDepositAccount shareDepositAccount;
	private String name;

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

	public long getShareValue() {
		return shareDepositAccount.getValue();
	}

}