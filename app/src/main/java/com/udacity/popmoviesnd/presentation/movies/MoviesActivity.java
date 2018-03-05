package com.udacity.popmoviesnd.presentation.movies;

import android.os.Bundle;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.presentation.BaseActivity;

public class MoviesActivity extends BaseActivity
        implements MoviesPresenter.MoviesView {

    private MoviesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    @Override
    protected MoviesPresenter getPresenter() {
        return presenter;
    }

}
