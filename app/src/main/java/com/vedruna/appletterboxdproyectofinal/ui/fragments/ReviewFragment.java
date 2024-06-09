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
import com.vedruna.appletterboxdproyectofinal.dto.ReviewDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.ReviewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewReviews);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReviewAdapter(new ArrayList<>(), getContext()); // Pasar lista vacía y contexto
        recyclerView.setAdapter(adapter);

        fetchReviews();

        return view;
    }

    private void fetchReviews() {
        ApiService apiService = RetrofitClient.getApiService(getContext());
        Call<List<ReviewDTO>> call = apiService.getAllReviews();
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ReviewDTO> reviews = response.body();
                    Collections.reverse(reviews); // Invertir la lista

                    adapter.updateItems(reviews);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // Handle the error
            }
        });
    }
}
