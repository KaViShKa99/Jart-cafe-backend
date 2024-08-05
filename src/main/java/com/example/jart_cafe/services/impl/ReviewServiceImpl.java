package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.model.Review;
import com.example.jart_cafe.repositories.ReviewRepository;
import com.example.jart_cafe.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewByProductId(Long id) {
        System.out.println(reviewRepository.findByArtworkId(id));
        return reviewRepository.findByArtworkId(id);
    }

    @Override
    public Optional<Review> getReviewById(Long id){
        return reviewRepository.findById(id);
    }

    @Override
    public void updateReview(Long id, Review updatedReview) {
         reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(updatedReview.getRating());
                    review.setReviewText(updatedReview.getReviewText());
                    review.setUsername(updatedReview.getUsername());
                    review.setUserEmail(updatedReview.getUserEmail());
                    review.setReviewedDate(updatedReview.getReviewedDate());  // Updating the date to now
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
