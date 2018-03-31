package com.udacity.popmoviesnd.presentation.review;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;

public final class ReviewActivity extends AppCompatActivity
        implements ReviewListFragment.Callback {

    private static final String MOVIE = "param:movie_with_reviews";

    public static Bundle newBundle(final Movie movie) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movie);
        return bundle;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.review_list_fragment);
        ((ReviewListFragment) fragment).setMovieWithReviews((Movie) getIntent().getParcelableExtra(MOVIE));
    }

    @Override
    public void onShowNoInternetConnectionMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_internet_title)
                .setMessage(R.string.enable_internet_message)
                .setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                    public void onClick(
                            final DialogInterface dialog,
                            final int which) {

                        openDeviceSettings();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(
                            final DialogInterface dialog,
                            final int which) {

                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void openDeviceSettings() {
        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

}
