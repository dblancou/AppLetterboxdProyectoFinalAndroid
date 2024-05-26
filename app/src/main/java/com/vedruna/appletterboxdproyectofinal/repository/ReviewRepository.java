package com.vedruna.appletterboxdproyectofinal.repository;

import java.util.List;
import com.vedruna.appletterboxdproyectofinal.models.Review;


public interface ReviewRepository {
    void createReview(Review review);
    Review getReviewById(Long id);
    List<Review> getAllReviews();
    void updateReview(Review review);
    void deleteReview(Long id);
    List<Review> getReviewsByUserId(Long userId);
    List<Review> getReviewsByFilmId(Long filmId);
}
