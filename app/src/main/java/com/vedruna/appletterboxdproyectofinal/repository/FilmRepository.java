package com.vedruna.appletterboxdproyectofinal.repository;

import com.vedruna.appletterboxdproyectofinal.models.Film;
import java.util.List;

public interface FilmRepository {
    void createFilm(Film film);
    Film getFilmById(Long id);
    List<Film> getAllFilms();
    void updateFilm(Film film);
    void deleteFilm(Long id);
}
