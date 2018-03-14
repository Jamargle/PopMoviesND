package com.udacity.popmoviesnd.domain.interactor;

import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.util.List;

import io.reactivex.Observable;

public final class FetchMoviesUseCase extends UseCase<Void, List<Movie>> {

    private final NetworkMovieGateway movieNetworkGateway;

    public FetchMoviesUseCase(
            final NetworkMovieGateway movieNetworkGateway,
            final ThreadExecutor threadExecutor,
            final PostExecutionThread postExecutionThread) {

        super(threadExecutor, postExecutionThread);
        this.movieNetworkGateway = movieNetworkGateway;
    }

    @Override
    protected Observable<List<Movie>> buildUseCaseObservable(@Nullable final Void params) {
        return movieNetworkGateway.obtainMovies();
    }
}
