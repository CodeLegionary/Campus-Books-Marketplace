package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import com.example.demo.model.Role;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public User createUser(@RequestBody User user) {
    // Codifica la password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    user.setActive(true);

    user.setRole(Role.USER);

    // Salva l'utente nel database
    return myAppUserRepository.save(user);
    }
}
