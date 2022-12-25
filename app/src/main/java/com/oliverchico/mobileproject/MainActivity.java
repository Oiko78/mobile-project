package com.oliverchico.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oliverchico.mobileproject.model.Configuration;
import com.oliverchico.mobileproject.model.Movie;
import com.oliverchico.mobileproject.model.MovieResultsPage;
import com.oliverchico.mobileproject.model.TimeWindow;
import com.oliverchico.mobileproject.tmdb.ConfigurationService;
import com.oliverchico.mobileproject.tmdb.MovieService;

import java.io.IOException;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MovieService service = MovieService.getInstance();
//        service.getTrendingMovies(TimeWindow.DAY).enqueue(new Callback<MovieResultsPage>() {
//            @Override
//            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response) {
//                if (response.isSuccessful())  {
//                    MovieResultsPage movieResultPage = response.body();
//
//                    // do something with movies
//                    System.out.println(
//                            "Trending movies:\n" +
//                            movieResultPage.getResults()
//                                    .stream()
//                                    .map(movie -> movie.getTitle())
//                                    .collect(Collectors.joining("\n"))
//                    );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieResultsPage> call, Throwable t) {
//                // do something if error
//            }
//        });
//
//        service.getMovie(76600).enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                if (response.isSuccessful())  {
//                    Movie movie = response.body();
//
//                    // do something with the movie
//                    System.out.println(
//                            "Movie " +
//                            movie.getTitle() +
//                            " | " +
//                            movie.getGenres()
//                                    .stream()
//                                    .map(genre -> genre.getName())
//                                    .collect(Collectors.joining(", "))
//                    );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//
//            }
//        });

        ConfigurationService service = ConfigurationService.getInstance();
        service.getConfiguration().enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                if(response.isSuccessful()) {
                    Configuration configuration = response.body();
                    System.out.println(configuration.getImage().getBaseUrl());
                }
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {

            }
        });
    }
}