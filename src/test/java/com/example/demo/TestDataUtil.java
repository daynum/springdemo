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
                .id(2L)
                .name("Adam Smasher")
                .age(40)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(3L)
                .name("Alan Turing")
                .age(20)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(1L)
                .name("Yuval")
                .age(60)
                .build();
    }

    public static Book createTestBook() {
        return Book.builder()
            .title("Stolen Focus")
            .authorId(1L)
            .isbn("913-223233e24")
            .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .title("Outliers")
                .authorId(1L)
                .isbn("913-223233e24")
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .title("Cosmos")
                .authorId(2L)
                .isbn("913-223233e25")
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .title("Sapiens")
                .authorId(3L)
                .isbn("913-223233e26")
                .build();
    }
}
