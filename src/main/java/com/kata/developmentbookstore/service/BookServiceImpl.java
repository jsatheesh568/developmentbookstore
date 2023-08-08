package com.kata.developmentbookstore.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kata.developmentbookstore.constants.BookstoreConstants;
import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.model.BookInfo;

@Service
public class BookServiceImpl implements BookService {

	public static final double BASE_PRICE = 50.0;
	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	public List<BookInfo> books;

	@Override
	public List<BookInfo> getAllBooks() {
		logger.info("Fetching all books");
		return Arrays.asList(BookInfo.values());
	}

	public double calculateTotalPrice(List<Book> selectedBooks) {
		logger.info("Calculating total price");
		if (selectedBooks != null && selectedBooks.isEmpty()) {
			return BookstoreConstants.ZERO;
		}
		Map<String, Long> bookCountByTitle = selectedBooks.stream()
				.collect(Collectors.groupingBy(Book::getTitle, Collectors.counting()));

		int distinctBooksInCart = bookCountByTitle.size();
		int totalBooksInCart = selectedBooks.size();

		double totalPrice = BookstoreConstants.ZERO;
		if (distinctBooksInCart == BookstoreConstants.FIVE && totalBooksInCart == BookstoreConstants.FIVE) {
			totalPrice += totalBooksInCart * BookstoreConstants.BASE_PRICE
					* (BookstoreConstants.ONE - BookstoreConstants.DISCOUNT_5_BOOKS / BookstoreConstants.HUNDRED);
		} else if (distinctBooksInCart >= BookstoreConstants.TWO && distinctBooksInCart <= BookstoreConstants.FIVE
				&& totalBooksInCart > BookstoreConstants.ONE) {
			switch (distinctBooksInCart) {
			case 2:
				totalPrice = calculateDiscountedPrice(totalBooksInCart, BookstoreConstants.DISCOUNT_2_BOOKS);
				break;
			case 3:
				totalPrice = calculateDiscountedPrice(totalBooksInCart, BookstoreConstants.DISCOUNT_3_BOOKS);
				break;
			case 4:
				totalPrice = calculatePriceWithSets(totalBooksInCart, BookstoreConstants.DISCOUNT_4_BOOKS);
				break;
			case 5:
				totalPrice = calculatePriceWithSetsAndAdditionalBooks(totalBooksInCart);
				break;
			default:
				totalPrice = totalBooksInCart * BookstoreConstants.BASE_PRICE;
				break;
			}
		} else {
			totalPrice += totalBooksInCart * BookstoreConstants.BASE_PRICE;
		}
		return Math.floor(totalPrice * BookstoreConstants.HUNDRED) / BookstoreConstants.HUNDRED;
	}

	private double calculateDiscountedPrice(int totalBooks, double discountPercentage) {
		return totalBooks * BookstoreConstants.BASE_PRICE * (BookstoreConstants.ONE - discountPercentage / BookstoreConstants.HUNDRED);
	}

	private double calculatePriceWithSets(int totalBooks, double discountPercentage) {
		if (totalBooks % BookstoreConstants.FOUR == 0) {
			return (totalBooks / BookstoreConstants.FOUR) * (BookstoreConstants.FOUR * BookstoreConstants.BASE_PRICE
					* (BookstoreConstants.ONE - discountPercentage / BookstoreConstants.HUNDRED));
		} else {
			int completeBookSetsCount = totalBooks / BookstoreConstants.FOUR;
			int additionalBooks = totalBooks % BookstoreConstants.FOUR;
			return (completeBookSetsCount * (BookstoreConstants.THREE * BookstoreConstants.BASE_PRICE
					* (BookstoreConstants.ONE - discountPercentage / BookstoreConstants.HUNDRED)))
					+ (additionalBooks * BookstoreConstants.BASE_PRICE);
		}
	}

	private double calculatePriceWithSetsAndAdditionalBooks(int totalBooks) {
		return (BookstoreConstants.TWO * (BookstoreConstants.FOUR * BookstoreConstants.BASE_PRICE * (BookstoreConstants.ONE - BookstoreConstants.DISCOUNT_4_BOOKS / BookstoreConstants.HUNDRED)))
				+ (totalBooks - BookstoreConstants.EIGHT) * BookstoreConstants.BASE_PRICE * (BookstoreConstants.ONE - BookstoreConstants.DISCOUNT_4_BOOKS / BookstoreConstants.HUNDRED);
	}

}
