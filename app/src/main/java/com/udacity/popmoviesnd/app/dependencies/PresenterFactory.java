package com.udacity.popmoviesnd.app.dependencies;

import android.content.Context;

import com.udacity.popmoviesnd.app.util.SharedPreferencesHandler;
import com.udacity.popmoviesnd.app.util.StringProvider;
import com.udacity.popmoviesnd.presentation.details.MovieDetailFragmentPresenter;
import com.udacity.popmoviesnd.presentation.details.MovieDetailFragmentPresenterImp;
import com.udacity.popmoviesnd.presentation.movies.MovieListFragmentPresenter;
import com.udacity.popmoviesnd.presentation.movies.MovieListFragmentPresenterImp;

import java.lang.ref.WeakReference;

/**
 * Helper class to perform presenter creations
 */
public abstract class PresenterFactory {

    private static SharedPreferencesHandler sharedPreferencesHandler;
    private static StringProvider stringProvider;
    private static MovieListFragmentPresenter movieListFragmentPresenterInstance;
    private static MovieDetailFragmentPresenter movieDetailFragmentPresenterInstance;

    private PresenterFactory() {

    }

    public static MovieListFragmentPresenter makeMovieListFragmentPresenter(final WeakReference<Context> context) {
        if (movieListFragmentPresenterInstance == null) {
            movieListFragmentPresenterInstance = new MovieListFragmentPresenterImp(
                    getSharedPreferencesInstance(context),
                    getStringProviderInstance(context),
                    UseCaseFactory.makeFetchMoviesUseCase(context.get()));
        }
        return movieListFragmentPresenterInstance;
    }

    public static MovieDetailFragmentPresenter makeMovieDetailFragmentPresenter(final WeakReference<Context> contextWeakReference) {
        if (movieDetailFragmentPresenterInstance == null) {
            movieDetailFragmentPresenterInstance = new MovieDetailFragmentPresenterImp();
        }
        return movieDetailFragmentPresenterInstance;
    }

    private static SharedPreferencesHandler getSharedPreferencesInstance(final WeakReference<Context> context) {
        if (sharedPreferencesHandler == null) {
            sharedPreferencesHandler = new SharedPreferencesHandler(context.get());
        }
        return sharedPreferencesHandler;
    }

    private static StringProvider getStringProviderInstance(final WeakReference<Context> context) {
        if (stringProvider == null) {
            stringProvider = new StringProvider(context);
        }
        return stringProvider;
    }

}
