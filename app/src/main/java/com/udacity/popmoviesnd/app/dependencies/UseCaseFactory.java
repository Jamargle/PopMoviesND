package com.udacity.popmoviesnd.app.dependencies;

import android.content.Context;

import com.udacity.popmoviesnd.data.JobExecutor;
import com.udacity.popmoviesnd.domain.interactor.FetchMovieTrailersUseCase;
import com.udacity.popmoviesnd.domain.interactor.FetchMoviesUseCase;
import com.udacity.popmoviesnd.domain.interactor.PostExecutionThread;
import com.udacity.popmoviesnd.domain.interactor.ThreadExecutor;
import com.udacity.popmoviesnd.domain.interactor.UpdateMoviesUseCase;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.UiThread;

import java.util.List;

public abstract class UseCaseFactory {

    private static ThreadExecutor threadExecutorInstance;
    private static PostExecutionThread postExecutionThreadInstance;

    private UseCaseFactory() {
    }

    public static UseCase<Integer, List<Movie>> makeFetchMoviesUseCase(final Context context) {
        initThreadingInstances();
        return new FetchMoviesUseCase(
                GatewayFactory.makeNetworkMovieGateway(),
                GatewayFactory.makeLocalMovieGateway(context),
                threadExecutorInstance,
                postExecutionThreadInstance);
    }

    public static UseCase<Integer, List<Video>> makeFetchMovieTrailersUseCase() {
        initThreadingInstances();
        return new FetchMovieTrailersUseCase(
                GatewayFactory.makeNetworkMovieGateway(),
                threadExecutorInstance,
                postExecutionThreadInstance);
    }

    public static UseCase<Movie, Integer> makeUpdateMoviesUseCase(final Context context) {
        initThreadingInstances();
        return new UpdateMoviesUseCase(
                GatewayFactory.makeLocalMovieGateway(context),
                threadExecutorInstance,
                postExecutionThreadInstance);
    }

    private static void initThreadingInstances() {
        if (threadExecutorInstance == null) {
            threadExecutorInstance = new JobExecutor();
        }
        if (postExecutionThreadInstance == null) {
            postExecutionThreadInstance = new UiThread();
        }
    }

}
