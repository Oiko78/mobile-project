package com.oliverchico.mobileproject.tmdb;

import com.oliverchico.mobileproject.model.TimeWindow;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieService extends BaseService{

    private static MovieService instance;
    private MovieInterface movieInterface;

    private MovieService() {
        super();
        this.movieInterface = retrofit.create(MovieInterface.class);
    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }

        return instance;
    }

    public MovieService getPopularMovies(int page) {
        request = movieInterface.getPopularMovies(API_KEY, LANGUAGE, page, REGION);
        return this;
    }

    public MovieService getMovie(int id) {
        request = movieInterface.getMovie(id, API_KEY, LANGUAGE);
        return this;
    }

    public MovieService getTrendingMovies(TimeWindow timeWindow) {
        request = movieInterface.getTrendingMovies(timeWindow.toString(), API_KEY);
        return this;
    }

}
