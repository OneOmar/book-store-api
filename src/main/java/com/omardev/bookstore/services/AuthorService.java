package com.omardev.bookstore.services;

import com.omardev.bookstore.entities.Author;
import com.omardev.bookstore.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling business logic related to Author entities.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void saveAuthors(List<Author> authors) {
        authorRepository.saveAll(authors);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    public Author partialUpdate(Long id, Author author) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    if (author.getName() != null) {
                        existingAuthor.setName(author.getName());
                    }
                    if (author.getAge() != null) {
                        existingAuthor.setAge(author.getAge());
                    }
                    return authorRepository.save(existingAuthor);
                })
                .orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

}
