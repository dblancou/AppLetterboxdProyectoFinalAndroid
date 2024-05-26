package com.vedruna.appletterboxdproyectofinal.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable {
    private Long reviewId;
    private Long userId;
    private Long filmId;
    private String content;
    private Double rating;
    private LocalDateTime reviewDate;

    public Review() {}

    public Review(Long reviewId, Long userId, Long filmId, String content, Double rating, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.filmId = filmId;
        this.content = content;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    // Getters and setters

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}
