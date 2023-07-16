package com.kata.developmentbookstore;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.model.BookInfo;


class DevlopmentBookStoreApplicationTests {
	@Autowired
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(BookController.class).build();

	@Test
    public void testGetAllBooksEndpoint() throws Exception {

		  BookInfo[] books = {
	                new BookInfo("Clean Code", "Robert C. Martin", 2008),
	        };

		  mockMvc.perform(MockMvcRequestBuilders.get("/getAllBooks"))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$").isArray())
          .andExpect(jsonPath("$").isNotEmpty())
          .andExpect(jsonPath("$.length()").value(books.length))
          .andExpect(jsonPath("$[0].title").value(books[0].getTitle()))
          .andExpect(jsonPath("$[0].author").value(books[0].getAuthor()))
          .andExpect(jsonPath("$[0].year").value(books[0].getYear()));
}

}
