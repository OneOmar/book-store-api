package com.omardev.bookstore.repositories;

import com.omardev.bookstore.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Author entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Find an Author by their name.
     *
     * @param name the name of the author
     * @return an Optional containing the found Author, or empty if not found
     */
    Optional<Author> findByName(String name);
}
