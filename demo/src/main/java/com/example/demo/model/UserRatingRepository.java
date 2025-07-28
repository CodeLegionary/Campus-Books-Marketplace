package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    // 🔍 Find existing rating between two users (one rater, one target)
    Optional<UserRating> findByRaterAndTarget(User rater, User target);

    // 📊 Average rating received by a user (target)
    @Query("SELECT AVG(ur.rating) FROM UserRating ur WHERE ur.target = :target")
    Double findAverageRatingForUser(@Param("target") User target);

    // 📦 All ratings received by a user
    List<UserRating> findAllByTarget(User target);
}
