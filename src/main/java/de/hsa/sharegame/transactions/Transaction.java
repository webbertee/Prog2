package de.hsa.sharegame.transactions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	
	
	private final String owner;
	public enum Type{CREDIT, DEBIT};
	private final Type type;
	private final String share;
	private final int count;
	private final String participant;
	private final Date date;
	private final long amount;

	
	public Transaction(String owner, Type type, long amount, String share, int count, String participant,
			Date date) {
		this.owner = owner;
		this.type = type;
		this.amount = amount;
		this.count = count;
		this.share = share;
		this.participant = participant;
		this.date = date;
	}

	public long getAmount() {
		return amount;
	}

	public Type getType() {
		return type;
	}

	public String getShare() {
		return share;
	}

	public String getParticipant() {
		return participant;
	}

	public Date getDate() {
		return date;
	}
	
	public String getOwner() {
		return owner;
	}
	//TODO: Lokalisation
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(SimpleDateFormat.getDateInstance().format(date) + ": ");
		str.append(DecimalFormat.getCurrencyInstance().format(amount/100.0) + " ");
		if(type == Type.CREDIT) {
			str.append(participant + " -> " + owner);
		} else {
			str.append(owner + " -> " + participant);
		}
		str.append(": " + count + " " + share);
		
		return str.toString();
	}

	public int getCount() {
		return count;
	}
}
