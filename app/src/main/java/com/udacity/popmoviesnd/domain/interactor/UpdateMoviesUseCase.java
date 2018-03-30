package com.udacity.popmoviesnd.domain.interactor;

import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.repository.LocalMovieGateway;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public final class UpdateMoviesUseCase extends UseCase<Movie, Integer> {

    private final LocalMovieGateway movieLocalGateway;

    public UpdateMoviesUseCase(
            final LocalMovieGateway movieLocalGateway,
            final ThreadExecutor threadExecutor,
            final PostExecutionThread postExecutionThread) {

        super(threadExecutor, postExecutionThread);
        this.movieLocalGateway = movieLocalGateway;
    }

    @Override
    protected Observable<Integer> buildUseCaseObservable(@Nullable final Movie params) {
        if (params != null) {
            return Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(final ObservableEmitter<Integer> emitter) {
                    final int updatedMovies = movieLocalGateway.update(params);
                    if (updatedMovies > 0) {
                        emitter.onNext(updatedMovies);
                    } else {
                        emitter.onComplete();
                    }
                }
            });
        } else {
            return Observable.error(new RuntimeException());
        }
    }

}
