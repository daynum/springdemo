package com.example.demo;

import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder()
                .id(1L)
                .name("Henry Adams")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
            .id(1L)
            .name("Henry Adams")
            .age(80)
            .build();
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Adam Smasher")
                .age(40)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Alan Turing")
                .age(20)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Yuval")
                .age(60)
                .build();
    }

    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
            .title("Stolen Focus")
            .authorEntity(authorEntity)
            .isbn("913-223233e24")
            .build();
    }

    public static BookDto createTestBookDto(final AuthorDto authorDto) {
        return BookDto.builder()
                .title("Stolen Focus")
                .authorDto(authorDto)
                .isbn("913-223233e24")
                .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .title("Outliers")
                .authorEntity(authorEntity)
                .isbn("913-223233e24")
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .title("Cosmos")
                .authorEntity(authorEntity)
                .isbn("913-223233e25")
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .title("Sapiens")
                .authorEntity(authorEntity)
                .isbn("913-223233e26")
                .build();
    }
}
