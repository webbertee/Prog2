package de.hsa.sharegame.transactions;



public abstract class TransactionFormater {
	public static TransactionFormater getFormater(String MIMEType){
		for(Formaters s : Formaters.values()) {
			if(s.getMIMEType().equals(MIMEType))
				return s.getInstance();
		}
		throw new UnknownMIMETypeException(MIMEType);
	}
	
	public abstract String format(Transaction t);
	
	public String preFormat() {
		return "";
	}
	
	public String postFormat() {
		return "";
	}
}
