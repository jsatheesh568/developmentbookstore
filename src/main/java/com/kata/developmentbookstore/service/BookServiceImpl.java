package com.kata.developmentbookstore.service;

import java.util.List;

import com.kata.developmentbookstore.model.BookInfo;

public class BookServiceImpl implements BookService {

	public List<BookInfo> books;
	
	@Override
	public List<BookInfo> getAllBooks() {
		return books;
	}

}
