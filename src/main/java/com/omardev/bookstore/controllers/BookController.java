package com.omardev.bookstore.controllers;

import com.omardev.bookstore.entities.Book;
import com.omardev.bookstore.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveOrUpdate(null, book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/pageable")
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookService.findAllBooksPageable(pageable);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookById(@PathVariable String isbn) {
        Optional<Book> book = bookService.findBookById(isbn);

        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(
            @PathVariable String isbn,
            @RequestBody Book book) {

        if (!bookService.existsById(isbn)) {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookService.saveOrUpdate(isbn, book);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<Book> partialUpdateBook(
            @PathVariable String isbn,
            @RequestBody Book book) {
        if (!bookService.existsById(isbn)) {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookService.partialUpdate(isbn, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        bookService.delete(isbn);
        return ResponseEntity.noContent().build();
    }
}
