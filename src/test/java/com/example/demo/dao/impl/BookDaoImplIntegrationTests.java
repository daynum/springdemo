package com.example.demo.dao.impl;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.Author;
import com.example.demo.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;
    private AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCreateAndRecall(){
        Author author = TestDataUtil.createTestAuthor();
        Book book = TestDataUtil.createTestBook();
        authorDao.create(author);
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testManyBooksCreateAndRecall(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);
        Book bookA = TestDataUtil.createTestBookA();
        Book bookB = TestDataUtil.createTestBookB();
        Book bookC = TestDataUtil.createTestBookC();
        bookA.setAuthorId(author.getId());
        bookB.setAuthorId(author.getId());
        bookC.setAuthorId(author.getId());

        underTest.create(bookA);underTest.create(bookB);underTest.create(bookC);
        List<Book> result = underTest.find();
        assertThat(result).hasSize(3).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testBookUpdate(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);

        book.setTitle("UPDATED TITLE");

        underTest.update(book.getIsbn(), book);

        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testBookDelete(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isEmpty();
    }

}
