package com.kata.developmentbookstore.model;

public enum BookInfo {

	CLEAN_CODE("Clean Code", "Robert Martin", 2008), 
	CLEAN_CODER("Clean Coder", "Robert Martin", 2011);
	
	private String title;
	private String author;
	private int year;

	BookInfo(String title, String author, int year) {
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getYear() {
		return year;
	}
}
