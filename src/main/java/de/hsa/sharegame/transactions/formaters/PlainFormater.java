package de.hsa.sharegame.transactions.formaters;

import de.hsa.sharegame.transactions.Transaction;
import de.hsa.sharegame.transactions.TransactionFormater;

public class PlainFormater extends TransactionFormater{

	@Override
	public String format(Transaction t) {
		return t.toString() + System.getProperty("line.separator");
	}
}
