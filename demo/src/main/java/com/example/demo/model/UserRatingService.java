package com.example.demo.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRatingService {

    private final UserRatingRepository userRatingRepository;

    public UserRatingService(UserRatingRepository userRatingRepository) {
        this.userRatingRepository = userRatingRepository;
    }

    @Transactional
    public void addOrUpdateRating(User rater, User target, int ratingValue) {
        UserRating rating = userRatingRepository.findByRaterAndTarget(rater, target)
                .orElse(new UserRating());

        rating.setRater(rater);
        rating.setTarget(target);
        rating.setRating(ratingValue);

        userRatingRepository.save(rating); // Single save, smart overwrite
    }
}
