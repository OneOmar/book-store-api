package com.omardev.bookstore.repositories;

import com.omardev.bookstore.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = Book.builder()
                .isbn("123-4567890123")
                .title("1984")
                .publishedYear(1949)
                .build();
        bookRepository.save(book);
    }

    @Test
    public void testFindById() {
        Optional<Book> foundBook = bookRepository.findById("123-4567890123");
        assertTrue(foundBook.isPresent());
        assertEquals("1984", foundBook.get().getTitle());
    }

    @Test
    public void testSaveBook() {
        Book newBook = Book.builder()
                .isbn("456-7890123456")
                .title("Animal Farm")
                .publishedYear(1945)
                .build();
        Book savedBook = bookRepository.save(newBook);
        assertEquals("Animal Farm", savedBook.getTitle());
    }

    @Test
    public void testUpdateBook() {
        book.setPublishedYear(1950);
        Book updatedBook = bookRepository.save(book);
        assertEquals(1950, updatedBook.getPublishedYear());
    }

    @Test
    public void testDeleteBook() {
        bookRepository.delete(book);
        Optional<Book> foundBook = bookRepository.findById("123-4567890123");
        assertTrue(foundBook.isEmpty());
    }

    @Test
    public void testFindAllBooks() {
        Book anotherBook = Book.builder()
                .isbn("789-0123456789")
                .title("Brave New World")
                .publishedYear(1932)
                .build();
        bookRepository.save(anotherBook);

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }
}
