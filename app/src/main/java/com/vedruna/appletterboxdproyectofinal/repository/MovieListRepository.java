package com.vedruna.appletterboxdproyectofinal.repository;

import com.vedruna.appletterboxdproyectofinal.models.MovieList;
import java.util.List;

public interface MovieListRepository {
    void createMovieList(MovieList movieList);
    MovieList getMovieListById(Long id);
    List<MovieList> getAllMovieLists();
    void updateMovieList(MovieList movieList);
    void deleteMovieList(Long id);
    List<MovieList> getMovieListsByUser(Long userId);
}
