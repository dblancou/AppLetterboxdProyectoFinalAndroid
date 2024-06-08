package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.MovieListDTO;

import java.util.List;

public class ManageMovieListsAdapter extends RecyclerView.Adapter<ManageMovieListsAdapter.ViewHolder> {

    private List<MovieListDTO> movieLists;
    private OnManageListListener onManageListListener;

    public ManageMovieListsAdapter(List<MovieListDTO> movieLists, OnManageListListener onManageListListener) {
        this.movieLists = movieLists;
        this.onManageListListener = onManageListListener;
    }

    public void updateItems(List<MovieListDTO> newMovieLists) {
        this.movieLists = newMovieLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_manage_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieListDTO movieList = movieLists.get(position);
        holder.listName.setText(movieList.getName());

        Log.d("ManageMovieListsAdapter", "List ID: " + movieList.getListId() + ", List Name: " + movieList.getName());

        holder.addButton.setOnClickListener(v -> onManageListListener.onAddMovieToList(movieList));
        holder.deleteButton.setOnClickListener(v -> onManageListListener.onDeleteList(movieList));
        holder.removeButton.setOnClickListener(v -> onManageListListener.onRemoveMovieFromList(movieList));
    }



    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public interface OnManageListListener {
        void onAddMovieToList(MovieListDTO movieList);
        void onDeleteList(MovieListDTO movieList);
        void onRemoveMovieFromList(MovieListDTO movieList); // Agregar método
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        Button addButton, deleteButton, removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            addButton = itemView.findViewById(R.id.addButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            removeButton = itemView.findViewById(R.id.removeButton); // Inicializar botón
        }
    }
}
