package com.udacity.popmoviesnd.data.local;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.udacity.popmoviesnd.data.local.MovieContract.MovieEntry;

public class TestMovieDbContract extends AndroidTestCase {

    private static final long TEST_MOVIE_ID = 12345;

    public void testBuildMovieWithAppendedId() {
        Uri movieByIdUri = MovieEntry.buildMovieUri(TEST_MOVIE_ID);
        assertNotNull("Error: Null Uri returned.  You must fill-in buildMovieUri in " +
                "MovieContract.", movieByIdUri);
        assertEquals("Error: Movie ID not properly appended to the end of the Uri",
                TEST_MOVIE_ID, Long.parseLong(movieByIdUri.getLastPathSegment()));
        assertEquals("Error: MovieByID Uri doesn't match our expected result",
                movieByIdUri.toString(),
                "content://com.udacity.popmoviesnd.app/movie/" + TEST_MOVIE_ID);
    }

    public void testBuildPopularMoviesUri() {
        Uri popularMoviesUri = MovieEntry.buildPopularMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildPopularMoviesUri in " +
                "MovieContract.", popularMoviesUri);
        assertEquals("Error: Popular movies string not properly appended to the end of the Uri",
                MovieContract.ORDER_BY_POPULAR, popularMoviesUri.getLastPathSegment());
        assertEquals("Error: Popular movies Uri doesn't match our expected result",
                popularMoviesUri.toString(),
                "content://com.udacity.popmoviesnd.app/movie/" + MovieContract.ORDER_BY_POPULAR);
    }

    public void testBuildTopRatedMoviesUri() {
        Uri topRatedMoviesUri = MovieContract.MovieEntry.buildTopRatedMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildTopRatedMoviesUri in " +
                "MovieContract.", topRatedMoviesUri);
        assertEquals("Error: Top rated movies string not properly appended to the end of the Uri",
                MovieContract.ORDER_BY_TOPRATED, topRatedMoviesUri.getLastPathSegment());
        assertEquals("Error: Top rated movies Uri doesn't match our expected result",
                topRatedMoviesUri.toString(),
                "content://com.udacity.popmoviesnd.app/movie/" + MovieContract.ORDER_BY_TOPRATED);
    }

    public void testBuildFavoriteMoviesUri() {
        Uri favoriteMoviesUri = MovieEntry.buildFavoriteMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildFavoriteMoviesUri in " +
                "MovieContract.", favoriteMoviesUri);
        assertEquals("Error: Favorite movies string not properly appended to the end of the Uri",
                MovieContract.FAVORITE_MOVIES, favoriteMoviesUri.getLastPathSegment());
        assertEquals("Error: Favorite movies Uri doesn't match our expected result",
                favoriteMoviesUri.toString(),
                "content://com.udacity.popmoviesnd.app/movie/" + MovieContract.FAVORITE_MOVIES);
    }
}
