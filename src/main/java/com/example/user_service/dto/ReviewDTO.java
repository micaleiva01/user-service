package com.example.user_service.dto;

import com.example.user_service.model.Review;

public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private String username;
    private Long movieId;

    public ReviewDTO(Long id, int rating, String comment, String username, Long movieId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
        this.movieId = movieId;
    }

    public ReviewDTO(Review review) {
        if (review != null) {
            this.id = review.getId();
            this.rating = review.getRating();
            this.comment = review.getComment();
            this.username = (review.getUser() != null) ? review.getUser().getUsername() : null;
            this.movieId = review.getMovieId();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
}
