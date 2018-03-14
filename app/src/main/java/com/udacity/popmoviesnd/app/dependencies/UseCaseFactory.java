package com.udacity.popmoviesnd.app.dependencies;

import com.udacity.popmoviesnd.data.JobExecutor;
import com.udacity.popmoviesnd.domain.interactor.FetchMoviesUseCase;
import com.udacity.popmoviesnd.domain.interactor.PostExecutionThread;
import com.udacity.popmoviesnd.domain.interactor.ThreadExecutor;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.UiThread;

import java.util.List;

public abstract class UseCaseFactory {

    private static ThreadExecutor threadExecutorInstance;
    private static PostExecutionThread postExecutionThreadInstance;

    private UseCaseFactory() {
    }

    public static UseCase<Void, List<Movie>> makeFetchMoviesUseCase() {
        initThreadingInstances();
        return new FetchMoviesUseCase(
                GatewayFactory.makeNetworkMovieGateway(),
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
