package com.omardev.bookstore.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a Book entity.
 * Maps to the 'books' table in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String isbn;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "published_year", nullable = false)
    private Integer publishedYear;
}
