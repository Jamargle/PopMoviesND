package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.app.util.DateUtils;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

public final class MovieDetailFragmentPresenterImp extends BasePresenterImpl<MovieDetailFragmentPresenter.MovieDetailFragmentView>
        implements MovieDetailFragmentPresenter {

    @Override
    public void loadMovieDetails(final Movie movie) {
        final MovieDetailFragmentView view = getView();
        if (view != null) {
            if (movie.getOriginalTitle() != null) {
                view.setTitle(movie.getOriginalTitle());
            }
            if (movie.getOverview() != null) {
                view.setOverview(movie.getOverview());
            }
            view.setReleaseYear(getReleaseYear(movie.getReleaseDate()));
            view.setVoteAverage(getVoteAverage(movie.getVoteAverage()));

            if (movie.getThumbnailPosterPath() != null) {
                if (movie.getThumbnailPosterPath().contains(BuildConfig.BASE_IMAGE_URL)) {
                    view.setMovieImage(movie.getThumbnailPosterPath());
                } else {
                    view.setMovieImage(BuildConfig.BASE_IMAGE_URL + BuildConfig.IMAGE_MEDIUM_SIZE_URL + movie.getThumbnailPosterPath());
                }
            }
        }
    }

    private String getReleaseYear(final String releaseDate) {
        if (releaseDate == null) {
            return "";
        }
        final Integer year = DateUtils.getYear(releaseDate);
        if (year != null) {
            return String.valueOf(year);
        } else {
            return "";
        }
    }

    private String getVoteAverage(final float voteAverage) {
        return String.valueOf(voteAverage) + " / 10";
    }

}
