package com.example.demo.controller;


import com.example.demo.model.Card;
import com.example.demo.model.User;
import com.example.demo.model.CardRepository;
import com.example.demo.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository; // To retrieve a managed User entity

    /**
     * Creates or updates a Card for the authenticated user.
     * The card details are provided in the request body.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveCard(@RequestBody Card cardDetails,
                                      @AuthenticationPrincipal User authenticatedUser) {

        // 1. Validate the authenticated user is present
        if (authenticatedUser == null) {
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "User not authenticated"),
                    HttpStatus.UNAUTHORIZED);
        }

        // 2. Fetch the full, managed User entity from the database
        //    This is crucial for proper JPA relationship management.
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 3. Check if the user already has a card
        Card existingCard = user.getCard();

        if (existingCard != null) {
            // Update the existing card with new details from the request
            existingCard.setCardNumber(cardDetails.getCardNumber());
            existingCard.setCardHolder(cardDetails.getCardHolder());
            existingCard.setExpiryDate(cardDetails.getExpiryDate());
            existingCard.setCvv(cardDetails.getCvv());

            cardRepository.save(existingCard);
            return new ResponseEntity<>(
                    Collections.singletonMap("message", "Card details updated successfully."),
                    HttpStatus.OK);
        } else {
            // Create a new card and associate it with the user
            cardDetails.setUser(user);
            cardRepository.save(cardDetails);
            return new ResponseEntity<>(
                    Collections.singletonMap("message", "Card details saved successfully."),
                    HttpStatus.CREATED);
        }
    }

    /**
     * Retrieves the Card details for the authenticated user.
     */
    @GetMapping
    public ResponseEntity<?> getCard(@AuthenticationPrincipal User authenticatedUser) {

        // 1. Validate the authenticated user is present
        if (authenticatedUser == null) {
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "User not authenticated"),
                    HttpStatus.UNAUTHORIZED);
        }

        // 2. Retrieve the user and their associated card directly
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Card card = user.getCard();

        if (card != null) {
            // Return the card details in the response body.
            // Spring will automatically convert this to JSON.
            return new ResponseEntity<>(card, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    Collections.singletonMap("message", "No card found for the user."),
                    HttpStatus.NOT_FOUND);
        }
    }
}