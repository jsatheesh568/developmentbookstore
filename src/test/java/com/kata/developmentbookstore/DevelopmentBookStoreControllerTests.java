package com.kata.developmentbookstore;

import com.kata.developmentbookstore.controller.BookController;
import com.kata.developmentbookstore.exception.EmptyCartException;
import com.kata.developmentbookstore.service.BookService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class DevelopmentBookStoreControllerTests {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        bookService = Mockito.mock(BookService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
    }
    @Test
    public void testEmptyCart_ShouldReturnNoPrice() throws Exception {
        Mockito.when(bookService.calculateTotalPrice(Mockito.anyList()))
                .thenThrow(new EmptyCartException("The cart is empty."));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/development-bookstore/v1/calculate-price")
                .contentType(MediaType.APPLICATION_JSON).content("[]")).andExpect(status().isBadRequest()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"message\":\"The cart is empty\"}", responseBody);
    }
}
