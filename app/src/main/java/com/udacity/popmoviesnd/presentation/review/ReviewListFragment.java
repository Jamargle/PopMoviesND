package com.udacity.popmoviesnd.presentation.review;

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
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class ReviewListFragment extends BaseFragment
        implements ReviewListFragmentPresenter.ReviewListFragmentView,
        ReviewAdapter.OnReviewClickListener {

    @BindView(R.id.review_list) RecyclerView reviewsListView;
    @BindView(R.id.empty_list) TextView emptyListView;
    @BindView(R.id.reviews_loading) ProgressBar loadingView;

    private ReviewListFragmentPresenter presenter;
    private Callback callback;
    private ReviewAdapter adapter;

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

        final View rootView = inflater.inflate(R.layout.fragment_review_list, container, false);
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
    protected ReviewListFragmentPresenter getPresenter() {
        if (presenter == null && getActivity() != null) {
            presenter = PresenterFactory.makeReviewListFragmentPresenter();
        }
        return presenter;
    }

    public void setMovieWithReviews(final Movie movie) {
        if (hasNetworkConnection()) {
            presenter.loadReviews(movie);
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
    public void showReviews(final List<MovieReview> reviews) {
        adapter.setTrailersToShow(reviews);
    }

    @Override
    public void showErrorFetchingReviews() {
        showNoReviewsToShow();
    }

    @Override
    public void showNoInternetConnectionMessage() {
        callback.onShowNoInternetConnectionMessage();
    }

    @Override
    public void showNoReviewsToShow() {
        reviewsListView.setVisibility(View.GONE);
        emptyListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReviewClicked(final MovieReview review) {
        // Nothing to do so far
    }

    private void initRecyclerView() {
        reviewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsListView.setHasFixedSize(true);
        adapter = new ReviewAdapter(new ArrayList<MovieReview>(), this);
        reviewsListView.setAdapter(adapter);
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

    }

}
