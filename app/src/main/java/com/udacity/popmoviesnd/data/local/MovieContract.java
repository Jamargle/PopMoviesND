package com.udacity.popmoviesnd.data.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.popmoviesnd.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movie";
    public static final String ORDER_BY_POPULAR = "popular_ordered";
    public static final String ORDER_BY_TOPRATED = "top_rated_ordered";
    public static final String FAVORITE_MOVIES = "favorite_movies";

    private MovieContract() {
    }

    /**
     * Inner class that defines the table contents of the movie table
     */
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_api_id";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "synopsis";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ORDER_TYPE = "way_to_sort";
        public static final String COLUMN_FAVORITE = "favorite";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPopularMoviesUri() {
            return CONTENT_URI.buildUpon().appendPath(ORDER_BY_POPULAR).build();
        }

        public static Uri buildTopRatedMoviesUri() {
            return CONTENT_URI.buildUpon().appendPath(ORDER_BY_TOPRATED).build();
        }

        public static Uri buildFavoriteMoviesUri() {
            return CONTENT_URI.buildUpon().appendPath(FAVORITE_MOVIES).build();
        }

    }

}
