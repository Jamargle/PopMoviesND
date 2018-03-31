package com.udacity.popmoviesnd.presentation.trailer;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.BasePresenter;

import java.util.List;

public interface TrailerListFragmentPresenter
        extends BasePresenter<TrailerListFragmentPresenter.TrailerListFragmentView> {

    void loadTrailers(Movie movie);

    void onNoNetworkConnection();

    interface TrailerListFragmentView extends BasePresenter.BaseView {

        void showLoading();

        void hideLoading();

        void showTrailers(List<Video> videos);

        void showErrorFetchingTrailers();

        void showNoInternetConnectionMessage();

        void showNoTrailersToShow();

    }

}
