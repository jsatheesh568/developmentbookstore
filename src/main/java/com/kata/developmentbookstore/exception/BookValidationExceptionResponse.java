package com.kata.developmentbookstore.exception;

import java.util.List;

public class BookValidationExceptionResponse {

	private String message;
	private List<String> errors;

	public BookValidationExceptionResponse(String message, List<String> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}
}
