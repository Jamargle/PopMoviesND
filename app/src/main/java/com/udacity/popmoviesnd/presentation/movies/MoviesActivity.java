package com.udacity.popmoviesnd.presentation.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.popmoviesnd.R;
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (R.id.action_settings == item.getItemId()) {
            startSettingsActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    protected MoviesActivityPresenter getPresenter() {
        if (presenter == null) {
            presenter = new MoviesActivityPresenterImp();
        }
        return presenter;
    }

    private void startSettingsActivity() {
    }

}
