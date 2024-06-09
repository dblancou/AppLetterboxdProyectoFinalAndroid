package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.ReviewDTO;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewDTO> reviews;
    private Context context;

    public ReviewAdapter(List<ReviewDTO> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    public void updateItems(List<ReviewDTO> newReviews) {
        this.reviews = newReviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDTO review = reviews.get(position);
        holder.titleTextView.setText(review.getTitle());
        holder.userNameTextView.setText(review.getUserName());
        holder.contentTextView.setText(review.getContent());
        Glide.with(context)
                .load(review.getPosterUrl())
                .into(holder.posterImageView);

        checkFollowingStatus(review.getUserId(), holder.followButton, review);

        holder.followButton.setOnClickListener(v -> {
            boolean isFollowing = review.isFollowing();
            if (isFollowing) {
                unfollowUser(review.getUserId(), holder.followButton, review);
            } else {
                followUser(review.getUserId(), holder.followButton, review);
            }
            review.setFollowing(!isFollowing);  // Update the local state
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView userNameTextView;
        TextView contentTextView;
        ImageView posterImageView;
        Button followButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }

    private void checkFollowingStatus(Long userId, Button followButton, ReviewDTO review) {
        ApiService apiService = RetrofitClient.getApiService(context);
        Call<Boolean> call = apiService.isFollowing(userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isFollowing = response.body();
                    review.setFollowing(isFollowing);
                    updateFollowButton(isFollowing, followButton);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ReviewAdapter", "Error checking following status", t);
            }
        });
    }

    private void followUser(Long userId, Button followButton, ReviewDTO review) {
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
                Log.e("ReviewAdapter", "Error following user", t);
            }
        });
    }

    private void unfollowUser(Long userId, Button followButton, ReviewDTO review) {
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
                Log.e("ReviewAdapter", "Error unfollowing user", t);
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
}
