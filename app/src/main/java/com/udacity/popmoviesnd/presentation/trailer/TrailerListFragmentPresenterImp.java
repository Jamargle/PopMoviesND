package com.udacity.popmoviesnd.presentation.trailer;

import com.udacity.popmoviesnd.domain.interactor.DefaultObserver;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

import java.util.List;

public final class TrailerListFragmentPresenterImp
        extends BasePresenterImpl<TrailerListFragmentPresenter.TrailerListFragmentView>
        implements TrailerListFragmentPresenter {

    private UseCase<Integer, List<Video>> fetchMovieTrailersUseCase;

    public TrailerListFragmentPresenterImp(final UseCase<Integer, List<Video>> fetchMovieTrailersUseCase) {
        this.fetchMovieTrailersUseCase = fetchMovieTrailersUseCase;
    }

    @Override
    public void loadTrailers(final Movie movie) {
        loadMovieTrailers(movie.getMovieApiId());
    }

    @Override
    public void onNoNetworkConnection() {
        if (getView() != null) {
            getView().showNoInternetConnectionMessage();
        }
    }

    private void loadMovieTrailers(final long movieApiId) {
        if (getView() != null) {
            getView().showLoading();
        }
        fetchMovieTrailersUseCase.execute((int) movieApiId, new DefaultObserver<List<Video>>() {

            @Override
            public void processOnNext(final List<Video> videos) {
                if (getView() != null) {
                    if (videos.isEmpty()) {
                        getView().showNoTrailersToShow();
                    } else {
                        getView().showTrailers(videos);
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
                    getView().showErrorFetchingTrailers();
                }
            }

        });
    }

}
