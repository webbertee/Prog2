package de.hsa.sharegame.transactions;

import de.hsa.sharegame.transactions.formaters.HTMLFormater;
import de.hsa.sharegame.transactions.formaters.PlainFormater;


public enum Formaters {
		PLAIN("text/plain", PlainFormater.class), 
		HTML("text/html", HTMLFormater.class);
		
		private final String MIMEType;
		private final Class<?> formater;
		private TransactionFormater instance;
		private Formaters(String MIMEType, Class<?> formater) {
			this.MIMEType = MIMEType;
			this.formater = formater;
		}
		
		public String getMIMEType() {
			return MIMEType;
		}
		public TransactionFormater getInstance() {
			if(instance == null) {
				try {
					instance = (TransactionFormater) formater.newInstance();
				} catch (InstantiationException e) {
					throw new IllegalFormaterException(formater.getName());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return instance;
		}
}
