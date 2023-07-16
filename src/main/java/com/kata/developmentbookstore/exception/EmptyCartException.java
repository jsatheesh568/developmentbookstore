package com.kata.developmentbookstore.exception;

public class EmptyCartException extends RuntimeException {
	public EmptyCartException(String message) {
		super(message);
	}
}	
