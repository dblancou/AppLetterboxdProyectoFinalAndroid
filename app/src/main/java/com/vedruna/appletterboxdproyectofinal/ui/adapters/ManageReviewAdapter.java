package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.ReviewDTO;

import java.util.List;

public class ManageReviewAdapter extends RecyclerView.Adapter<ManageReviewAdapter.ViewHolder> {

    private List<ReviewDTO> reviews;
    private OnReviewActionListener onReviewActionListener;

    public ManageReviewAdapter(List<ReviewDTO> reviews, OnReviewActionListener onReviewActionListener) {
        this.reviews = reviews;
        this.onReviewActionListener = onReviewActionListener;
    }

    public void updateItems(List<ReviewDTO> newReviews) {
        this.reviews = newReviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_manage_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDTO review = reviews.get(position);
        holder.reviewTitleTextView.setText(review.getTitle());

        holder.deleteReviewButton.setOnClickListener(v -> onReviewActionListener.onDeleteReview(review));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public interface OnReviewActionListener {
        void onDeleteReview(ReviewDTO review);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewTitleTextView;
        Button deleteReviewButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewTitleTextView = itemView.findViewById(R.id.reviewTitleTextView);
            deleteReviewButton = itemView.findViewById(R.id.deleteReviewButton);
        }
    }
}
