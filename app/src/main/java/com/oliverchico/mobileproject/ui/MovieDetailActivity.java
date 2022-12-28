package com.oliverchico.mobileproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.db.Database;
import com.oliverchico.mobileproject.model.Configuration;
import com.oliverchico.mobileproject.model.Genre;
import com.oliverchico.mobileproject.model.Movie;
import com.oliverchico.mobileproject.model.Status;
import com.oliverchico.mobileproject.model.Watchlist;
import com.oliverchico.mobileproject.tmdb.ConfigurationService;
import com.oliverchico.mobileproject.tmdb.MovieService;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivity";
    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    private final MovieService movieService = MovieService.getInstance();
    private final ConfigurationService configurationService = ConfigurationService.getInstance();

    private CoordinatorLayout coordinatorLayout;
    private MaterialToolbar toolbar;
    private ShapeableImageView ivPoster, ivBackdrop;
    private TextView tvTitle, tvOverview, tvMiniInfo, tvReleaseDate, tvStatus;
    private Button btnWatchlist;
    private RatingBar rbVoteAverage;

    private final Database db = new Database(this);

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        coordinatorLayout = findViewById(R.id.movie_detail_coordinator_layout);
        toolbar = findViewById(R.id.movie_detail_top_app_bar);

        ivPoster = findViewById(R.id.iv_movie_detail_poster);
        ivBackdrop = findViewById(R.id.iv_movie_detail_backdrop);
        tvTitle = findViewById(R.id.tv_movie_detail_title);
        tvOverview = findViewById(R.id.tv_movie_detail_overview);
        tvMiniInfo = findViewById(R.id.tv_movie_detail_mini_info);
        tvReleaseDate = findViewById(R.id.tv_movie_detail_release_date);
        tvStatus = findViewById(R.id.tv_movie_detail_status);
        rbVoteAverage = findViewById(R.id.rb_movie_detail_vote_average);
        btnWatchlist = findViewById(R.id.btn_movie_detail_add_to_watchlist);

        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        if (movieId == -1) {
            throw new RuntimeException("Movie ID not found in intent");
        }

        fetchMovie(movieId);
    }

    private void fetchMovie(int movieId) {
        configurationService.getConfiguration()
                .enqueue(new Callback<Configuration>() {
                    @Override
                    public void onResponse(@NonNull Call<Configuration> call, @NonNull Response<Configuration> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Configuration.Image config = response.body()
                                    .getImage();
                            movieService.getMovie(movieId)
                                    .enqueue(new Callback<Movie>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                                            Movie movie = response.body();
                                            setView(movie, config);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Configuration> call, @NonNull Throwable t) {

                    }
                });
    }

    private void setView(Movie movie, Configuration.Image config) {
        setToolbar(movie);
        setMovie(movie, config);
    }

    private void setMovie(Movie movie, Configuration.Image config) {
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        LocalDate releaseDate = LocalDate.parse(movie.getReleaseDate());
        tvMiniInfo.setText(movie.getGenres()
                .subList(0, 3)
                .stream()
                .map(Genre::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("-"));
        tvReleaseDate.setText(releaseDate.toString());
        tvStatus.setText(movie.getStatus());
        rbVoteAverage.setRating(movie.getVoteAverage() / 2);
        String posterUrl = config.getBaseUrl() + config.getPosterSizes()
                .get(4) + movie.getPosterPath();
        String backdropUrl = config.getBaseUrl() + config.getBackdropSizes()
                .get(2) + movie.getBackdropPath();
        Glide.with(this)
                .load(posterUrl)
                .into(ivPoster);
        Glide.with(this)
                .load(backdropUrl)
                .into(ivBackdrop);

        if (db.isWatchlistExist(movie.getId())) {
            btnWatchlist.setText(R.string.view_watchlist_text);
            btnWatchlist.setOnClickListener(v -> {
                Intent intent = new Intent(this, WatchlistActivity.class);
                startActivity(intent);
            });
        } else {
            btnWatchlist.setText(R.string.add_to_watchlist_text);
            btnWatchlist.setOnClickListener(v -> {
                Watchlist watchlist = new Watchlist();
                watchlist.setId(movie.getId());
                watchlist.setTitle(movie.getTitle());
                watchlist.setStatus(Status.PLAN_TO_WATCH);
                db.insertWatchlist(watchlist);
                btnWatchlist.setText(R.string.view_watchlist_text);
                btnWatchlist.setOnClickListener(v1 -> {
                    Intent intent = new Intent(this, WatchlistActivity.class);
                    startActivity(intent);
                });

                Snackbar.make(coordinatorLayout, "Added to Watchlist", Snackbar.LENGTH_SHORT)
                        .show();
            });
        }
    }

    private void setToolbar(Movie movie) {
        toolbar.setTitle(movie.getTitle());
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
            return false;
        });
    }
}