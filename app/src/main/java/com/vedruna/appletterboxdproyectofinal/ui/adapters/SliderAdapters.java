package com.vedruna.appletterboxdproyectofinal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {

    private List<FilmDTO> sliderItems;
    private Context context;

    public SliderAdapters(List<FilmDTO> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.slide_container, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    FilmDTO film = sliderItems.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("filmId", film.getFilmId());
                    context.startActivity(intent);
                }
            });
        }

        void setImage(FilmDTO film) {
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new CenterCrop(), new RoundedCorners(30));
            Glide.with(context)
                    .load(film.getPosterUrl())
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    public void updateItems(List<FilmDTO> newItems) {
        sliderItems.clear();
        sliderItems.addAll(newItems);
        notifyDataSetChanged();
    }
}
