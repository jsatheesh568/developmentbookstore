package com.kata.developmentbookstore.service;

import java.util.List;

import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;

public interface BookService {

	List<BookInfo> getAllBooks();
	double calculateTotalPrice(List<Book> selectedBooks);
}
