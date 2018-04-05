package com.udacity.popmoviesnd.domain.interactor;

import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.repository.LocalMovieGateway;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class FetchMoviesUseCase extends UseCase<Integer, List<Movie>> {

    private final NetworkMovieGateway movieNetworkGateway;
    private final LocalMovieGateway movieLocalGateway;

    public FetchMoviesUseCase(
            final NetworkMovieGateway movieNetworkGateway,
            final LocalMovieGateway movieLocalGateway,
            final ThreadExecutor threadExecutor,
            final PostExecutionThread postExecutionThread) {

        super(threadExecutor, postExecutionThread);
        this.movieNetworkGateway = movieNetworkGateway;
        this.movieLocalGateway = movieLocalGateway;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable(@Nullable final Integer params) {
        if (params != null) {
            return Observable.concat(
                    movieLocalGateway.obtainMovies(params),
                    movieNetworkGateway.obtainMovies(params)
                            .doAfterNext(new Consumer<List<Movie>>() {
                                @Override
                                public void accept(final List<Movie> movies) {
                                    persistFetchedMovies(movies);
                                }
                            }))
                    .firstElement().toObservable();
        } else {
            throw new RuntimeException();
        }
    }

    private void persistFetchedMovies(final List<Movie> movies) {
        movieLocalGateway.persist(movies);
    }

}
