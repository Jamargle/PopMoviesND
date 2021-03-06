package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BasePresenter;

public interface MovieDetailFragmentPresenter extends BasePresenter<MovieDetailFragmentPresenter.MovieDetailFragmentView> {

    void loadMovieDetails(Movie movie);

    void onChangeFavoriteState();

    void onTrailerTitleClicked();

    void onReviewTitleClicked();

    interface MovieDetailFragmentView extends BasePresenter.BaseView {

        void setTitle(String originalTitle);

        void setOverview(String overview);

        void setReleaseYear(String releaseDate);

        void setVoteAverage(String voteAverage);

        void setMovieImage(String posterPath);

        void setFavoriteButtonText(boolean isFavorite);

        void onUpdateMovieError();

        void proceedToShowTrailers(Movie movie);

        void proceedToShowReviews(Movie movie);

    }

}
