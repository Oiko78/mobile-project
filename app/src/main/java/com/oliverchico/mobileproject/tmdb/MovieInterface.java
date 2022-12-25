package com.oliverchico.mobileproject.tmdb;

import com.oliverchico.mobileproject.model.Movie;
import com.oliverchico.mobileproject.model.MovieResultsPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") Integer id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/popular")
    Call<MovieResultsPage> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Integer page,
            @Query("region") String region
    );

    @GET("trending/movie/{time_window}")
    Call<MovieResultsPage> getTrendingMovies(
            @Path("time_window") String timeWindow,
            @Query("api_key") String apiKey
    );

}
