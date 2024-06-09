package com.vedruna.appletterboxdproyectofinal.dto;

import java.io.Serializable;

public class ReviewDTO implements Serializable {
    private Long reviewId;
    private Long userId;
    private String userName; // Añadido
    private Long filmId;
    private String title; // Añadido
    private String posterUrl; // Añadido
    private String content;
    private Double rating;
    private boolean isFollowing; // Añadir este campo

    public ReviewDTO() {

    }

    public ReviewDTO(Long reviewId, Long userId, String userName, Long filmId, String title, String posterUrl, String content, Double rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.filmId = filmId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.content = content;
        this.rating = rating;
    }

    // Getters and Setters
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }
}
