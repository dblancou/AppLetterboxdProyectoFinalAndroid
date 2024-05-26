package com.vedruna.appletterboxdproyectofinal.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class MovieListDTO implements Serializable {
    private Long listId;
    private Long userId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<FilmDTO> films;

    // Getters and Setters
    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<FilmDTO> getFilms() {
        return films;
    }

    public void setFilms(List<FilmDTO> films) {
        this.films = films;
    }
}
