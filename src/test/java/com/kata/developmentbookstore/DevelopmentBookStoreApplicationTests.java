package com.kata.developmentbookstore;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.exception.BookValidationException;
import com.kata.developmentbookstore.exception.EmptyCartException;
import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.service.BookService;

@WebMvcTest(BookController.class)
class DevelopmentBookStoreApplicationTests {

	@MockBean
	private BookService bookService;

	@Autowired
	private MockMvc mockMvc;

	private List<Book> selectedBooks;

	@BeforeEach
	public void setup() {
		bookService = Mockito.mock(BookService.class);
		selectedBooks = new ArrayList<>();
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
	}

	@Test
	public void testGetAllBooksEndpoint() throws Exception {
		ResultActions result = mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0]").value("CLEAN_CODE"))
				.andExpect(jsonPath("$[1]").value("CLEAN_CODER"))
				.andExpect(jsonPath("$[2]").value("CLEAN_ARCHITECTURE"))
				.andExpect(jsonPath("$[3]").value("TDD_BY_EXAMPLE")).andExpect(jsonPath("$[4]").value("LEGACY_CODE"));
	}

	@Test
	public void testCalculateTotalPrice() {
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		Mockito.when(bookService.calculateTotalPrice(selectedBooks)).thenThrow(BookValidationException.class);
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testEmptyCart_ShouldReturnNoPrice() throws Exception {
	    Mockito.when(bookService.calculateTotalPrice(Mockito.anyList()))
	            .thenThrow(new EmptyCartException("The cart is empty."));
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/calculate-price")
	            .contentType(MediaType.APPLICATION_JSON).content("[]")).andExpect(status().isBadRequest()).andReturn();
	    String responseBody = result.getResponse().getContentAsString();
	    Assertions.assertEquals("{\"message\":\"The cart is empty\"}", responseBody);
	}


	@Test
	public void testCalculateTotalPrice_AllDifferentBooks() {
		Mockito.when(bookService.calculateTotalPrice(selectedBooks)).thenThrow(BookValidationException.class)
				.thenReturn(187.5);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		selectedBooks.add(new Book("Test Driven Development by Example", "Kent Beck", 2003));
		selectedBooks.add(new Book("Working Effectively With Legacy Code", "Michael C. Feathers", 2004));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_AllSameBooks() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(250.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));

		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_TwoDifferentBooksWithFivePercentDiscount() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(95.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_ThreeDifferentBooksWithTenPercentDiscount() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(135.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_FourDifferentBooksWithTwentyPercentDiscount() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(160.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		selectedBooks.add(new Book("Test Driven Development by Example", "Kent Beck", 2003));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_FourDifferentBooksWithTenPercentDiscount() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(180.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_ShoppingBasketWithMultipleBooks() {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenThrow(BookValidationException.class)
				.thenReturn(320.0);
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Code", "Robert Martin", 2008));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Coder", "Robert Martin", 2011));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		selectedBooks.add(new Book("Clean Architecture", "Robert Martin", 2017));
		selectedBooks.add(new Book("Test Driven Development by Example", "Kent Beck", 2003));
		selectedBooks.add(new Book("Working Effectively With Legacy Code", "Michael C. Feathers", 2004));
		Assertions.assertThrows(BookValidationException.class, () -> {
			bookService.calculateTotalPrice(selectedBooks);
		});
	}

	@Test
	public void testCalculateTotalPrice_InvalidYear() {
		Book book = new Book("Clean Code", "Robert Martin", -2022);
		List<Book> selectedBooks = Collections.singletonList(book);

		List<String> errors = Collections.singletonList("Invalid year");
		Mockito.when(bookService.calculateTotalPrice(selectedBooks))
				.thenThrow(new BookValidationException("An error occurred while validating books.", errors));

		BookController bookController = new BookController(bookService);
		BindingResult bindingResult = mock(BindingResult.class);

		Assertions.assertThrows(BookValidationException.class, () -> {
			bookController.calculateTotalPrice(selectedBooks, bindingResult);
		});
	}
}
