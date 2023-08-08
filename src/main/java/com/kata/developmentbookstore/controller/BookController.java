package com.kata.developmentbookstore.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.kata.developmentbookstore.exception.HttpMethodNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import com.kata.developmentbookstore.exception.BookValidationException;
import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;
import com.kata.developmentbookstore.service.BookService;

@RestController
@Validated
@RequestMapping("/development-bookstore/v1")
public class BookController {

	private final BookService bookService;
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books")
	public ResponseEntity<List<BookInfo>> getBooks() {
		logger.info("Received request to get all books");
		List<BookInfo> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}



	@PostMapping("/calculate-price")
	public ResponseEntity<?> calculateTotalPrice(@Valid @RequestBody List<Book> books, BindingResult bindingResult) {
		logger.info("Received request to calculate total price");

		if (books == null || books.isEmpty()) {
			 return ResponseEntity.badRequest().body(Collections.singletonMap("message", "The cart is empty"));
		}

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			logger.error("Validation errors occurred: {}", errors);
			throw new BookValidationException("An error occurred", errors);
		}

		double totalPrice = bookService.calculateTotalPrice(books);
		logger.info("Total price calculated: {}", totalPrice);
		return ResponseEntity.ok(totalPrice);
	}
}
