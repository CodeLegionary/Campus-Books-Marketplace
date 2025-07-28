package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users") // Ensure this matches your database table name
public class User implements UserDetails, Serializable { // Implement UserDetails interface

    private static final long serialVersionUID = 1L; //Good practice

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean reported = false;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>(); //initialized to avoid NullPointerException

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Card card;

    // Default constructor (required by JPA)
    public User() {
    }

    // Constructor for creating a user (optional, but useful)
    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true; // Default to active
        this.reported =  false;
    }

    // --- Getters and Setters ---
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Note: Spring Security's UserDetails interface has getPassword() and getUsername()
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        if (card != null && card.getUser() != this) {
            card.setUser(this);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new java.util.ArrayList<>();
        }
        this.books.add(book);
        book.setOwner(this);
    }

    public void removeBook(Book book) {
        if (this.books != null) {
            this.books.remove(book);
            book.setOwner(null);
        }
    }

    // --- UserDetails Interface Implementations ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a collection of authorities (roles) for the user
        // Assuming your Role enum values match Spring Security's expected role format (e.g., "ADMIN", "USER")
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        // For simplicity, assuming accounts do not expire
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // For simplicity, assuming accounts are not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // For simplicity, assuming credentials do not expire
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Use the 'active' field to determine if the user is enabled
        return this.active;
    }
}
