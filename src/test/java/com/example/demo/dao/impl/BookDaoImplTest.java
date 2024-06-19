package com.example.demo.dao.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import com.example.demo.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.domain.Book;

import javax.swing.text.html.Option;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testCreateBookCorrectSql(){
        Book book = TestDataUtil.createTestBook();

        underTest.create(book);
        verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
            eq("913-223233e24"), eq("Stolen Focus"), eq(1L)
        );
    }

    @Test
    public void testFindOneBookGeneratesCorrectSql(){
        underTest.findOne("913-223233e24");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn=? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("913-223233e24")

        );
    }

    @Test
    public void testFindManyBookGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testBookUpdateGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBook();
        underTest.update("913-223233e24", book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn=?, title=?, author_id=? WHERE isbn=?"),
                eq(book.getIsbn()), eq(book.getTitle()), eq(book.getAuthorId()), eq("913-223233e24")
        );
    }

    @Test
    public void testBookDeleteGeneratesCorrectSql(){
        String isbn="32y4389-12382y";
        underTest.delete(isbn);

        verify(jdbcTemplate).update(
                eq("DELETE FROM books WHERE isbn=?"),
                eq(isbn)
        );
    }
}
