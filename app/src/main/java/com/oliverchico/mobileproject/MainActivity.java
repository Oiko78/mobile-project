package com.oliverchico.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import info.movito.themoviedbapi.model.MovieDb;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // consuming api example
        TmdbClient client = new TmdbClient();
        for(MovieDb movie : client.getPopularMovies(1)) {
            System.out.println(movie.getTitle());
        }
    }
}