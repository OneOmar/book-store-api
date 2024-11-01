package com.omardev.bookstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Author entity.
 * Maps to the 'authors' table in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer age;

    /**
     * The list of books associated with this author.
     * CascadeType.ALL: Any operations on the Author will propagate to the related Books.
     * orphanRemoval = true: Ensures that any Book entity removed from this list
     *                       will be automatically deleted from the database.
     * FetchType.LAZY: Improves performance by loading the books only when accessed.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private List<Book> books = new ArrayList<>(); // Initialize to avoid null
}
