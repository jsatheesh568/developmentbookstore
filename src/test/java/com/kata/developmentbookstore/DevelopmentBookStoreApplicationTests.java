package com.kata.developmentbookstore;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.exception.BookValidationException;
import com.kata.developmentbookstore.model.Book;
import com.kata.developmentbookstore.service.BookService;

class DevelopmentBookStoreApplicationTests {

	@Mock
	private BookService bookService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		bookService = Mockito.mock(BookService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
	}

	@Test
	public void testGetAllBooksEndpoint() throws Exception {
		ResultActions result = mockMvc.perform(get("/getAllBooks").accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0]").value("CLEAN_CODE"))
				.andExpect(jsonPath("$[1]").value("CLEAN_CODER"));
	}

	@Test
	public void testSingleBookPrice() throws Exception {
		double expectedPrice = 50.0;
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(expectedPrice);
		mockMvc.perform(post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON)
				.content("[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008}]"))
				.andExpect(status().isOk()).andExpect(content().string("50.0"));
	}

	@Test
	public void testEmptyCart_ShouldCheckEmptyCart() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList()))
				.thenThrow(new IllegalArgumentException("The cart is empty"));

		mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON)
				.content("[]")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("The cart is empty"));
	}

	@Test
	public void testCalculateTotalBookPrice() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(250.0);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Test Driven Development by Example\",\"author\":\"Kent Beck\",\"year\":2003},{\"title\":\"Working effectively with Legacy Code\",\"author\":\"Michael C. Feathers\",\"year\":2004}]"))
				.andExpect(status().isOk()).andExpect(content().json("250.0"));
	}

	@Test
	public void testCalculateTotalBookPrice_AllDifferentBooks() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(187.5);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Test Driven Development by Example\",\"author\":\"Kent Beck\",\"year\":2003},{\"title\":\"Working effectively with Legacy Code\",\"author\":\"Michael C. Feathers\",\"year\":2004}]"))
				.andExpect(status().isOk()).andExpect(content().json("187.5"));
	}

	@Test
	public void testCalculateTotalPrice_AllBooksSame() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(250.0);
		String content = "[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},"
				+ "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},"
				+ "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},"
				+ "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},"
				+ "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008}]";
		mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON)
				.content(content)).andExpect(status().isOk()).andExpect(jsonPath("$").value(250.0))
				.andExpect(jsonPath("$").exists());
	}

	@Test
	public void testCalculateTotalPrice_TwoDifferentBooksWithFivePercentDiscount() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(95.0);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011}]"))
				.andExpect(status().isOk()).andExpect(content().json("95.0"));
	}

	@Test
	public void testCalculateTotalPrice_ThreeDifferentBooksWithTenPercentDiscount() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(135.0);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Clean Architecture\",\"author\":\"Robert Martin\",\"year\":2017}]"))
				.andExpect(status().isOk()).andExpect(content().json("135.0"));
	}

	@Test
	public void testCalculateTotalPrice_FourDifferentBooksWithTwentyPercentDiscount() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(160.0);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Clean Architecture\",\"author\":\"Robert Martin\",\"year\":2017},{\"title\":\"Test Driven Development by Example\",\"author\":\"Kent Beck\",\"year\":2003}]"))
				.andExpect(status().isOk()).andExpect(content().json("160.0"));
	}

	@Test
	public void testCalculateTotalPrice_FourDifferentBooksWithTenPercentDiscount() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(180.0);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON).content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Clean Architecture\",\"author\":\"Robert Martin\",\"year\":2017}]"))
				.andExpect(status().isOk()).andExpect(content().json("180.0"));
	}

	@Test
	public void testCalculateTotalPrice_ShoppingBasketWithMultipleBooks() throws Exception {
		Mockito.when(bookService.calculateTotalPrice(Mockito.anyList())).thenReturn(320.0);

		mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalPrice").contentType(MediaType.APPLICATION_JSON)

				.content(
						"[{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":2008},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Clean Coder\",\"author\":\"Robert Martin\",\"year\":2011},{\"title\":\"Clean Architecture\",\"author\":\"Robert Martin\",\"year\":2017},{\"title\":\"Clean Architecture\",\"author\":\"Robert Martin\",\"year\":2017},{\"title\":\"Test Driven Development by Example\",\"author\":\"Kent Beck\",\"year\":2003},{\"title\":\"Working Effectively With Legacy Code\",\"author\":\"Michael C. Feathers\",\"year\":2004}]"))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value(320.0)).andExpect(jsonPath("$").exists());

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
