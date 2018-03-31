package com.udacity.popmoviesnd.presentation.trailer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class TrailerListFragment extends BaseFragment
        implements TrailerListFragmentPresenter.TrailerListFragmentView,
        TrailerAdapter.OnTrailerClickListener {

    @BindView(R.id.trailer_list) RecyclerView trailersListView;
    @BindView(R.id.empty_list) TextView emptyListView;
    @BindView(R.id.trailers_loading) ProgressBar loadingView;

    private TrailerListFragmentPresenter presenter;
    private Callback callback;
    private TrailerAdapter adapter;

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

        final View rootView = inflater.inflate(R.layout.fragment_trailer_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecyclerView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull
    @Override
    protected TrailerListFragmentPresenter getPresenter() {
        if (presenter == null && getActivity() != null) {
            presenter = PresenterFactory.makeTrailerListFragmentPresenter();
        }
        return presenter;
    }

    public void setMovieWithTrailers(final Movie movie) {
        if (hasNetworkConnection()) {
            presenter.loadTrailers(movie);
        } else {
            presenter.onNoNetworkConnection();
        }
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
    public void showTrailers(final List<Video> videos) {
        adapter.setTrailersToShow(videos);
    }

    @Override
    public void showErrorFetchingTrailers() {
        showNoTrailersToShow();
    }

    @Override
    public void showNoInternetConnectionMessage() {
        callback.onShowNoInternetConnectionMessage();
    }

    @Override
    public void showNoTrailersToShow() {
        trailersListView.setVisibility(View.GONE);
        emptyListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTrailerClicked(final Video trailer) {
        callback.onTrailerClicked(trailer);
    }

    private void initRecyclerView() {
        trailersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trailersListView.setHasFixedSize(true);
        adapter = new TrailerAdapter(new ArrayList<Video>(), this);
        trailersListView.setAdapter(adapter);
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

    interface Callback {

        void onShowNoInternetConnectionMessage();

        void onTrailerClicked(Video trailer);

    }

}
