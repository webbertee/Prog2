package de.hsaugsburg.ShareGame.Assets;

import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.NotEnoughMoneyException;

public class CashAccount extends Asset {

	private long value;

	
	public void add(long amount) {
		value += amount;
	}
	
	public void remove(long amount) throws NotEnoughMoneyException {
		if(value >= amount) {
			value -= amount;
			return;
		}else {
			throw new NotEnoughMoneyException();
		}
	}
	public long getValue() {
		return this.value;
	}

	public String toString() {
		return "CashAccount " + this.getName() + " with Value " + getValue();
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public CashAccount(String name, long value) {
		super(name);
		this.value = value;
	}

}