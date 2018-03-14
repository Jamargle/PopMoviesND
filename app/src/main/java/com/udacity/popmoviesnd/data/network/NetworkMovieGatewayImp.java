package com.udacity.popmoviesnd.data.network;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MoviePage;
import com.udacity.popmoviesnd.domain.model.Sorting;
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
    public Observable<List<Movie>> obtainMovies() {
        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> emitter) throws Exception {
                Call<MoviePage> call = apiService.getListOfPopularMovies(BuildConfig.MOVIES_API_KEY);
                addMoviesToList(call, Sorting.POPULAR);

                call = apiService.getListOfTopRatedMovies(BuildConfig.MOVIES_API_KEY);
                addMoviesToList(call, Sorting.TOP_RATED);

                emitter.onNext(movies);
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

}
