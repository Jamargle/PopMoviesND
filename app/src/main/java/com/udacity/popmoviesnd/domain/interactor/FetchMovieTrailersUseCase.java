package com.udacity.popmoviesnd.domain.interactor;

import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.util.List;

import io.reactivex.Observable;

public final class FetchMovieTrailersUseCase extends UseCase<Integer, List<Video>> {

    private final NetworkMovieGateway movieNetworkGateway;

    public FetchMovieTrailersUseCase(
            final NetworkMovieGateway movieNetworkGateway,
            final ThreadExecutor threadExecutor,
            final PostExecutionThread postExecutionThread) {

        super(threadExecutor, postExecutionThread);
        this.movieNetworkGateway = movieNetworkGateway;
    }

    @Override
    protected Observable<List<Video>> buildUseCaseObservable(@Nullable final Integer params) {
        if (params != null) {
            return movieNetworkGateway.obtainTrailers(params);
        } else {
            return Observable.error(new RuntimeException());
        }
    }

}
