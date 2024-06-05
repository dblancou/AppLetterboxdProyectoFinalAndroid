package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.dto.FilmDTO;
import com.vedruna.appletterboxdproyectofinal.ui.activities.DetailActivity;

import java.util.List;

public class SearchFilmListAdapter extends RecyclerView.Adapter<SearchFilmListAdapter.ViewHolder> {

    private List<FilmDTO> films;
    private Context context;

    public SearchFilmListAdapter(List<FilmDTO> films) {
        this.films = films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_film_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmDTO film = films.get(position);
        holder.titleTxt.setText(film.getTitle());

        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context)
                .load(film.getPosterUrl())
                .apply(requestOptions)
                .into(holder.pic);

        holder.pic.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("filmId", film.getFilmId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void updateItems(List<FilmDTO> newFilms) {
        films.clear();
        films.addAll(newFilms);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
