package com.example.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Card implements Serializable {
    private static final long serialVersionUID= 1L; // best practice on the long run

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expiryDate;
    private String cardNumber;
    private String cardHolder;
    private String cvv;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Card() { }

    public Card(String cardNumber, String cardHolder, String expiryDate, String cvv, User user) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.user = user;
    }

    // —— Getters & Setters —— //

    public Long getId() {
        return id;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate= expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber= cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder= cardHolder;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv= cvv;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        // The check below prevents an infinite loop
        if (user != null && user.getCard() != this) {
            user.setCard(this);
        }
    }
}
