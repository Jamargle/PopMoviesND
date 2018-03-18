package com.udacity.popmoviesnd.presentation.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseActivity;

public final class DetailActivity extends BaseActivity
        implements MovieDetailFragment.Callback,
        DetailActivityPresenter.DetailActivityView {

    private static final String MOVIE_TO_SHOW = "param:movie_to_show";

    private DetailActivityPresenter presenter;

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

    @NonNull
    @Override
    protected DetailActivityPresenter getPresenter() {
        if (presenter == null) {
            presenter = new DetailActivityPresenterImp();
        }
        return presenter;
    }

}
