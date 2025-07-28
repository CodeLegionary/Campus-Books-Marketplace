package com.example.demo.model;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Optional should just return one
    Optional<User> findByUsername(String username);
    List<User> findByReportedTrue();
}
