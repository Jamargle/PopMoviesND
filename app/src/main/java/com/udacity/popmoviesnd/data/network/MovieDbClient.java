package com.udacity.popmoviesnd.data.network;

import com.udacity.popmoviesnd.domain.model.MoviePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbClient {

    String API_KEY_KEY = "api_key";

    @GET("movie/popular")
    Call<MoviePage> getListOfPopularMovies(@Query(API_KEY_KEY) String apiKey);

    @GET("movie/top_rated")
    Call<MoviePage> getListOfTopRatedMovies(@Query(API_KEY_KEY) String apiKey);

}
