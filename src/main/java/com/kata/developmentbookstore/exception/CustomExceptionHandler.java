package com.kata.developmentbookstore.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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

	@ExceptionHandler(HttpMethodNotAllowedException.class)
	public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
		String errorMessage = "The requested HTTP method is not supported for this resource.";
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorMessage);
	}

}
