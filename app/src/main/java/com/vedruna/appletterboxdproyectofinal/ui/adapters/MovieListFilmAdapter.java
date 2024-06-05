package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;

import java.util.List;

public class MovieListFilmAdapter extends RecyclerView.Adapter<MovieListFilmAdapter.ViewHolder> {

    private List<FilmDTO> films;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(FilmDTO film);
    }

    public MovieListFilmAdapter(List<FilmDTO> films) {
        this.films = films;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmDTO film = films.get(position);
        Glide.with(holder.itemView.getContext())
                .load(film.getPosterUrl())
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(film);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
