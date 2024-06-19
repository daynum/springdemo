package com.example.demo;

import com.example.demo.domain.Author;
import com.example.demo.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static Author createTestAuthor() {
        return Author.builder()
            .id(1L)
            .name("Henry Adams")
            .age(80)
            .build();
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Adam Smasher")
                .age(40)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Alan Turing")
                .age(20)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Yuval")
                .age(60)
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
            .title("Stolen Focus")
            .author(author)
            .isbn("913-223233e24")
            .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .title("Outliers")
                .author(author)
                .isbn("913-223233e24")
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .title("Cosmos")
                .author(author)
                .isbn("913-223233e25")
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .title("Sapiens")
                .author(author)
                .isbn("913-223233e26")
                .build();
    }
}
