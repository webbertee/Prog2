package de.hsa.sharegame.transactions;

public class UnknownMIMETypeException extends RuntimeException {
	public UnknownMIMETypeException(String type) {
		super(type);
	}

}
