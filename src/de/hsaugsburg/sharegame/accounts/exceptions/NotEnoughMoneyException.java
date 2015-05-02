package de.hsaugsburg.sharegame.accounts.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
	
	public NotEnoughMoneyException(long missingMoney) {
		super();
		this.missingMoney = missingMoney;
	}

	private final long missingMoney;

	public long getMissingMoney() {
		return missingMoney;
	}
}
