package com.example.demo.controllers;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entities.BookEntity;
import com.example.demo.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testBookCreateReturns201() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testBookCreateSaved() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );

    }

    @Test
    public void testListAllBooksReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testListAllBooksReturnsListOfBooks() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookEntity.getTitle())
        );
    }

    @Test
    public void testFindOneBooksReturns200() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFindOneBooksReturns404() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testFindOneBooksReturnsBookBody() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookEntity.getTitle())
        );
    }

    @Test
    public void testFullUpdateBookReturns201() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFullUpdateBookReturnsUpdatedBody() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/{isbn}", bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testPartialUpdateBookReturns200() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("UPDATED TITLE");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testPartialUpdateBookReturnsUpdatedBody() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("UPDATED TITLE");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testDeleteBookReturns204() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
