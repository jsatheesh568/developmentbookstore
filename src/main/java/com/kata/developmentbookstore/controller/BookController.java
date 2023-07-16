package com.kata.developmentbookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.developmentbookstore.model.BookInfo;

@RestController
public class BookController {

	@GetMapping("/getAllBooks")
	public List<BookInfo> getAllBooksEndpoint() {
		List<BookInfo> books = new ArrayList<>();
		books.add(new BookInfo("Clean Code", "Robert C. Martin", 2008));
		return books;
	}
}
