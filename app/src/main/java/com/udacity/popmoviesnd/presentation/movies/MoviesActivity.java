package com.udacity.popmoviesnd.presentation.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;

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

    @NonNull
    @Override
    protected MoviesPresenter getPresenter() {
        return presenter;
    }

}
