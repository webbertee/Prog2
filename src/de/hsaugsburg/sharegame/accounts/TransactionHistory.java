package de.hsaugsburg.sharegame.accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistory {
	private List<Transaction> history;
	private final String owner;
	
	public TransactionHistory(String owner) {
		this.owner = owner;
		history = new ArrayList<Transaction>();
	}
	
	public void addTransaction(Transaction.Type type, long amount, String purpose, String parcipiant, Date date) {
		history.add(new Transaction(owner, type,amount, purpose, parcipiant, date));
	}
	
	@Override
	public String toString() {
		String sep = System.getProperty("line.separator");
		StringBuffer str = new StringBuffer("Transaction History of " + owner + sep);
		for(Transaction t : history){
			str.append(t.toString() + sep);
		}
		return str.toString();
	}
}
