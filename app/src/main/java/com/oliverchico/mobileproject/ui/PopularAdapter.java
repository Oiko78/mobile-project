package com.oliverchico.mobileproject.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.model.Configuration;
import com.oliverchico.mobileproject.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
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
                .inflate(R.layout.item_popular, parent, false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.iv_popular);
        }

        public void bind(Movie movie) {
            Glide.with(itemView)
                    .load(configuration.getSecureBaseUrl() + configuration.getPosterSizes().get(4) + movie.getPosterPath())
                    .error(R.drawable.ic_launcher_background)
                    .into(ivBackdrop);
            itemView.setOnClickListener(v -> v.getContext().startActivity(MovieDetailActivity.newIntent(v.getContext(), movie)));
        }
    }
}
