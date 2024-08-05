package com.example.jart_cafe.api;

import com.example.jart_cafe.model.Review;
import com.example.jart_cafe.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/save")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }
    @GetMapping("/getAll")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("get/{id}")
    public List<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewByProductId(id);
    }

    @PutMapping("update/{id}")
    public void updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
         reviewService.updateReview(id, updatedReview);
    }

    @DeleteMapping("delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }


}
