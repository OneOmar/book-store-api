package com.omardev.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omardev.bookstore.models.Author;
import com.omardev.bookstore.models.Book;
import com.omardev.bookstore.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testCreateAuthor() throws Exception {
        Author author = new Author(null, "Jane Austen", 41, new ArrayList<>()); // Initialize books

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Jane Austen")))
                .andExpect(jsonPath("$.age", is(41)));
    }

    @Test
    void testListAuthors() throws Exception {
        List<Author> authors = List.of(
                new Author(null, "Author One", 35, new ArrayList<>()),
                new Author(null, "Author Two", 40, new ArrayList<>())
        );
        authorRepository.saveAll(authors);

        mockMvc.perform(get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Author One")))
                .andExpect(jsonPath("$[1].name", is("Author Two")));
    }

    @Test
    void testGetAuthorByIdFound() throws Exception {
        Author author = new Author(null, "John Doe", 45, new ArrayList<>());
        Author savedAuthor = authorRepository.save(author);

        mockMvc.perform(get("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedAuthor.getId().intValue())))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.age", is(45)));
    }

    @Test
    void testGetAuthorByIdNotFound() throws Exception {
        mockMvc.perform(get("/authors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFullUpdateAuthor() throws Exception {
        // Arrange: Create and save an author with initial values
        Author author = new Author(null, "Original Name", 50, new ArrayList<>());
        Author savedAuthor = authorRepository.save(author);

        // Arrange: Create an updated author with new values
        Author updatedAuthor = new Author(null, "Updated Name", 55, new ArrayList<>());

        // Act & Assert: Perform the update via the PUT request
        mockMvc.perform(put("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name")))
                .andExpect(jsonPath("$.age", is(55)));
    }

    @Test
    void testFullUpdateAuthorWithBooks() throws Exception {
        // Arrange: Create and save an author with initial values and an associated book
        Book initialBook = new Book("978-3-16-148410-0", "Original Book Title", 2020);
        Author author = new Author(null, "Original Name", 50, new ArrayList<>(List.of(initialBook)));
        Author savedAuthor = authorRepository.save(author);

        // Arrange: Create an updated author with new values and a new book
        Book updatedBook = new Book("978-3-16-148410-1", "Updated Book Title", 2021);
        Author updatedAuthor = new Author(savedAuthor.getId(), "Updated Name", 55, new ArrayList<>(List.of(updatedBook)));

        // Act & Assert: Perform the update via the PUT request
        mockMvc.perform(put("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name"))) // Validate name update
                .andExpect(jsonPath("$.age", is(55))) // Validate age update
                .andExpect(jsonPath("$.books[0].title", is("Updated Book Title"))); // Validate book title update
    }


    @Test
    void testPartialUpdateAuthor() throws Exception {
        Author author = new Author(null, "Initial Name", 30, new ArrayList<>()); // Initialize books
        Author savedAuthor = authorRepository.save(author);

        Author partialUpdate = new Author(null, "Partially Updated", null, null); // Initialize books to null

        mockMvc.perform(patch("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Partially Updated")))
                .andExpect(jsonPath("$.age", is(30))); // Age should remain unchanged
    }

    @Test
    void testDeleteAuthor() throws Exception {
        Author author = new Author(null, "Author to Delete", 29, new ArrayList<>());
        Author savedAuthor = authorRepository.save(author);

        mockMvc.perform(delete("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/authors/{id}", savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
