package com.omardev.bookstore.controllers;

import com.omardev.bookstore.entities.Author;
import com.omardev.bookstore.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Author entities.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @GetMapping
    public ResponseEntity<List<Author>> listAuthors() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.findAuthorById(id);
        return author.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> fullUpdateAuthor(
            @PathVariable Long id,
            @RequestBody Author author) {

        if (!authorService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        author.setId(id);
        Author updatedAuthor = authorService.save(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> partialUpdateAuthor(
            @PathVariable Long id,
            @RequestBody Author author) {

        if (!authorService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Author updatedAuthor = authorService.partialUpdate(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!authorService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
