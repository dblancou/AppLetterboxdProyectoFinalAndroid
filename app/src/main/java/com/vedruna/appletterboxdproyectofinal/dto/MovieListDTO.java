package com.vedruna.appletterboxdproyectofinal.dto;

import java.util.ArrayList;
import java.util.List;

public class MovieListDTO {
    private Long listId;
    private String name;
    private String userName; // Nombre del usuario que creó la lista
    private Long userId; // ID del usuario que creó la lista
    private String description;
    private boolean following;
    private List<FilmDTO> films;

    // Constructor por defecto
    public MovieListDTO() {
        this.films = new ArrayList<>(); // Inicializar la lista de películas como vacía
    }

    // Constructor con parámetros (sin following)
    public MovieListDTO(Long listId, String name, String userName, Long userId, String description, List<FilmDTO> films) {
        this.listId = listId;
        this.name = name;
        this.userName = userName;
        this.userId = userId;
        this.description = description;
        this.following = false; // Valor por defecto
        this.films = films != null ? films : new ArrayList<>();
    }

    // Constructor con parámetros (con following)
    public MovieListDTO(Long listId, String name, String userName, Long userId, String description, boolean following, List<FilmDTO> films) {
        this.listId = listId;
        this.name = name;
        this.userName = userName;
        this.userId = userId;
        this.description = description;
        this.following = following;
        this.films = films != null ? films : new ArrayList<>();
    }

    // Getters y Setters
    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public List<FilmDTO> getFilms() {
        return films;
    }

    public void setFilms(List<FilmDTO> films) {
        this.films = films;
    }
}
