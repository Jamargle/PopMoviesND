package com.udacity.popmoviesnd.presentation.details;

import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.domain.interactor.FetchMovieReviewsUseCase;
import com.udacity.popmoviesnd.domain.interactor.FetchMovieTrailersUseCase;
import com.udacity.popmoviesnd.domain.interactor.UpdateMoviesUseCase;
import com.udacity.popmoviesnd.domain.model.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieDetailFragmentPresenterTest {

    private MovieDetailFragmentPresenter presenter;
    private MovieDetailFragmentPresenter.MovieDetailFragmentView view;

    @Before
    public void setUp() {
        view = mock(MovieDetailFragmentPresenter.MovieDetailFragmentView.class);
        presenter = new MovieDetailFragmentPresenterImp(
                mock(UpdateMoviesUseCase.class),
                mock(FetchMovieTrailersUseCase.class),
                mock(FetchMovieReviewsUseCase.class));
        presenter.attachView(view);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(view);
    }

    @Test
    public void loadMovieDetails_showAllGivenDetails() {
        // Given
        final String movieTitle = "some title";
        final String overview = "some overview";
        final String releaseDate = "2017-10-27";
        final String expectedYear = "2017";
        final String path = "12345";
        final String expectedCompletePath = BuildConfig.BASE_IMAGE_URL + BuildConfig.IMAGE_MEDIUM_SIZE_URL + path;
        final float votes = 1.2f;
        final String expectedVotes = "1.2 / 10";

        final Movie movie = new Movie.Builder()
                .originalTitle(movieTitle)
                .overview(overview)
                .releaseDate(releaseDate)
                .thumbnailPosterPath(path)
                .voteAverage(votes)
                .build();

        // When
        presenter.loadMovieDetails(movie);

        // Then
        verify(view).setTitle(movieTitle);
        verify(view).setOverview(overview);
        verify(view).setReleaseYear(expectedYear);
        verify(view).setVoteAverage(expectedVotes);
        verify(view).setMovieImage(expectedCompletePath);
    }

    @Test
    public void loadMovieDetails_showThumbnailCompletePath() {
        // Given
        final String path = BuildConfig.BASE_IMAGE_URL + BuildConfig.IMAGE_MEDIUM_SIZE_URL + "12345";
        final Movie movie = new Movie.Builder()
                .thumbnailPosterPath(path)
                .build();

        // When
        presenter.loadMovieDetails(movie);

        // Then
        verify(view).setMovieImage(path);
        verify(view).setReleaseYear("");
        verify(view).setVoteAverage(anyString());
    }

    @Test
    public void loadMovieDetails_showDetailsWithWrongDate() {
        // Given
        final Movie movie = new Movie.Builder()
                .releaseDate("wrong date")
                .build();

        // When
        presenter.loadMovieDetails(movie);

        // Then
        verify(view).setReleaseYear("");
        verify(view).setVoteAverage(anyString());
    }

}
