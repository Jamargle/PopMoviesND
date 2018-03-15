package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenter;

public interface MovieDetailFragmentPresenter extends BasePresenter<MovieDetailFragmentPresenter.MovieDetailFragmentView> {

    void initMovieDetailViews(Movie movie);

    interface MovieDetailFragmentView extends BasePresenter.BaseView {

    }

}
