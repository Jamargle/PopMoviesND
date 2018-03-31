package com.udacity.popmoviesnd.domain.interactor;

import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.util.List;

import io.reactivex.Observable;

public final class FetchMovieReviewsUseCase extends UseCase<Integer, List<MovieReview>> {

    private final NetworkMovieGateway movieNetworkGateway;

    public FetchMovieReviewsUseCase(
            final NetworkMovieGateway movieNetworkGateway,
            final ThreadExecutor threadExecutor,
            final PostExecutionThread postExecutionThread) {

        super(threadExecutor, postExecutionThread);
        this.movieNetworkGateway = movieNetworkGateway;
    }

    @Override
    protected Observable<List<MovieReview>> buildUseCaseObservable(@Nullable final Integer params) {
        if (params != null) {
            return movieNetworkGateway.obtainReviews(params);
        } else {
            return Observable.error(new RuntimeException());
        }
    }

}
