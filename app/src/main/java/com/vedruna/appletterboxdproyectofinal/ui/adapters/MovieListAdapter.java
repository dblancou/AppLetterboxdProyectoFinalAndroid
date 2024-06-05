package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.MovieListDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<MovieListDTO> movieLists;
    private Context context;

    public MovieListAdapter(List<MovieListDTO> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieListDTO movieList = movieLists.get(position);
        holder.listName.setText(movieList.getName());
        holder.description.setText(movieList.getDescription());
        holder.userName.setText(movieList.getUserName());

        holder.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewMovies.setAdapter(new FilmListAdapter(movieList.getFilms()));

        checkFollowingStatus(movieList.getUserId(), holder.followButton, movieList);

        holder.followButton.setOnClickListener(v -> {
            boolean isFollowing = movieList.isFollowing();
            if (isFollowing) {
                unfollowUser(movieList.getUserId(), holder.followButton);
            } else {
                followUser(movieList.getUserId(), holder.followButton);
            }
            movieList.setFollowing(!isFollowing);  // Update the local state
        });
    }

    private void checkFollowingStatus(Long userId, Button followButton, MovieListDTO movieList) {
        ApiService apiService = RetrofitClient.getApiService(context);
        Call<Boolean> call = apiService.isFollowing(userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    boolean isFollowing = response.body();
                    movieList.setFollowing(isFollowing);
                    updateFollowButton(isFollowing, followButton);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Handle the error
                Log.e("MovieListAdapter", "Error checking following status", t);
            }
        });
    }

    private void followUser(Long userId, Button followButton) {
        ApiService apiService = RetrofitClient.getApiService(context);
        Call<Void> call = apiService.followUser(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    updateFollowButton(true, followButton);
                    Toast.makeText(context, "Siguiendo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the error
                Log.e("MovieListAdapter", "Error following user", t);
            }
        });
    }

    private void unfollowUser(Long userId, Button followButton) {
        ApiService apiService = RetrofitClient.getApiService(context);
        Call<Void> call = apiService.unfollowUser(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    updateFollowButton(false, followButton);
                    Toast.makeText(context, "Dejaste de seguir", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the error
                Log.e("MovieListAdapter", "Error unfollowing user", t);
            }
        });
    }

    private void updateFollowButton(boolean isFollowing, Button followButton) {
        if (isFollowing) {
            followButton.setText("Siguiendo");
            followButton.setBackgroundColor(ContextCompat.getColor(context, R.color.buttonPressed));
        } else {
            followButton.setText("Seguir");
            followButton.setBackgroundColor(ContextCompat.getColor(context, R.color.buttonNormal));
        }
    }

    @Override
    public int getItemCount() {
        return movieLists != null ? movieLists.size() : 0;
    }

    public void updateItems(List<MovieListDTO> newMovieLists) {
        movieLists = newMovieLists;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView description;
        TextView userName;
        Button followButton;
        RecyclerView recyclerViewMovies;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            description = itemView.findViewById(R.id.description);
            userName = itemView.findViewById(R.id.userName);
            followButton = itemView.findViewById(R.id.followButton);
            recyclerViewMovies = itemView.findViewById(R.id.recyclerViewMovies);
        }
    }
}
