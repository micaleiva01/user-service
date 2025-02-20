package com.example.user_service.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Long movieId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Review() {}

    public Review(int rating, String comment, User user, Long movieId) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.movieId = movieId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
