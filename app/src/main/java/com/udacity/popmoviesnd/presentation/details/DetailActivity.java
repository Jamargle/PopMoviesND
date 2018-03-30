package com.udacity.popmoviesnd.presentation.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;

public final class DetailActivity extends AppCompatActivity
        implements MovieDetailFragment.Callback {

    private static final String MOVIE_TO_SHOW = "param:movie_to_show";

    public static Bundle newBundle(final Movie movieToShow) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_TO_SHOW, movieToShow);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.movie_details_fragment);
        ((MovieDetailFragment) fragment).setMovieToShow((Movie) getIntent().getParcelableExtra(MOVIE_TO_SHOW));
    }

    @Override
    public void onUpdateMovieError() {
        Toast.makeText(this, getString(R.string.error_while_setting_movie_as_favorite), Toast.LENGTH_SHORT).show();
    }
}
