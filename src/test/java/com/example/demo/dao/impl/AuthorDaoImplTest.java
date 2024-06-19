package com.example.demo.dao.impl;

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

import com.example.demo.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);

        verify(jdbcTemplate).update(
            eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
            eq(1L), eq("Henry Adams"), eq(80)
        );
    }

    @Test
    public void testFindOneAuthorGeneratesCorrectSql(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }
    @Test
    public void testFindManyAuthorGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testAuthorUpdateGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthor();

        underTest.update(3L, author);

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id=?, name=?, age=? WHERE id=?"),
                eq(1L), eq("Henry Adams"), eq(80), eq(3L)
        );
    }

    @Test
    public void testAuthorDeleteGeneratesCorrectSql(){
        Long id = 1L;
        underTest.delete(id);

        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id=?"),
                eq(id)
        );
    }

}
