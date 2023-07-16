package com.kata.developmentbookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.service.BookService;

class DevlopmentBookStoreApplicationTests {
	
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
}


