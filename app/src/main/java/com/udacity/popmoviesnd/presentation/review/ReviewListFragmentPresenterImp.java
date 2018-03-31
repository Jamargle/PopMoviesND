package com.udacity.popmoviesnd.presentation.review;

import com.udacity.popmoviesnd.domain.interactor.DefaultObserver;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

import java.util.List;

public final class ReviewListFragmentPresenterImp
        extends BasePresenterImpl<ReviewListFragmentPresenter.ReviewListFragmentView>
        implements ReviewListFragmentPresenter {

    private UseCase<Integer, List<MovieReview>> fetchMovieReviewsUseCase;

    public ReviewListFragmentPresenterImp(final UseCase<Integer, List<MovieReview>> fetchMovieReviewsUseCase) {
        this.fetchMovieReviewsUseCase = fetchMovieReviewsUseCase;
    }

    @Override
    public void loadReviews(final Movie movie) {
        loadMovieReviews(movie.getMovieApiId());
    }

    @Override
    public void onNoNetworkConnection() {
        if (getView() != null) {
            getView().showNoInternetConnectionMessage();
        }
    }

    private void loadMovieReviews(final long movieApiId) {
        if (getView() != null) {
            getView().showLoading();
        }
        fetchMovieReviewsUseCase.execute((int) movieApiId, new DefaultObserver<List<MovieReview>>() {

            @Override
            public void processOnNext(final List<MovieReview> reviews) {
                if (getView() != null) {
                    if (reviews.isEmpty()) {
                        getView().showNoReviewsToShow();
                    } else {
                        getView().showReviews(reviews);
                    }
                }
            }

            @Override
            public void onAny() {
                if (getView() != null) {
                    getView().hideLoading();
                }
            }

            @Override
            public void processOnError(final Throwable exception) {
                if (getView() != null) {
                    getView().showErrorFetchingReviews();
                }
            }

        });
    }

}
