package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Book implements Serializable {
    private static final long serialVersionUID = 1L; //best practice on the long run

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private Double price;

    private boolean pariANuovo; // true = nuovo, false = usato o danneggiato

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;

    // GETTER & SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public boolean isPariANuovo() { return pariANuovo; }
    public void setPariANuovo(boolean pariANuovo) { this.pariANuovo = pariANuovo; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
