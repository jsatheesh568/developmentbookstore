package com.kata.developmentbookstore.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Controller
@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(EmptyCartException.class)
	public ResponseEntity<String> handleEmptyCartException(EmptyCartException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(BookValidationException.class)
	public ResponseEntity<BookValidationExceptionResponse> handleBookValidationException(BookValidationException ex) {
		List<String> errors = ex.getErrors();
		BookValidationExceptionResponse response = new BookValidationExceptionResponse("Invalid book data.", errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An error occurred with book object value.");
	}
}
