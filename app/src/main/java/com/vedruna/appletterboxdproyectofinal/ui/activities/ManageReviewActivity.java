package com.vedruna.appletterboxdproyectofinal.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.ReviewDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.ManageReviewAdapter;
import com.vedruna.appletterboxdproyectofinal.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageReviewActivity extends AppCompatActivity implements ManageReviewAdapter.OnReviewActionListener {

    private static final String TAG = "ManageReviewsActivity";
    private RecyclerView recyclerView;
    private ManageReviewAdapter adapter;
    private Long filmId; // ID de la película actual
    private Long userId; // ID del usuario actualmente logueado

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reviews);

        filmId = getIntent().getLongExtra("filmId", -1);
        userId = TokenManager.getInstance(this).getUserId(); // Obtiene el ID del usuario logueado

        recyclerView = findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ManageReviewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        fetchReviews();

        Button createNewReviewButton = findViewById(R.id.createNewReviewButton);
        createNewReviewButton.setOnClickListener(v -> showCreateReviewDialog());
    }

    private void fetchReviews() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<ReviewDTO>> call = apiService.getReviewsByUserId(userId);

        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateItems(response.body());
                } else {
                    Toast.makeText(ManageReviewActivity.this, "Failed to fetch reviews", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to fetch reviews: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
                Toast.makeText(ManageReviewActivity.this, "Failed to fetch reviews", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to fetch reviews: " + t.getMessage(), t);
            }
        });
    }

    private void showCreateReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Review");

        View view = getLayoutInflater().inflate(R.layout.dialog_create_review, null);
        builder.setView(view);

        EditText reviewContentEditText = view.findViewById(R.id.editReviewContent);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String content = reviewContentEditText.getText().toString().trim();
            createReview(new ReviewDTO(null, userId, null, filmId, null, null, content, 0.0)); // Asignar un valor predeterminado a rating
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void createReview(ReviewDTO reviewDTO) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<ReviewDTO> call = apiService.createReview(reviewDTO);

        call.enqueue(new Callback<ReviewDTO>() {
            @Override
            public void onResponse(Call<ReviewDTO> call, Response<ReviewDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchReviews(); // Refresh the list
                    Toast.makeText(ManageReviewActivity.this, "Review created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageReviewActivity.this, "Failed to create review", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to create review: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewDTO> call, Throwable t) {
                Toast.makeText(ManageReviewActivity.this, "Failed to create review", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to create review: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onDeleteReview(ReviewDTO review) {
        Log.d(TAG, "Deleting review: " + review.getReviewId());
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Void> call = apiService.deleteReview(review.getReviewId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchReviews(); // Refresh the list
                    Toast.makeText(ManageReviewActivity.this, "Review deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageReviewActivity.this, "Failed to delete review", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to delete review: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManageReviewActivity.this, "Failed to delete review", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to delete review: " + t.getMessage(), t);
            }
        });
    }
}
