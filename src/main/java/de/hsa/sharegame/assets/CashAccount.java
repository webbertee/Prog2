package de.hsa.sharegame.assets;

import de.hsa.sharegame.accounts.exceptions.NotEnoughMoneyException;

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
			throw new NotEnoughMoneyException(amount-value);
		}
	}
	public long getValue() {
		return this.value;
	}

	public String toString() {
		return "CashAccount " + this.getName() + " with Value " + getValue();
		
	}


	public CashAccount(String name, long value) {
		super(name);
		this.value = value;
	}

}