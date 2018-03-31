package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.BasePresenter;

import java.util.List;

public interface MovieDetailFragmentPresenter extends BasePresenter<MovieDetailFragmentPresenter.MovieDetailFragmentView> {

    void loadMovieDetails(Movie movie);

    void onChangeFavoriteState();

    interface MovieDetailFragmentView extends BasePresenter.BaseView {

        void setTitle(String originalTitle);

        void setOverview(String overview);

        void setReleaseYear(String releaseDate);

        void setVoteAverage(String voteAverage);

        void setMovieImage(String posterPath);

        void setFavoriteButtonText(boolean isFavorite);

        void onUpdateMovieError();

        void showTrailers(List<Video> videos);

        void showErrorFetchingTrailers();

        void showReviews(List<MovieReview> reviews);

        void showErrorFetchingReviews();

    }

}
