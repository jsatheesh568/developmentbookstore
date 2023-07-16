package com.kata.developmentbookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;

@Service
public class BookServiceImpl implements BookService {

	public List<BookInfo> books;
	
	@Override
	public List<BookInfo> getAllBooks() {
		return books;
	}

	@Override
	@PostMapping("/calculateTotalPrice")
	public double calculateTotalPrice(List<Book> selectedBooks) {
		if (selectedBooks == null || selectedBooks.isEmpty()) {
			return 0.0;
		}
		double totalPrice = 0.0;
		int totalBooksInCart = selectedBooks.size();
		return totalPrice = totalBooksInCart * 50;		
	}

}
