package com.udacity.popmoviesnd.presentation.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MovieListFragment extends BaseFragment
        implements MovieListFragmentPresenter.MovieListFragmentView,
        MovieListAdapter.OnMovieClickListener {

    @BindView(R.id.movies_grid_recyclerview) RecyclerView movieRecyclerView;
    @BindView(R.id.empty_list) TextView emptyListView;
    @BindView(R.id.movies_loading) ProgressBar loadingView;

    private MovieListFragmentPresenter presenter;
    private Callback callback;
    private MovieListAdapter adapter;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        initAdapter();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initRecyclerView();
        if (hasNetworkConnection()) {
            presenter.refreshMovies();
        } else {
            presenter.onNoNetworkConnection();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull
    @Override
    protected MovieListFragmentPresenter getPresenter() {
        if (presenter == null && getActivity() != null) {
            final WeakReference<Context> contextWeakReference = new WeakReference<>(getActivity().getApplicationContext());
            presenter = PresenterFactory.makeMovieListFragmentPresenter(contextWeakReference);
        }
        return presenter;
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showLoadMoviesError() {
        // TODO Finish showLoadMoviesError implementation
    }

    @Override
    public void hideMoviesError() {
        // TODO Finish hideMoviesError implementation
    }

    @Override
    public void updateMoviesToShow(final List<Movie> movies) {
        if (movieRecyclerView.getVisibility() == View.GONE) {
            movieRecyclerView.setVisibility(View.VISIBLE);
        }
        adapter.updateMovies(movies);
    }

    @Override
    public void showNoMoviesToShowScreen() {
        movieRecyclerView.setVisibility(View.GONE);
        emptyListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetConnectionMessage() {
        if (getActivity() == null) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        callback.openDeviceSettings();
    }

    @Override
    public void onMoviePosterClicked(final Movie movie) {
        startMovieDetailsActivity(movie);
    }

    private void initAdapter() {
        adapter = new MovieListAdapter(getContext(), new ArrayList<Movie>(), this);
    }

    private void initRecyclerView() {
        movieRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), calculateBestNumberOfColumns());
        movieRecyclerView.setLayoutManager(gridLayoutManager);

        movieRecyclerView.setAdapter(adapter);
    }

    private int calculateBestNumberOfColumns() {
        final int defaultNumberOfColumns = 3;
        if (getActivity() != null) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int widthDivider = (int) getResources().getDimension(R.dimen.poster_width);
            final int screenWidth = displayMetrics.widthPixels;
            final int numberOfColumns = screenWidth / widthDivider;
            if (numberOfColumns < defaultNumberOfColumns) {
                return defaultNumberOfColumns;
            }
            return numberOfColumns;
        }
        return defaultNumberOfColumns;
    }

    private boolean hasNetworkConnection() {
        if (getActivity() != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }

    private void startMovieDetailsActivity(final Movie movie) {
        callback.onItemSelected(movie);
    }

    interface Callback {

        void onItemSelected(Movie movie);

        void openDeviceSettings();

    }

}
