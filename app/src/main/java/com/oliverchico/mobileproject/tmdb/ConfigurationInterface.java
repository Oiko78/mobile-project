package com.oliverchico.mobileproject.tmdb;

import com.oliverchico.mobileproject.model.Configuration;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ConfigurationInterface {

    @GET("configuration")
    Call<Configuration> getConfiguration(@Query("api_key") String apiKey);

}
