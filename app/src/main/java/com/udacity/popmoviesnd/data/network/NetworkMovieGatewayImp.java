package com.udacity.popmoviesnd.data.network;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MoviePage;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.domain.model.MovieReviewPage;
import com.udacity.popmoviesnd.domain.model.MovieVideos;
import com.udacity.popmoviesnd.domain.model.Sorting;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;

public final class NetworkMovieGatewayImp implements NetworkMovieGateway {

    private final MovieDbClient apiService;
    private final List<Movie> movies;

    public NetworkMovieGatewayImp(final MovieDbClient apiService) {
        this.apiService = apiService;
        movies = new ArrayList<>();
    }

    @Override
    public Observable<List<Movie>> obtainMovies(@Sorting final int criteria) {
        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> emitter) {
                movies.clear();
                if (Sorting.SHOW_ALL == criteria || Sorting.POPULAR == criteria) {
                    final Call<MoviePage> call = apiService.getListOfPopularMovies(BuildConfig.MOVIES_API_KEY);
                    addMoviesToList(call, Sorting.POPULAR);
                }

                if (Sorting.SHOW_ALL == criteria || Sorting.TOP_RATED == criteria) {
                    final Call<MoviePage> call = apiService.getListOfTopRatedMovies(BuildConfig.MOVIES_API_KEY);
                    addMoviesToList(call, Sorting.TOP_RATED);
                }

                emitter.onNext(movies);
            }
        });
    }

    @Override
    public Observable<List<Video>> obtainTrailers(final int movieId) {
        return Observable.create(new ObservableOnSubscribe<List<Video>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Video>> emitter) {
                final Call<MovieVideos> call = apiService.getListOfVideos(
                        movieId,
                        BuildConfig.MOVIES_API_KEY);

                emitter.onNext(fetchMovieTrailers(call));
            }
        });
    }

    @Override
    public Observable<List<MovieReview>> obtainReviews(final int movieId) {
        return Observable.create(new ObservableOnSubscribe<List<MovieReview>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<MovieReview>> emitter) {
                final Call<MovieReviewPage> call = apiService.getListOfReviews(
                        movieId,
                        BuildConfig.MOVIES_API_KEY);

                emitter.onNext(fetchMovieReviews(call));
            }
        });
    }

    private void addMoviesToList(
            final Call<MoviePage> call,
            @Sorting final int wayToOrder) {

        final MoviePage page;
        try {
            page = call.execute().body();
            if (page != null && page.getMovies() != null) {
                for (Movie movie : page.getMovies()) {
                    movie.setOrderType(wayToOrder);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Video> fetchMovieTrailers(final Call<MovieVideos> call) {
        final List<Video> trailers = new ArrayList<>();
        try {
            final MovieVideos page = call.execute().body();
            if (page != null && page.getResults() != null) {
                trailers.addAll(page.getResults());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    private List<MovieReview> fetchMovieReviews(final Call<MovieReviewPage> call) {
        final List<MovieReview> reviews = new ArrayList<>();
        try {
            final MovieReviewPage page = call.execute().body();
            if (page != null) {
                reviews.addAll(page.getReviews());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

}
