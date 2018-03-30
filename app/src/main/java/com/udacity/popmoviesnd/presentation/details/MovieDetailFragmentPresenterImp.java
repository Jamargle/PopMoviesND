package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.app.util.DateUtils;
import com.udacity.popmoviesnd.domain.interactor.DefaultObserver;
import com.udacity.popmoviesnd.domain.interactor.UseCase;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenterImpl;

public final class MovieDetailFragmentPresenterImp
        extends BasePresenterImpl<MovieDetailFragmentPresenter.MovieDetailFragmentView>
        implements MovieDetailFragmentPresenter {

    private final UseCase<Movie, Integer> updateMovieUseCase;
    private Movie movie;

    public MovieDetailFragmentPresenterImp(final UseCase<Movie, Integer> updateMovieUseCase) {
        this.updateMovieUseCase = updateMovieUseCase;
    }

    @Override
    public void loadMovieDetails(final Movie movie) {
        this.movie = movie;
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
            view.setFavoriteButtonText(movie.getFavorite());
        }
    }

    @Override
    public void onChangeFavoriteState() {
        movie.setFavorite(!movie.getFavorite());
        updateMovieUseCase.execute(movie, new DefaultObserver<Integer>(getView()) {

            @Override
            public void processOnNext(final Integer moviesUpdated) {
                if (getView() == null) {
                    return;
                }
                if (moviesUpdated > 0) {
                    getView().setFavoriteButtonText(movie.getFavorite());
                } else {
                    getView().onUpdateMovieError();
                }
            }

            @Override
            public void processOnError(final Throwable exception) {
                if (getView() != null) {
                    getView().onUpdateMovieError();
                }
            }

        });
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
