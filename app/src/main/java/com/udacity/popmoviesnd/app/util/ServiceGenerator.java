package com.udacity.popmoviesnd.app.util;

import com.udacity.popmoviesnd.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(final Class<S> serviceClass) {
        final Retrofit retrofit = retrofitBuilder
                .client(new OkHttpClient.Builder().build())
                .build();
        return retrofit.create(serviceClass);
    }

}
