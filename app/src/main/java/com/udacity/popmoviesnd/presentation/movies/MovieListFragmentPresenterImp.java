package com.udacity.popmoviesnd.presentation.movies;

import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

public final class MovieListFragmentPresenterImp extends BasePresenterImpl<MovieListFragmentPresenter.MovieListFragmentView>
        implements MovieListFragmentPresenter {

    @Override
    public void refreshMovies() {
        if(getView() != null) {
            getView().showLoading();
        }
    }

}
