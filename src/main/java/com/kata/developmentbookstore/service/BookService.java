package com.kata.developmentbookstore.service;

import java.util.List;

import com.kata.developmentbookstore.model.BookInfo;

interface BookService {

	List<BookInfo> getAllBooks();
}
