package com.a700apps.techmart.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samir salah on 9/14/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://23.236.154.106:8086/api/";
    private static Retrofit retrofit = null;
    public static int unique_id;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
