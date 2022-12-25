package com.oliverchico.mobileproject.tmdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbClient {

    private final static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit;

    public static Retrofit retrofit() {
        if (retrofit == null) {
           retrofit = new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
        }

        return retrofit;
    }
}
