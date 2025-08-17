package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.model.BookDto;
import com.example.demo.model.BookRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserRepository;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/books") // Base URL for all endpoints in this controller
public class BookController {

    @Autowired
    private BookRepository bookRepository; // To interact with the database for Book entities

    @Autowired
    private UserRepository userRepository; // To get the authenticated User entity

    /**
     * Handles GET requests to /api/books
     * Retrieves all books from the database.
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new) // Uses the constructor to map Book to BookDto
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    /**
     * Handles POST requests to /api/books
     * Publishes a new book, associating it with the authenticated user.
     */
    @PostMapping
    public ResponseEntity<?> publishBook(@RequestBody Book bookDetails,
                                        @AuthenticationPrincipal User authenticatedUser) {

        // Ensure user is authenticated
        if (authenticatedUser == null) {
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "User not authenticated."),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Fetch the full User entity from the database
        // This is important for JPA to manage the relationship correctly.
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found in database."));

        // Associate the book with the authenticated user
        bookDetails.setOwner(user); // Assuming Book entity has a setOwner method

        // Save the book
        Book savedBook = bookRepository.save(bookDetails);

        return new ResponseEntity<>(new BookDto(savedBook), HttpStatus.CREATED);
    }

    // You can add more endpoints here, e.g.,
    // @GetMapping("/{id}") to get a book by ID
    // @PutMapping("/{id}") to update a book
    // @DeleteMapping("/{id}") to delete a book
}