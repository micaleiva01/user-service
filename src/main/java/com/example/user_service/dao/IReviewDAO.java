package com.example.user_service.dao;

import com.example.user_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IReviewDAO extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
}
