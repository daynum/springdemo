package com.example.demo.dao.impl;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testManyAuthorsCreateAndRecall(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);

        List<Author> result = underTest.find();

        assertThat(result).hasSize(3).containsExactly(authorC, authorA, authorB);
    }

    @Test
    public void testAuthorUpdate(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        author.setName("UPDATED NAME");
        underTest.update(author.getId(), author);

        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testAuthorDelete(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);

        underTest.delete(author.getId());

        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isEmpty();
    }
}
