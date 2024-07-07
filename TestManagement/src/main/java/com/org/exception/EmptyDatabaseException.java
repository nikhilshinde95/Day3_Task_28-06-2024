package com.org.exception;

public class EmptyDatabaseException extends RuntimeException {
	
	public EmptyDatabaseException(String msg) {
		super(msg);
	}

}
