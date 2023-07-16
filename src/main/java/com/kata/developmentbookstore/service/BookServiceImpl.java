package com.kata.developmentbookstore.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;

@Service
public class BookServiceImpl implements BookService {

	public static final double BASE_PRICE = 50.0;
	public List<BookInfo> books;

	@Override
	public List<BookInfo> getAllBooks() {
		return books;
	}

	public double calculateTotalPrice(List<Book> selectedBooks) {
		if (selectedBooks != null && selectedBooks.isEmpty()) {
			return 0.0;
		}
		Map<String, Integer> bookCounts = new HashMap<>();

		for (Book book : selectedBooks) {
			String title = book.getTitle();
			int count = bookCounts.getOrDefault(title, 0);
			bookCounts.put(title, count + 1);
		}

		int distinctBooksInCart = bookCounts.size();
		int totalBooksInCart = selectedBooks.size();
		double totalPrice = 0.0;
		if (totalBooksInCart == 5 && distinctBooksInCart == 5) {
			totalPrice += totalBooksInCart * BASE_PRICE * (1 - 25.0 / 100.0);
		} else {
			return totalPrice = totalBooksInCart * BASE_PRICE;
		}
		return totalPrice;
	}

}
