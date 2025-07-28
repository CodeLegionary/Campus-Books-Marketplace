package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_rating", uniqueConstraints = @UniqueConstraint(columnNames = {"rater_id", "target_id"}))
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Chi vota
    @ManyToOne
    @JoinColumn(name = "rater_id", nullable = false)
    private User rater;

    // Chi riceve il voto
    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private User target;

    private int rating; // da 1 a 5

    // GETTER e SETTER
    public Long getId() { return id; }

    public User getRater() { return rater; }
    public void setRater(User rater) { this.rater = rater; }

    public User getTarget() { return target; }
    public void setTarget(User target) { this.target = target; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
