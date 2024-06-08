package com.vedruna.appletterboxdproyectofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, directorTextView, yearTextView, genreTextView, movieSummaryInfo;
    private ImageView pic2, backImg, addToListImg;
    private RecyclerView recyclerViewActors, recyclerViewCategory;
    private NestedScrollView scrollView;
    private long filmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_activity);

        filmId = getIntent().getLongExtra("filmId", 0);
        initView();
        fetchFilmDetails();
    }

    private void fetchFilmDetails() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<FilmDTO> call = apiService.getFilmById(filmId);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        call.enqueue(new Callback<FilmDTO>() {
            @Override
            public void onResponse(Call<FilmDTO> call, Response<FilmDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FilmDTO film = response.body();
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    Glide.with(DetailActivity.this)
                            .load(film.getPosterUrl())
                            .into(pic2);

                    titleTxt.setText(film.getTitle());
                    movieRateTxt.setText(String.valueOf(film.getImdbRating()));
                    directorTextView.setText(film.getDirector());
                    yearTextView.setText(film.getYear());
                    genreTextView.setText(film.getGenreName());
                    movieSummaryInfo.setText(film.getDescription());
                }
            }

            @Override
            public void onFailure(Call<FilmDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        titleTxt = findViewById(R.id.movieNameTxt);
        progressBar = findViewById(R.id.progressBarDetail);
        scrollView = findViewById(R.id.scrollView2);
        pic2 = findViewById(R.id.picDetail);
        movieRateTxt = findViewById(R.id.movieStar);
        directorTextView = findViewById(R.id.directorTextView);
        yearTextView = findViewById(R.id.yearTextView);
        genreTextView = findViewById(R.id.genreTextView);
        movieSummaryInfo = findViewById(R.id.movieSummery);
        backImg = findViewById(R.id.backImg);
        addToListImg = findViewById(R.id.imageView5);
        recyclerViewCategory = findViewById(R.id.genreView);
        recyclerViewActors = findViewById(R.id.imagesRecycler);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(v -> finish());

        addToListImg.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, ManageMovieListsActivity.class);
            intent.putExtra("filmId", filmId);
            startActivity(intent);
        });
    }
}
