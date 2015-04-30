package de.hsaugsburg.sharegame.assets;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;

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
			throw new NotEnoughMoneyException(this.getName(), amount-value);
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