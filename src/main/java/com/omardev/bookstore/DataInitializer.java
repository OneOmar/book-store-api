//package com.omardev.bookstore;
//
//import com.omardev.bookstore.entities.Author;
//import com.omardev.bookstore.entities.Book;
//import com.omardev.bookstore.services.AuthorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private AuthorService authorService;
//
//    @Override
//    public void run(String... args) {
//        Author author1 = Author.builder()
//                .name("J.K. Rowling")
//                .age(58)
//                .books(Arrays.asList(
//                        Book.builder().isbn("978-0747532743").title("Harry Potter and the Philosopher's Stone").publishedYear(1997).build(),
//                        Book.builder().isbn("978-0747538486").title("Harry Potter and the Chamber of Secrets").publishedYear(1998).build()
//                ))
//                .build();
//
//        Author author2 = Author.builder()
//                .name("George R.R. Martin")
//                .age(75)
//                .books(Arrays.asList(
//                        Book.builder().isbn("978-0553103540").title("A Game of Thrones").publishedYear(1996).build(),
//                        Book.builder().isbn("978-0553108576").title("A Clash of Kings").publishedYear(1998).build()
//                ))
//                .build();
//
//
//        Author author3 = Author.builder()
//                .name("J.R.R. Tolkien")
//                .age(127)
//                .books(Arrays.asList(
//                        Book.builder().isbn("978-0261103207").title("The Hobbit").publishedYear(1937).build(),
//                        Book.builder().isbn("978-0261102385").title("The Lord of the Rings").publishedYear(1954).build()
//                ))
//                .build();
//
//        Author author4 = Author.builder()
//                .name("Isaac Asimov")
//                .age(92)
//                .books(Arrays.asList(
//                        Book.builder().isbn("978-0553293357").title("Foundation").publishedYear(1951).build(),
//                        Book.builder().isbn("978-0553293418").title("I, Robot").publishedYear(1950).build()
//                ))
//                .build();
//
//        Author author5 = Author.builder()
//                .name("Agatha Christie")
//                .age(85)
//                .books(Arrays.asList(
//                        Book.builder().isbn("978-0008129620").title("Murder on the Orient Express").publishedYear(1934).build(),
//                        Book.builder().isbn("978-0008140304").title("The Murder of Roger Ackroyd").publishedYear(1926).build()
//                ))
//                .build();
//
//        authorService.saveAuthors(Arrays.asList(author1, author2, author3, author4, author5));
//    }
//}