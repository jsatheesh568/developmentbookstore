package com.kata.developmentbookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
