package com.oliverchico.mobileproject;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.oliverchico.mobileproject.model.Configuration;
import com.oliverchico.mobileproject.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private Configuration.Image configuration;


    @SuppressLint("NotifyDataSetChanged")
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setConfiguration(Configuration.Image configuration) {
        this.configuration = configuration;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView ivBackdrop;
        private final TextView tvTitle, tvExtraInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.iv_trending);
            tvTitle = itemView.findViewById(R.id.tv_trending_title);
            tvExtraInfo = itemView.findViewById(R.id.tv_trending_extra_info);
        }

        public void bind(Movie movie) {
            String backdropUrl;
            if (configuration != null) {
                backdropUrl = configuration.getSecureBaseUrl() + configuration.getBackdropSizes()
                        .get(2) + movie.getBackdropPath();
            } else {
                backdropUrl = "https://image.tmdb.org/t/p/w780" + movie.getBackdropPath();
            }

            Glide.with(itemView)
                    .load(backdropUrl)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivBackdrop);
            tvTitle.setText(movie.getTitle());
            tvExtraInfo.setText(String.format("Release Date: %s", movie.getReleaseDate()));
        }
    }
}
