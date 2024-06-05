package com.vedruna.appletterboxdproyectofinal.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.ui.adapters.SearchFilmListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchFilmListAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.recyclerViewSearchResults);

        // Configurar GridLayoutManager con 2 columnas
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SearchFilmListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        String query = getIntent().getStringExtra("query");
        if (query != null) {
            searchFilms(query);
        } else {
            Toast.makeText(this, "No search query provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchFilms(String query) {
        apiService = RetrofitClient.getApiService(this);
        Call<List<FilmDTO>> call = apiService.searchFilmsByTitle(query);
        call.enqueue(new Callback<List<FilmDTO>>() {
            @Override
            public void onResponse(Call<List<FilmDTO>> call, Response<List<FilmDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateItems(response.body());
                } else {
                    Toast.makeText(SearchResultsActivity.this, "No films found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FilmDTO>> call, Throwable t) {
                Toast.makeText(SearchResultsActivity.this, "Error searching films", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
