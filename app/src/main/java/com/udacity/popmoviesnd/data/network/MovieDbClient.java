package com.udacity.popmoviesnd.data.network;

import com.udacity.popmoviesnd.domain.model.MoviePage;
import com.udacity.popmoviesnd.domain.model.MovieReviewPage;
import com.udacity.popmoviesnd.domain.model.MovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbClient {

    String API_KEY_KEY = "api_key";

    @GET("movie/popular")
    Call<MoviePage> getListOfPopularMovies(@Query(API_KEY_KEY) String apiKey);

    @GET("movie/top_rated")
    Call<MoviePage> getListOfTopRatedMovies(@Query(API_KEY_KEY) String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieVideos> getListOfVideos(
            @Path("id") int movieId,
            @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<MovieReviewPage> getListOfReviews(
            @Path("id") int movieId,
            @Query("api_key") String apiKey);

}
