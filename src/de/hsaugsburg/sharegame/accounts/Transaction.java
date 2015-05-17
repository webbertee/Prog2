package de.hsaugsburg.sharegame.accounts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private final String owner;
	public enum Type{CREDIT, DEBIT};
	private final Type type;
	private final String purpose;
	private final String participant;
	private final Date date;
	

	
	public Transaction(String owner, Type type, String purpose, String participant,
			Date date) {
		this.owner = owner;
		this.type = type;
		this.purpose = purpose;
		this.participant = participant;
		this.date = date;
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
	//this is the correct spelling of participant
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(sdf.format(date) + ": ");
		if(type == Type.CREDIT) {
			str.append(participant + " -> " + owner);
		} else {
			str.append(owner + " -> " + participant);
		}
		str.append(": " + purpose);
		
		return str.toString();
	}
}
