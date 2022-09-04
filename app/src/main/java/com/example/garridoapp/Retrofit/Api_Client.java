package com.example.garridoapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Client {

    public static final String BASE_URL = "https://manticoresoft.com/rn/garrido/api/";

    private static Retrofit retrofit = null;
    private static Api api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

   // static HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    /*private static OkHttpClient client= new OkHttpClient
            .Builder()
            .build();*/


   public static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

//            OkHttpClient.Builder()
//            .addInterceptor(tmdbApiInterceptor)
//            .addNetworkInterceptor(interceptor)
//            .build();

    public static Api getClient() {
        if (api == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            api = retrofit.create(Api.class);
            return api;
        }
        return api;
    }

//      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)

}