/*
package com.example.garridoapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class api_client {

    public static final String BASE_URL = "https://manticoresoft.com/rn/garrido/api/";

    private static Retrofit retrofit = null;
    private static APIService api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static APIService getClient() {
        if (api == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            APIService api = retrofit.create(APIService.class);
            return api;
        } else
            return api;
    }
}
*/
