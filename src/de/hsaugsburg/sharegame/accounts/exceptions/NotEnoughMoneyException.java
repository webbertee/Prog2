package de.hsaugsburg.sharegame.accounts.exceptions;

public class NotEnoughMoneyException extends Exception {
	
	public NotEnoughMoneyException(String accountName, long missingMoney) {
		super();
		this.missingMoney = missingMoney;
		this.accountName = accountName;
	}

	private final long missingMoney;
	private final String accountName;

	public long getMissingMoney() {
		return missingMoney;
	}
}
