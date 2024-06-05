package com.vedruna.appletterboxdproyectofinal.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.ui.activities.SearchResultsActivity;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.FilmListAdapter;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.SliderAdapters;
import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private RecyclerView recyclerViewBestMovies;
    private RecyclerView recyclerViewActionMovies;
    private RecyclerView recyclerViewHorrorMovies;
    private RecyclerView recyclerViewSuspenseMovies;
    private RecyclerView recyclerViewSciFiMovies;
    private RecyclerView recyclerViewAnimationMovies;
    private ProgressBar progressBar1;
    private SliderAdapters sliderAdapter;
    private Runnable sliderRunnable;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = view.findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies = view.findViewById(R.id.view1);
        recyclerViewActionMovies = view.findViewById(R.id.view2);
        recyclerViewHorrorMovies = view.findViewById(R.id.view3);
        recyclerViewSuspenseMovies = view.findViewById(R.id.view4);
        recyclerViewSciFiMovies = view.findViewById(R.id.view5);
        recyclerViewAnimationMovies = view.findViewById(R.id.view6);
        progressBar1 = view.findViewById(R.id.progressBar1);
        searchEditText = view.findViewById(R.id.editTextText2);

        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewActionMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorrorMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSuspenseMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSciFiMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAnimationMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        progressBar1.setVisibility(View.VISIBLE);

        setupViewPager();
        fetchLatestFilms();
        fetchBestMovies();
        fetchFilmsByGenre("Accion", 10, recyclerViewActionMovies);
        fetchFilmsByGenre("Terror", 10, recyclerViewHorrorMovies);
        fetchFilmsByGenre("Suspenso", 10, recyclerViewSuspenseMovies);
        fetchFilmsByGenre("Ciencia Ficcion", 10, recyclerViewSciFiMovies);
        fetchFilmsByGenre("Animacion", 10, recyclerViewAnimationMovies);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchEditText.getText().toString();
            if (!query.isEmpty()) {
                Intent intent = new Intent(getContext(), SearchResultsActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
            return true;
        });

        return view;
    }

    private void setupViewPager() {
        List<FilmDTO> sliderItems = new ArrayList<>();
        sliderAdapter = new SliderAdapters(sliderItems);
        viewPager2.setAdapter(sliderAdapter);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int totalItems = sliderAdapter.getItemCount();
                if (totalItems > 0) {
                    int nextItem = (currentItem + 1) % totalItems;
                    viewPager2.setCurrentItem(nextItem, true);
                    sliderHandler.postDelayed(this, 5000); // Desplazamiento cada 5 segundos
                }
            }
        };
    }

    private void fetchLatestFilms() {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<FilmDTO>> call = apiService.getLatestFilms(6, "desc");
        call.enqueue(new Callback<List<FilmDTO>>() {
            @Override
            public void onResponse(Call<List<FilmDTO>> call, Response<List<FilmDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FilmDTO> films = response.body();
                    sliderAdapter.updateItems(films);
                }
            }

            @Override
            public void onFailure(Call<List<FilmDTO>> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching latest films", t);
            }
        });
    }

    private void fetchBestMovies() {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<FilmDTO>> call = apiService.getTopRatedFilms(10);
        call.enqueue(new Callback<List<FilmDTO>>() {
            @Override
            public void onResponse(Call<List<FilmDTO>> call, Response<List<FilmDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FilmDTO> films = response.body();
                    FilmListAdapter adapter = new FilmListAdapter(films);
                    recyclerViewBestMovies.setAdapter(adapter);
                    progressBar1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<FilmDTO>> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching best movies", t);
                progressBar1.setVisibility(View.GONE);
            }
        });
    }

    private void fetchFilmsByGenre(String genreName, int limit, RecyclerView recyclerView) {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<FilmDTO>> call = apiService.getFilmsByGenre(genreName, limit);
        call.enqueue(new Callback<List<FilmDTO>>() {
            @Override
            public void onResponse(Call<List<FilmDTO>> call, Response<List<FilmDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FilmDTO> films = response.body();
                    FilmListAdapter adapter = new FilmListAdapter(films);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<FilmDTO>> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching " + genreName + " films", t);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }


    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }

}
