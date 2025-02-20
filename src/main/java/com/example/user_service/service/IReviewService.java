package com.example.user_service.service;

import com.example.user_service.dto.ReviewDTO;
import com.example.user_service.model.Review;
import java.util.List;
import java.util.Optional;

public interface IReviewService {
    Review saveReview(Review review);
    Optional<ReviewDTO> getReviewById(Long id);
    void deleteReview(Long id);
    ReviewDTO addReview(Long movieId, int rating, String comment, String username);
    List<ReviewDTO> getReviewsByMovieId(Long movieId);
    ReviewDTO updateReview(Long reviewId, int rating, String comment);
}
