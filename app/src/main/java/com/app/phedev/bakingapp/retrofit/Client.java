package com.app.phedev.bakingapp.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phedev in 2017.
 */

public class Client {
    public static final String BASE_URL="https://d17h27t6h515a5.cloudfront.net/";
    //topher/2017/May/59121517_baking/baking.json

    private static OkHttpClient.Builder sHttpClient =
            //can add interceptors here
            new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(sHttpClient.build());

    public static <T> T createService(Class<T> serviceClass) {
        return sBuilder.build().create(serviceClass);
    }


}
