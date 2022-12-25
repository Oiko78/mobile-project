package com.oliverchico.mobileproject.tmdb;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BaseService {
    protected final static String API_KEY = "3c4a05dc7a92dcfac5346b5c715a984c";
    protected final static String LANGUAGE = "en-US";
    protected final static String REGION = "ID";

    protected Retrofit retrofit;
    protected Call request;

    protected BaseService() {
        this.retrofit = TmdbClient.retrofit();
    }

    public <T> void enqueue(Callback<T> callback) {
        request.enqueue(callback);
    }

}
