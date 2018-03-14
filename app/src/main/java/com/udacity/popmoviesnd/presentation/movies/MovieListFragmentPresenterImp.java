package com.udacity.popmoviesnd.presentation.movies;

import com.udacity.popmoviesnd.domain.interactor.DefaultObserver;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

import java.util.List;

public final class MovieListFragmentPresenterImp extends BasePresenterImpl<MovieListFragmentPresenter.MovieListFragmentView>
        implements MovieListFragmentPresenter {

    private final UseCase<Void, List<Movie>> fetchMoviesUseCase;

    public MovieListFragmentPresenterImp(final UseCase<Void, List<Movie>> fetchMoviesUseCase) {
        this.fetchMoviesUseCase = fetchMoviesUseCase;
    }

    @Override
    public void refreshMovies() {
        if (getView() != null) {
            getView().showLoading();
        }

        fetchMoviesUseCase.execute(null, new DefaultObserver<List<Movie>>(getView()) {

            @Override
            public void processOnNext(final List<Movie> movies) {
                super.processOnNext(movies);
                if (movies.isEmpty()) {
                    getView().showLoadMoviesError();
                } else {
                    getView().updateMoviesToShow(movies);
                }
            }

            @Override
            public void onAny() {
                super.onAny();
                getView().hideLoading();
            }

        });
    }

}
