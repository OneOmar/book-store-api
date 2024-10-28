package com.omardev.bookstore.services;

import com.omardev.bookstore.entities.Book;
import com.omardev.bookstore.repositories.BookRepository;
import com.omardev.bookstore.utils.ISBNGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling business logic related to Book entities.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveOrUpdate(String isbn, Book book) {
        if (isbn == null || isbn.isEmpty()) {
            isbn = ISBNGenerator.generateISBN();  // Automatically generate ISBN if not provided
        }
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    public Optional<Book> findBookById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> findAllBooksPageable(Pageable pageable) { return bookRepository.findAll(pageable); }

    public boolean existsById(String isbn) {
        return bookRepository.existsById(isbn);
    }

    public Book partialUpdate(String isbn, Book book) {
        return bookRepository.findById(isbn)
                .map(existingBook -> {
                    Optional.ofNullable(book.getTitle()).ifPresent(existingBook::setTitle);
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    public void delete(String isbn) {
        if (!existsById(isbn)) {
            throw new RuntimeException("Book does not exist");
        }
        bookRepository.deleteById(isbn);
    }
}
