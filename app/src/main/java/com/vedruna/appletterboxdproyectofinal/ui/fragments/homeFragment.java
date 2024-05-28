package com.vedruna.appletterboxdproyectofinal.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ProgressBar progressBar1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = view.findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies = view.findViewById(R.id.view1);
        progressBar1 = view.findViewById(R.id.progressBar1);

        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        progressBar1.setVisibility(View.VISIBLE);

        setupViewPager();
        fetchLatestFilms();
        fetchBestMovies();

        return view;
    }

    private void setupViewPager() {
        List<FilmDTO> sliderItems = new ArrayList<>();
        SliderAdapters sliderAdapter = new SliderAdapters(sliderItems);
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
    }

    private void fetchLatestFilms() {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<FilmDTO>> call = apiService.getLatestFilms(5);
        call.enqueue(new Callback<List<FilmDTO>>() {
            @Override
            public void onResponse(Call<List<FilmDTO>> call, Response<List<FilmDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FilmDTO> films = response.body();
                    SliderAdapters sliderAdapter = new SliderAdapters(films);
                    viewPager2.setAdapter(sliderAdapter);
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
        Call<List<FilmDTO>> call = apiService.getTopRatedFilms(8);
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

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
}
