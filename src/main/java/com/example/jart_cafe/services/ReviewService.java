package com.example.jart_cafe.services;

import com.example.jart_cafe.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review saveReview(Review review);

    List<Review> getAllReviews();

    List<Review> getReviewByProductId(Long id);

    Optional<Review> getReviewById(Long id);

    void updateReview(Long id, Review updatedReview);

    void deleteReview(Long id);
}
