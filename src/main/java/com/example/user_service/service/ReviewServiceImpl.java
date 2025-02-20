package com.example.user_service.service;

import com.example.user_service.dao.IReviewDAO;
import com.example.user_service.dto.ReviewDTO;
import com.example.user_service.model.Review;
import com.example.user_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private IReviewDAO reviewDAO;

    @Autowired
    private IUserService userService;

    private final String MOVIE_SERVICE_URL = "http://localhost:8081/movies";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ReviewDTO addReview(Long movieId, int rating, String comment, String username) {
        ResponseEntity<Void> movieResponse = restTemplate.getForEntity(MOVIE_SERVICE_URL + "/" + movieId, Void.class);
        if (!movieResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Pelicula " + movieId + " no existe.");
        }

        Optional<User> userOptional = userService.getUserEntityByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        User user = userOptional.get();

        Review review = new Review(rating, comment, user, movieId);
        Review savedReview = reviewDAO.save(review);

        return new ReviewDTO(
                savedReview.getId(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getUser().getUsername(),
                savedReview.getMovieId()
        );
    }

    @Override
    public List<ReviewDTO> getReviewsByMovieId(Long movieId) {
        return reviewDAO.findByMovieId(movieId).stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getUser().getUsername(),
                        review.getMovieId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewDAO.findById(id).map(review -> new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getUsername(),
                review.getMovieId()
        ));
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewDAO.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada");
        }
        reviewDAO.deleteById(id);
    }

    @Override
    public Review saveReview(Review review) {
        return reviewDAO.save(review);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, int rating, String comment) {
        Optional<Review> optionalReview = reviewDAO.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new IllegalArgumentException("Reseña no encontrada");
        }

        Review review = optionalReview.get();
        review.setRating(rating);
        review.setComment(comment);

        Review updatedReview = reviewDAO.save(review);
        return new ReviewDTO(updatedReview);
    }
}
