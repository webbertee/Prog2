package de.hsa.sharegame.transactions;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class TransactionHistory {
	private List<Transaction> history;
	private final String owner;
	
	public TransactionHistory(String owner) {
		this.owner = owner;
		history = new ArrayList<Transaction>();
	}
	
	public void addTransaction(Transaction.Type type, long amount, String share, int count, String parcipiant, Date date) {
		history.add(new Transaction(owner, type,amount, share,count, parcipiant, date));
	}
	
	public int getCount() {
		return history.size();
	}
	
	public Transaction getTransaction(int i) {
		return history.get(i);
	}

	public void listByShares(Writer writer, String MIMEType) {
		Stream<Transaction> str = history.stream().sorted((Transaction t1, Transaction t2)-> t1.getShare().compareTo(t2.getShare()));
		printOut(str, writer, MIMEType);
	}
	
	public void listShare(Writer writer, String MIMEType, String name) {
		printOut(history.stream().filter((Transaction t) -> t.getShare().equals(name)), writer, MIMEType);
	}
	
	public void listByTime(Writer writer, String MIMEType) {
		printOut(history.stream(), writer, MIMEType);
	}
	
	private void printOut(Stream<Transaction> str, Writer writer, String MIMEType) {
		str.forEachOrdered((Transaction t) -> {
			try {
				writer.append(TransactionFormater.getFormater(MIMEType).format(t));
			}catch(IOException e) {
				System.out.println("cannot write into file");
			}});;
	}
}
