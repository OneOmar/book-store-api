package com.omardev.bookstore.repositories;

import com.omardev.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Book entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> { }
