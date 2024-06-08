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
import com.vedruna.appletterboxdproyectofinal.dto.MovieListDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.ManageMovieListsAdapter;
import com.vedruna.appletterboxdproyectofinal.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMovieListsActivity extends AppCompatActivity implements ManageMovieListsAdapter.OnManageListListener {

    private static final String TAG = "ManageMovieListsActivity";
    private RecyclerView recyclerView;
    private ManageMovieListsAdapter adapter;
    private Long filmId; // ID de la película actual
    private String username; // Nombre de usuario del usuario actualmente logueado

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_movie_lists);

        filmId = getIntent().getLongExtra("filmId", -1);
        username = TokenManager.getInstance(this).getUsername(); // Obtiene el nombre de usuario logueado

        recyclerView = findViewById(R.id.listsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ManageMovieListsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        fetchMovieLists();

        Button createNewListButton = findViewById(R.id.createNewListButton);
        createNewListButton.setOnClickListener(v -> showCreateListDialog());
    }

    private void fetchMovieLists() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<MovieListDTO>> call = apiService.getMovieListsByUsername(username);

        call.enqueue(new Callback<List<MovieListDTO>>() {
            @Override
            public void onResponse(Call<List<MovieListDTO>> call, Response<List<MovieListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateItems(response.body());
                } else {
                    Toast.makeText(ManageMovieListsActivity.this, "Failed to fetch lists", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to fetch lists: " + response.message());
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
            public void onFailure(Call<List<MovieListDTO>> call, Throwable t) {
                Toast.makeText(ManageMovieListsActivity.this, "Failed to fetch lists", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to fetch lists: " + t.getMessage(), t);
            }
        });
    }

    private void showCreateListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New List");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_list, null);
        builder.setView(view);

        EditText listNameEditText = view.findViewById(R.id.editListName);
        EditText listDescriptionEditText = view.findViewById(R.id.editListDescription);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String name = listNameEditText.getText().toString().trim();
            String description = listDescriptionEditText.getText().toString().trim();
            createMovieList(new MovieListDTO(null, name, username, null, description, false, new ArrayList<>())); // Usa el nombre de usuario
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void createMovieList(MovieListDTO movieListDTO) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<MovieListDTO> call = apiService.createMovieList(movieListDTO);

        call.enqueue(new Callback<MovieListDTO>() {
            @Override
            public void onResponse(Call<MovieListDTO> call, Response<MovieListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchMovieLists(); // Refresh the list
                    Toast.makeText(ManageMovieListsActivity.this, "List created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageMovieListsActivity.this, "Failed to create list", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to create list: " + response.message());
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
            public void onFailure(Call<MovieListDTO> call, Throwable t) {
                Toast.makeText(ManageMovieListsActivity.this, "Failed to create list", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to create list: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onAddMovieToList(MovieListDTO movieList) {
        Log.d(TAG, "Adding film to list: " + movieList.getListId());
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<MovieListDTO> call = apiService.addFilmToList(movieList.getListId(), filmId);

        call.enqueue(new Callback<MovieListDTO>() {
            @Override
            public void onResponse(Call<MovieListDTO> call, Response<MovieListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchMovieLists(); // Refresh the list
                    Toast.makeText(ManageMovieListsActivity.this, "Film added to list", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageMovieListsActivity.this, "Failed to add film to list", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to add film to list: " + response.message());
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
            public void onFailure(Call<MovieListDTO> call, Throwable t) {
                Toast.makeText(ManageMovieListsActivity.this, "Failed to add film to list", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to add film to list: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onDeleteList(MovieListDTO movieList) {
        Log.d(TAG, "Deleting list: " + movieList.getListId());
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Void> call = apiService.deleteMovieList(movieList.getListId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchMovieLists(); // Refresh the list
                    Toast.makeText(ManageMovieListsActivity.this, "List deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageMovieListsActivity.this, "Failed to delete list", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to delete list: " + response.message());
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
                Toast.makeText(ManageMovieListsActivity.this, "Failed to delete list", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to delete list: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onRemoveMovieFromList(MovieListDTO movieList) {
        Log.d("ManageMovieListsActivity", "Removing film from list: " + movieList.getListId() + " Film ID: " + filmId);
        if (movieList.getListId() == null) {
            Log.e("ManageMovieListsActivity", "List ID is null. Cannot remove film.");
            Toast.makeText(this, "List ID is null. Cannot remove film.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService(this);
        Call<MovieListDTO> call = apiService.removeFilmFromList(movieList.getListId(), filmId);

        call.enqueue(new Callback<MovieListDTO>() {
            @Override
            public void onResponse(Call<MovieListDTO> call, Response<MovieListDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchMovieLists(); // Refresh the list
                    Toast.makeText(ManageMovieListsActivity.this, "Film removed from list", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageMovieListsActivity.this, "Failed to remove film from list", Toast.LENGTH_SHORT).show();
                    Log.e("ManageMovieListsActivity", "Failed to remove film from list: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("ManageMovieListsActivity", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieListDTO> call, Throwable t) {
                Toast.makeText(ManageMovieListsActivity.this, "Failed to remove film from list", Toast.LENGTH_SHORT).show();
                Log.e("ManageMovieListsActivity", "Failed to remove film from list: " + t.getMessage(), t);
            }
        });
    }



}
