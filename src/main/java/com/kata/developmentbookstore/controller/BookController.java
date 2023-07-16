package com.kata.developmentbookstore.controller;

import java.util.Arrays;
import java.util.List;

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

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/getAllBooks")
    public List<BookInfo> getAllBooksEndpoint() {
	   return Arrays.asList(BookInfo.values());
    }
	
	@PostMapping("/calculateTotalPrice")
	public double calculateTotalPrice(@RequestBody List<Book> selectedBooks) {
		if (selectedBooks == null || selectedBooks.isEmpty()) {
			return 0.0;
		}
		double totalPrice = 0.0;
		int totalBooksInCart = selectedBooks.size();
		totalPrice = totalBooksInCart * 50;
		return totalPrice;
	}
}
