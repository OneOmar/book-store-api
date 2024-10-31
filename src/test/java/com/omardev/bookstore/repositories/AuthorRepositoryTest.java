package com.omardev.bookstore.repositories;

import com.omardev.bookstore.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = Author.builder()
                .name("J.K. Rowling")
                .age(55)
                .build();
        authorRepository.save(author);
    }

    @Test
    public void testFindByName() {
        Optional<Author> foundAuthor = authorRepository.findByName("J.K. Rowling");
        assertTrue(foundAuthor.isPresent());
        assertEquals("J.K. Rowling", foundAuthor.get().getName());
    }

    @Test
    public void testSaveAuthor() {
        Author newAuthor = Author.builder()
                .name("George Orwell")
                .age(46)
                .build();
        Author savedAuthor = authorRepository.save(newAuthor);
        assertEquals("George Orwell", savedAuthor.getName());
    }

    @Test
    public void testUpdateAuthor() {
        author.setAge(56);
        Author updatedAuthor = authorRepository.save(author);
        assertEquals(56, updatedAuthor.getAge());
    }

    @Test
    public void testDeleteAuthor() {
        authorRepository.delete(author);
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());
        assertTrue(foundAuthor.isEmpty());
    }

    @Test
    public void testFindAllAuthors() {
        Author anotherAuthor = Author.builder()
                .name("George Orwell")
                .age(46)
                .build();
        authorRepository.save(anotherAuthor);

        List<Author> authors = authorRepository.findAll();
        assertEquals(2, authors.size());
    }
}

