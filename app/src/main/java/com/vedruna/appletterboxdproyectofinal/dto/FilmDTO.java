package com.vedruna.appletterboxdproyectofinal.dto;

import java.io.Serializable;

public class FilmDTO implements Serializable {
    private Long filmId;
    private String title;
    private String director;
    private String year;
    private String genreName;
    private String description;
    private double imdbRating;
    private String posterUrl;

    // Constructor vacío
    public FilmDTO() {}

    // Constructor que acepta solo filmId
    public FilmDTO(Long filmId) {
        this.filmId = filmId;
    }

    // Getters and Setters
    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
