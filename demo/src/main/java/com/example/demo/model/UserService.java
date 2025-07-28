package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired // Explicitly injecting the repository
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Now, we directly return our 'User' entity, because it implements UserDetails
        return repository.findByUsername(username)
                .filter(User::isActive) // Ensure the user is active
                .orElseThrow(() -> new UsernameNotFoundException("User not found or inactive with username: " + username));
    }
}
