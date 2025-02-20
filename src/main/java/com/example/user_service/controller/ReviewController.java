package com.example.user_service.controller;

import com.example.user_service.dto.ReviewDTO;
import com.example.user_service.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<String> getReviews() {
        System.out.println("ok le llega el llamado");
        return ResponseEntity.ok("Anda!!!");
    }

    @PostMapping("/add/{movieId}")
    public ResponseEntity<?> createReview(
            @PathVariable Long movieId,
            @RequestBody Map<String, Object> reviewData) {
        try {
            int rating = (int) reviewData.get("rating");
            String comment = (String) reviewData.get("comment");
            String username = (String) reviewData.get("username");

            ReviewDTO createdReview = reviewService.addReview(movieId, rating, comment, username);
            return ResponseEntity.ok(createdReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovieId(movieId));
    }

    @PutMapping({"/{reviewId}", "/{reviewId}/"})
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Map<String, Object> reviewData) {
        try {
            int rating = (int) reviewData.get("rating");
            String comment = (String) reviewData.get("comment");
            ReviewDTO updatedReview = reviewService.updateReview(reviewId, rating, comment);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok("Review deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/test")
    public ResponseEntity<String> testPut() { return ResponseEntity.ok("PUT endpoint is working"); }
}

