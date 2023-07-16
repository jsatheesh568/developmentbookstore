package com.kata.developmentbookstore.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;
import com.kata.developmentbookstore.service.BookService;

@RestController
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
	public ResponseEntity<Double> calculateTotalPrice(@RequestBody List<Book> books) {
		logger.info("Received request to calculate total price");
		double totalPrice = bookService.calculateTotalPrice(books);
		logger.info("Total price calculated: {}", totalPrice);
		return ResponseEntity.ok(totalPrice);
	}
}
