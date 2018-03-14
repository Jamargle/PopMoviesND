package com.udacity.popmoviesnd.app.dependencies;

import com.udacity.popmoviesnd.presentation.movies.MovieListFragmentPresenter;
import com.udacity.popmoviesnd.presentation.movies.MovieListFragmentPresenterImp;

/**
 * Helper class to perform presenter creations
 */
public abstract class PresenterFactory {

    private static MovieListFragmentPresenter movieListFragmentPresenterInstance;

    private PresenterFactory() {

    }

    public static MovieListFragmentPresenter makeMovieListFragmentPresenter() {
        if (movieListFragmentPresenterInstance == null) {
            movieListFragmentPresenterInstance = new MovieListFragmentPresenterImp(UseCaseFactory.makeFetchMoviesUseCase());
        }
        return movieListFragmentPresenterInstance;
    }

}
