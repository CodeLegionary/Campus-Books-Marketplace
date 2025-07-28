// com.example.demo.dto.BookDto.java (Lean Version)
package com.example.demo.model;

import com.example.demo.model.Book;

public class BookDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private boolean pariANuovo;
    private Long ownerId; // <-- ONLY the owner's ID (Long), NOT a UserDto object

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.pariANuovo = book.isPariANuovo();
        if (book.getOwner() != null) {
            this.ownerId = book.getOwner().getId(); // Get just the ID
        } else {
            this.ownerId = null;
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public boolean isPariANuovo() { return pariANuovo; }
    public Long getOwnerId() { return ownerId; }
}