package com.kata.developmentbookstore.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Book {

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title cannot exceed 100 characters")
	private String title;

	@NotBlank(message = "Author is required")
	@Size(max = 100, message = "Author name cannot exceed 100 characters")
	private String author;

	@NotNull(message = "Year is required")
	@Positive(message = "Year must be a positive number")
	private Integer year;

	public Book() {

	}

	public Book(
			@NotBlank(message = "Title is required") @Size(max = 100, message = "Title cannot exceed 100 characters") String title,
			@NotBlank(message = "Author is required") @Size(max = 100, message = "Author name cannot exceed 100 characters") String author,
			@NotNull(message = "Year is required") @Positive(message = "Year must be a positive number") Integer year) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(author, title, year);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(title, other.title)
				&& Objects.equals(year, other.year);
	}
}
