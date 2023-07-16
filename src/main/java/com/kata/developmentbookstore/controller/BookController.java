package com.kata.developmentbookstore.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.developmentbookstore.model.BookInfo;

@RestController
public class BookController {

	@GetMapping("/getAllBooks")
    public List<BookInfo> getAllBooksEndpoint() {
	   return Arrays.asList(BookInfo.values());
    }
}
