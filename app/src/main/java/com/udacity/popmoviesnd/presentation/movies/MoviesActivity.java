package com.udacity.popmoviesnd.presentation.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseActivity;

public final class MoviesActivity extends BaseActivity
        implements MovieListFragment.Callback,
        MoviesActivityPresenter.MoviesActivityView {

    private MoviesActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    @NonNull
    @Override
    protected MoviesActivityPresenter getPresenter() {
        if (presenter == null) {
            presenter = new MoviesActivityPresenterImp();
        }
        return presenter;
    }

    @Override
    public void onItemSelected(final Movie movie) {
        // TODO Implement when detail activity is ready
    }

}
