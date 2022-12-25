package com.oliverchico.mobileproject;

import android.os.AsyncTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class TmdbClient {
    private final static String API_KEY = "3c4a05dc7a92dcfac5346b5c715a984c";
    private TmdbApi client = null;

    public TmdbClient() {
        client = getClient();
    }

    private TmdbApi getClient() {
        try {
            if (client == null) {
                client = new GetInstance().execute().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            client = null;
        }

        return client;
    }

    public MovieResultsPage getPopularMovies(int page) {
        MovieResultsPage movies = null;

        try {
            movies = new GetPopularMovies().execute(page).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public MovieDb getMovie(int movieId) {
        MovieDb movie = null;

        try {
            movie = new GetMovie().execute(movieId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    private class GetInstance extends AsyncTask<Void, Integer, TmdbApi> {
        @Override
        protected TmdbApi doInBackground(Void... voids) {
            TmdbApi client = new TmdbApi(API_KEY);
            return client;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // do something while fetching
        }
    }

    private class GetMovie extends AsyncTask<Integer, Integer, MovieDb> {
        @Override
        protected MovieDb doInBackground(Integer... params) {
            MovieDb movie = getClient().getMovies().getMovie(params[0], "en-US");
            return movie;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // do something while fetching
        }
    }

    private class GetPopularMovies extends AsyncTask<Integer, Integer, MovieResultsPage> {
        @Override
        protected MovieResultsPage doInBackground(Integer... params) {
            MovieResultsPage movies = getClient().getMovies().getPopularMovies("en-US", params[0]);
            return movies;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // do something while fetching
        }
    }
}
