package com.udacity.popmoviesnd.presentation.review;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.presentation.BasePresenter;

import java.util.List;

public interface ReviewListFragmentPresenter
        extends BasePresenter<ReviewListFragmentPresenter.ReviewListFragmentView> {

    void loadReviews(Movie movie);

    void onNoNetworkConnection();

    interface ReviewListFragmentView extends BasePresenter.BaseView {

        void showLoading();

        void hideLoading();

        void showReviews(List<MovieReview> reviews);

        void showErrorFetchingReviews();

        void showNoInternetConnectionMessage();

        void showNoReviewsToShow();

    }

}
