package com.example.demo.repositories;

import com.example.demo.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    // You have to smartly type the function name here
    // "age" is an instance variable, spring is smart to understand "LessThan" part. Amazing.
    Iterable<Author> ageLessThan(int age);

    // If you have some weird name, and spring is not able to pick up on its own, what needs to be done.
    // Then we have this way of specifying the "HQL", instead of writing "SQL"
    @Query("SELECT a FROM Author a where a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}
