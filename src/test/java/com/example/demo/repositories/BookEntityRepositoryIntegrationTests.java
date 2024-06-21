package com.example.demo.repositories;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testBookCreateAndRecall(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);

        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testManyBooksCreateAndRecall(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);

        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testBookUpdate(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);

        underTest.save(bookEntity);

        bookEntity.setTitle("UPDATED TITLE");

        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testBookDelete(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();
    }

}
