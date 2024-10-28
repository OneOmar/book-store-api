package com.omardev.bookstore.entities;

import jakarta.persistence.*;
import lombok.*;

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
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private List<Book> books;
}

