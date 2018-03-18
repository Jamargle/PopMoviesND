package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

public final class MovieDetailFragmentPresenterImp extends BasePresenterImpl<MovieDetailFragmentPresenter.MovieDetailFragmentView>
        implements MovieDetailFragmentPresenter {

    @Override
    public void loadMovieDetails(final Movie movie) {
        final MovieDetailFragmentView view = getView();
        if (view != null) {
            view.setTitle(movie.getOriginalTitle());
            view.setOverview(movie.getOverview());
            view.setReleaseYear(movie.getReleaseDate());
            view.setVoteAverage(movie.getVoteAverage());
            final String completePath;
            if (movie.getThumbnailPosterPath().contains(BuildConfig.BASE_IMAGE_URL)) {
                completePath = movie.getThumbnailPosterPath();
            } else {
                completePath = BuildConfig.BASE_IMAGE_URL + BuildConfig.IMAGE_MEDIUM_SIZE_URL + movie.getThumbnailPosterPath();
            }
            view.setMovieImage(completePath);
        }
    }

}
