package com.udacity.popmoviesnd.data.local;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

public class TestUriMatcher extends AndroidTestCase {

    private static final long TEST_ID = 123456;
    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_ORDER_BY_POPULAR = MovieContract.MovieEntry.buildPopularMoviesUri();
    private static final Uri TEST_MOVIE_ORDER_BY_RATING = MovieContract.MovieEntry.buildTopRatedMoviesUri();
    private static final Uri TEST_MOVIE_FAVORITES = MovieContract.MovieEntry.buildFavoriteMoviesUri();
    private static final Uri TEST_MOVIE_BY_ID = MovieContract.MovieEntry.buildMovieUri(TEST_ID);

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIES);
        assertEquals("Error: MOVIES ORDERED BY POPULARITY URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_ORDER_BY_POPULAR), MovieProvider.MOVIES_ORDER_BY_POPULAR);
        assertEquals("Error: MOVIES ORDERED BY RATING URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_ORDER_BY_RATING), MovieProvider.MOVIES_ORDER_BY_RATING);
        assertEquals("Error: FAVORITE MOVIES URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_FAVORITES), MovieProvider.MOVIES_FAVORITES);
        assertEquals("Error: MOVIE BY ID URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_BY_ID), MovieProvider.MOVIE_BY_ID);
    }

}
