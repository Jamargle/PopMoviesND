package com.udacity.popmoviesnd.presentation.trailer;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Video;

public final class TrailerActivity extends AppCompatActivity
        implements TrailerListFragment.Callback {

    private static final String MOVIE = "param:movie_with_trailers";

    public static Bundle newBundle(final Movie movie) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movie);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.trailer_list_fragment);
        ((TrailerListFragment) fragment).setMovieWithTrailers((Movie) getIntent().getParcelableExtra(MOVIE));
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

    @Override
    public void onTrailerClicked(final Video trailer) {
        final Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.youtube_base_url, trailer.getUrlKey())));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.need_app_to_open_link, Toast.LENGTH_SHORT).show();
        }
    }

    private void openDeviceSettings() {
        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

}
