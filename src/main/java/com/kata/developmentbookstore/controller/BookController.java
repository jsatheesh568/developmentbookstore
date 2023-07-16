package com.kata.developmentbookstore.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kata.developmentbookstore.exception.BookValidationException;
import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;
import com.kata.developmentbookstore.service.BookService;

@RestController
@Validated
public class BookController {

	private final BookService bookService;
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/getAllBooks")
	public List<BookInfo> getAllBooksEndpoint() {
		logger.info("Received request to get all books");
		return Arrays.asList(BookInfo.values());
	}

	@PostMapping("/calculateTotalPrice")
	public ResponseEntity<?> calculateTotalPrice(@Valid @RequestBody List<Book> books, BindingResult bindingResult) {
		logger.info("Received request to calculate total price");

		if (books == null || books.isEmpty()) {
			return ResponseEntity.badRequest().body("The cart is empty.");
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
