package com.vedruna.appletterboxdproyectofinal.models;

import java.io.Serializable;
import java.util.List;

public class MovieList implements Serializable {
    private Long listId;
    private Long userId;
    private String name;
    private String description;
    private List<Film> films;

    public MovieList() {}

    public MovieList(Long listId, Long userId, String name, String description, List<Film> films) {
        this.listId = listId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.films = films;
    }

    // Getters and setters

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

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
