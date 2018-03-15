package com.udacity.popmoviesnd.presentation.movies;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.util.SharedPreferencesHandler;
import com.udacity.popmoviesnd.app.util.StringProvider;
import com.udacity.popmoviesnd.domain.interactor.DefaultObserver;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Sorting;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

public final class MovieListFragmentPresenterImp extends BasePresenterImpl<MovieListFragmentPresenter.MovieListFragmentView>
        implements MovieListFragmentPresenter {

    private final SharedPreferencesHandler sharedPreferencesHandler;
    private final StringProvider stringProvider;
    private final UseCase<Void, List<Movie>> fetchMoviesUseCase;

    public MovieListFragmentPresenterImp(
            final SharedPreferencesHandler sharedPreferencesHandler,
            final StringProvider stringProvider,
            final UseCase<Void, List<Movie>> fetchMoviesUseCase) {

        this.sharedPreferencesHandler = sharedPreferencesHandler;
        this.stringProvider = stringProvider;
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
                    getView().showNoMoviesToShowScreen();
                } else {
                    getView().updateMoviesToShow(filterMoviesToShow(movies));
                }
            }

            @Override
            public void onAny() {
                super.onAny();
                getView().hideLoading();
            }

        });
    }

    private List<Movie> filterMoviesToShow(final List<Movie> movies) {
        final List<Movie> moviesToShow = new ArrayList<>();
        final int movieOrderSetting = getMovieOrderSetting();

        for (Movie movie : movies) {
            if (movieOrderSetting == movie.getOrderType()) {
                moviesToShow.add(movie);
            }
        }

        return moviesToShow;
    }

    private @Sorting int getMovieOrderSetting() {
        final String wayToOrder = sharedPreferencesHandler.getWayToOrderMovies(
                stringProvider.getString(R.string.pref_sorting_model_key),
                stringProvider.getString(R.string.pref_sort_by_popular));

        if (wayToOrder.equals(stringProvider.getString(R.string.pref_sort_by_popular))) {
            return Sorting.POPULAR;
        } else if (wayToOrder.equals(stringProvider.getString(R.string.pref_sort_by_rating))) {
            return Sorting.TOP_RATED;
        } else {
            return Sorting.POPULAR;
        }
    }

}
