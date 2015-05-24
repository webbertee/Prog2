package de.hsaugsburg.sharegame.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class TransactionHistory {
	private static final String sep = System.getProperty("line.separator");
	private List<Transaction> history;
	private final String owner;
	
	public TransactionHistory(String owner) {
		this.owner = owner;
		history = new ArrayList<Transaction>();
	}
	
	public void addTransaction(Transaction.Type type, long amount, String share, int count, String parcipiant, Date date) {
		history.add(new Transaction(owner, type,amount, share,count, parcipiant, date));
	}
	
	public String listByShares() {
		StringBuffer str = new StringBuffer();
		List<Transaction> tmp = new ArrayList<Transaction>(history);
		tmp.sort((Transaction t1, Transaction t2)-> t1.getShare().compareTo(t2.getShare()));
		concat(str,tmp);
		return str.toString();
	}
	
	public String listShare(String name) {
		StringBuffer str = new StringBuffer();
		List<Transaction> tmp = new ArrayList<Transaction>(history);
		tmp.removeIf((Transaction t)-> !t.getShare().equals(name));
		concat(str,tmp);
		return str.toString();
	}
	
	public String listByTime() {
		StringBuffer str = new StringBuffer("Transaction History of " + owner + sep);
		concat(str,history);
		return str.toString();
	}
	
	private void concat(StringBuffer str, List<Transaction> lst) {
		for(Transaction t : lst){
			str.append(t.toString() + sep);
		}
	}
}
