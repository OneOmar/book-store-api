package com.omardev.bookstore.services;

import com.omardev.bookstore.models.Author;
import com.omardev.bookstore.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1L)
                .name("John Doe")
                .age(45)
                .build();
    }

    @Test
    void testSave() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.save(author);

        assertNotNull(savedAuthor);
        assertEquals("John Doe", savedAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testSaveAuthors() {
        List<Author> authors = Arrays.asList(author, Author.builder().id(2L).name("Jane Doe").age(40).build());

        authorService.saveAuthors(authors);

        verify(authorRepository, times(1)).saveAll(authors);
    }

    @Test
    void testFindAll() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> foundAuthors = authorService.findAll();

        assertEquals(1, foundAuthors.size());
        assertEquals("John Doe", foundAuthors.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.findAuthorById(1L);

        assertTrue(foundAuthor.isPresent());
        assertEquals("John Doe", foundAuthor.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testExistsById() {
        when(authorRepository.existsById(1L)).thenReturn(true);

        boolean exists = authorService.existsById(1L);

        assertTrue(exists);
        verify(authorRepository, times(1)).existsById(1L);
    }

    @Test
    void testPartialUpdate() {
        Author updatedInfo = Author.builder().name("Johnny Doe").build();
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author updatedAuthor = authorService.partialUpdate(1L, updatedInfo);

        assertEquals("Johnny Doe", updatedAuthor.getName());
        assertEquals(45, updatedAuthor.getAge()); // Age remains the same
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testPartialUpdateAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authorService.partialUpdate(1L, author);
        });

        // assertTrue(exception.getMessage().contains("Author does not exist"));
        assertEquals("Author does not exist", exception.getMessage());

        // verify(authorRepository, never()).save(any());
        verify(authorRepository, times(0)).save(any());
    }

    @Test
    void testDelete() {
        authorService.delete(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}
