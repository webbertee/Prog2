package de.hsaugsburg.sharegame.accounts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private final DecimalFormat df = new DecimalFormat("#0.00€");
	private final String owner;
	public enum Type{CREDIT, DEBIT};
	private final Type type;
	private final String purpose;
	private final String participant;
	private final Date date;
	private final long amount;

	
	public Transaction(String owner, Type type, long amount, String purpose, String participant,
			Date date) {
		this.owner = owner;
		this.type = type;
		this.amount = amount;
		this.purpose = purpose;
		this.participant = participant;
		this.date = date;
		
	}

	public long getAmount() {
		return amount;
	}

	public Type getType() {
		return type;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getParticipant() {
		return participant;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(sdf.format(date) + ": ");
		str.append(df.format(amount/100) + " ");
		if(type == Type.CREDIT) {
			str.append(participant + " -> " + owner);
		} else {
			str.append(owner + " -> " + participant);
		}
		str.append(": " + purpose);
		
		return str.toString();
	}
}
