package com.vedruna.appletterboxdproyectofinal.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.MovieListDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.MovieListAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLists);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieListAdapter(null, getContext());
        recyclerView.setAdapter(adapter);

        fetchMovieLists();

        return view;
    }

    private void fetchMovieLists() {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<MovieListDTO>> call = apiService.getAllMovieLists();
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<MovieListDTO>>() {
            @Override
            public void onResponse(Call<List<MovieListDTO>> call, Response<List<MovieListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieListDTO> movieLists = response.body();
                    Collections.reverse(movieLists); // Invertir la lista
                    adapter.updateItems(movieLists);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<MovieListDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // Handle the error
            }
        });
    }
}
