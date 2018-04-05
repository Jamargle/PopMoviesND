package com.udacity.popmoviesnd.presentation.movies;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenter;

import java.util.List;

public interface MovieListFragmentPresenter extends BasePresenter<MovieListFragmentPresenter.MovieListFragmentView> {

    void refreshMovies();

    void onNoNetworkConnection();

    interface MovieListFragmentView extends BasePresenter.BaseView {

        void showLoading();

        void hideLoading();

        void updateMoviesToShow(List<Movie> movies);

        void showNoMoviesToShowScreen();

        void showNoInternetConnectionMessage();

    }

}
