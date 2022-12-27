package com.oliverchico.mobileproject.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.model.Configuration;
import com.oliverchico.mobileproject.model.MovieResultsPage;
import com.oliverchico.mobileproject.model.TimeWindow;
import com.oliverchico.mobileproject.tmdb.ConfigurationService;
import com.oliverchico.mobileproject.tmdb.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final MovieService movieService = MovieService.getInstance();
    private final ConfigurationService configurationService = ConfigurationService.getInstance();
    private TrendingAdapter trendingAdapter;
    private PopularAdapter popularAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.main_top_app_bar);
        setSupportActionBar(toolbar);

        RecyclerView rvTrendingMovies = findViewById(R.id.rv_trending);
        trendingAdapter = new TrendingAdapter();
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvTrendingMovies);
        rvTrendingMovies.setAdapter(trendingAdapter);
        rvTrendingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        MaterialButtonToggleGroup timeWindowToggleGroup = findViewById(R.id.time_window_toggle_group);
        timeWindowToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btn_time_window_day) {
                    fetchTrendingMovies(TimeWindow.DAY);
                } else {
                    fetchTrendingMovies(TimeWindow.WEEK);
                }
            }
        });

        RecyclerView rvPopularMovies = findViewById(R.id.rv_popular);
        popularAdapter = new PopularAdapter();
        rvPopularMovies.setAdapter(popularAdapter);
        rvPopularMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fetchTrendingMovies(TimeWindow.WEEK);
        fetchPopularMovies();
    }

    private void fetchPopularMovies() {
        configurationService.getConfiguration()
                .enqueue(new Callback<Configuration>() {
                    @Override
                    public void onResponse(@NonNull Call<Configuration> call, @NonNull Response<Configuration> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Configuration.Image config = response.body()
                                    .getImage();
                            movieService.getPopularMovies(1)
                                    .enqueue(new Callback<MovieResultsPage>() {
                                        @Override
                                        public void onResponse(@NonNull Call<MovieResultsPage> call, @NonNull Response<MovieResultsPage> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                popularAdapter.setMovies(response.body()
                                                        .getResults());
                                                popularAdapter.setConfiguration(config);
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<MovieResultsPage> call, @NonNull Throwable t) {
                                            Log.e(TAG, "onFailure: ", t);
                                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<Configuration> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void fetchTrendingMovies(TimeWindow timeWindow) {
        configurationService.getConfiguration()
                .enqueue(new Callback<Configuration>() {
                    @Override
                    public void onResponse(@NonNull Call<Configuration> call, @NonNull Response<Configuration> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Configuration.Image config = response.body()
                                    .getImage();
                            movieService.getTrendingMovies(timeWindow)
                                    .enqueue(new Callback<MovieResultsPage>() {
                                        @Override
                                        public void onResponse(@NonNull Call<MovieResultsPage> call, @NonNull Response<MovieResultsPage> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                trendingAdapter.setMovies(response.body()
                                                        .getResults());
                                                trendingAdapter.setConfiguration(config);
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<MovieResultsPage> call, @NonNull Throwable t) {
                                            Log.e(TAG, "onFailure: ", t);
                                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Configuration> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}