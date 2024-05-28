package com.vedruna.appletterboxdproyectofinal.network;

import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;
import com.vedruna.appletterboxdproyectofinal.dto.MovieListDTO;
import com.vedruna.appletterboxdproyectofinal.dto.ReviewDTO;
import com.vedruna.appletterboxdproyectofinal.dto.UserDTO;
import com.vedruna.appletterboxdproyectofinal.models.AuthResponse;
import com.vedruna.appletterboxdproyectofinal.models.LoginRequest;
import com.vedruna.appletterboxdproyectofinal.models.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Authentication endpoints
    @POST("/auth/register")
    Call<AuthResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/auth/login")
    Call<AuthResponse> loginUser(@Body LoginRequest loginRequest);

    // Film endpoints
    @POST("/api/films")
    Call<FilmDTO> createFilm(@Body FilmDTO filmDTO);

    @GET("/api/films/{id}")
    Call<FilmDTO> getFilmById(@Path("id") Long id);

    //MODIFICACIONES 28 DE MAYO
    @GET("/api/films")
    Call<List<FilmDTO>> getAllFilms();

    @GET("/api/films")
    Call<List<FilmDTO>> getFilms(@Query("sort") String sort, @Query("limit") int limit);


    @GET("/api/films/latest")
    Call<List<FilmDTO>> getLatestFilms(@Query("limit") int limit);

    @GET("/api/films/topRated")
    Call<List<FilmDTO>> getTopRatedFilms(@Query("limit") int limit);

    @PUT("/api/films/{id}")
    Call<FilmDTO> updateFilm(@Path("id") Long id, @Body FilmDTO filmDTO);

    @DELETE("/api/films/{id}")
    Call<Void> deleteFilm(@Path("id") Long id);

    // MovieList endpoints
    @POST("/api/movieLists")
    Call<MovieListDTO> createMovieList(@Body MovieListDTO movieListDTO);

    @GET("/api/movieLists/{id}")
    Call<MovieListDTO> getMovieListById(@Path("id") Long id);

    @GET("/api/movieLists")
    Call<List<MovieListDTO>> getAllMovieLists();

    @PUT("/api/movieLists/{id}")
    Call<MovieListDTO> updateMovieList(@Path("id") Long id, @Body MovieListDTO movieListDTO);

    @DELETE("/api/movieLists/{id}")
    Call<Void> deleteMovieList(@Path("id") Long id);

    // Review endpoints
    @POST("/api/reviews")
    Call<ReviewDTO> createReview(@Body ReviewDTO reviewDTO);

    @GET("/api/reviews/{id}")
    Call<ReviewDTO> getReviewById(@Path("id") Long id);

    @GET("/api/reviews/user/{userId}")
    Call<List<ReviewDTO>> getReviewsByUserId(@Path("userId") Long userId);

    @GET("/api/reviews/film/{filmId}")
    Call<List<ReviewDTO>> getReviewsByFilmId(@Path("filmId") Long filmId);

    @GET("/api/reviews")
    Call<List<ReviewDTO>> getAllReviews();

    @PUT("/api/reviews/{id}")
    Call<ReviewDTO> updateReview(@Path("id") Long id, @Body ReviewDTO reviewDTO);

    @DELETE("/api/reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long id);

    // User endpoints
    @GET("/api/users/public/{username}")
    Call<UserDTO> getUserByUsername(@Path("username") String username);

    @GET("/api/users")
    Call<List<UserDTO>> getAllUsers();

    @DELETE("/api/users/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @POST("/api/users/{id}/follow")
    Call<Void> followUser(@Path("id") Long id);

    @DELETE("/api/users/{id}/unfollow")
    Call<Void> unfollowUser(@Path("id") Long id);

    @GET("/api/users/followers/{username}")
    Call<List<UserDTO>> getFollowers(@Path("username") String username);

    @GET("/api/users/follows/{username}")
    Call<List<UserDTO>> getFollows(@Path("username") String username);

}
