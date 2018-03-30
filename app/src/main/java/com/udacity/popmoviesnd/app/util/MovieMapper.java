package com.udacity.popmoviesnd.app.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.udacity.popmoviesnd.data.local.MovieContract.MovieEntry;
import com.udacity.popmoviesnd.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class to translate a Movie to a ContentValues object and viceversa
 */
public final class MovieMapper {

    private MovieMapper() {
    }

    public static ContentValues mapToCV(final Movie movie) {
        final ContentValues movieContentValues = new ContentValues();
        if (movie != null) {
            movieContentValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getMovieApiId());
            movieContentValues.put(MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
            movieContentValues.put(MovieEntry.COLUMN_POSTER, movie.getThumbnailPosterPath());
            movieContentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            movieContentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            movieContentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            movieContentValues.put(MovieEntry.COLUMN_ORDER_TYPE, movie.getOrderType());
            movieContentValues.put(MovieEntry.COLUMN_FAVORITE, movie.getFavorite());
        }
        return movieContentValues;
    }

    public static List<ContentValues> mapToCV(final List<Movie> movieList) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        if (movieList != null) {
            for (Movie movie : movieList) {
                contentValuesList.add(mapToCV(movie));
            }
        }
        return contentValuesList;
    }

    private static Movie mapToMovie(final ContentValues movieValues) {
        if (movieValues != null) {
            return new Movie.Builder()
                    .movieDbId(movieValues.getAsLong(MovieEntry._ID))
                    .movieApiId(movieValues.getAsInteger(MovieEntry.COLUMN_MOVIE_ID))
                    .originalTitle(movieValues.getAsString(MovieEntry.COLUMN_TITLE))
                    .thumbnailPosterPath(movieValues.getAsString(MovieEntry.COLUMN_POSTER))
                    .overview(movieValues.getAsString(MovieEntry.COLUMN_OVERVIEW))
                    .voteAverage(movieValues.getAsFloat(MovieEntry.COLUMN_VOTE_AVERAGE))
                    .releaseDate(movieValues.getAsString(MovieEntry.COLUMN_RELEASE_DATE))
                    .orderType(movieValues.getAsInteger(MovieEntry.COLUMN_ORDER_TYPE))
                    .favorite(movieValues.getAsInteger(MovieEntry.COLUMN_FAVORITE) == 1)
                    .build();
        }
        return null;
    }

    public static List<Movie> mapToMovie(final List<ContentValues> movieValuesList) {
        final List<Movie> movieList = new ArrayList<>();
        if (movieValuesList != null) {
            for (ContentValues movieValues : movieValuesList) {
                movieList.add(mapToMovie(movieValues));
            }
        }
        return movieList;
    }

    public static List<Movie> mapToListOfMovies(final Cursor moviesCursor) {
        final List<Movie> movieList = new ArrayList<>();
        if (moviesCursor != null && moviesCursor.moveToFirst()) {
            Movie movie;
            do {
                movie = mapToMovie(moviesCursor);
                if (movie != null) {
                    movieList.add(movie);
                }
            } while (moviesCursor.moveToNext());
        }
        return movieList;
    }

    private static Movie mapToMovie(final Cursor movieCursor) {
        if (movieCursor != null) {
            return new Movie.Builder()
                    .movieDbId(movieCursor.getLong(movieCursor.getColumnIndex(MovieEntry._ID)))
                    .movieApiId(movieCursor.getInt(movieCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID)))
                    .originalTitle(movieCursor.getString(movieCursor.getColumnIndex(MovieEntry.COLUMN_TITLE)))
                    .thumbnailPosterPath(movieCursor.getString(movieCursor.getColumnIndex(MovieEntry.COLUMN_POSTER)))
                    .overview(movieCursor.getString(movieCursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)))
                    .voteAverage(movieCursor.getFloat(movieCursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)))
                    .releaseDate(movieCursor.getString(movieCursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)))
                    .orderType(movieCursor.getInt(movieCursor.getColumnIndex(MovieEntry.COLUMN_ORDER_TYPE)))
                    .favorite(movieCursor.getInt(movieCursor.getColumnIndex(MovieEntry.COLUMN_FAVORITE)) == 1)
                    .build();
        }
        return null;
    }

}
