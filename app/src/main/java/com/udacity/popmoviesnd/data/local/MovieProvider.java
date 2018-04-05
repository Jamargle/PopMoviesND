package com.udacity.popmoviesnd.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.popmoviesnd.domain.model.Sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MovieProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_ORDER_BY_POPULAR = 101;
    public static final int MOVIES_ORDER_BY_RATING = 102;
    public static final int MOVIES_FAVORITES = 103;
    public static final int MOVIE_BY_ID = 104;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.ORDER_BY_POPULAR,
                MOVIES_ORDER_BY_POPULAR);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.ORDER_BY_TOPRATED,
                MOVIES_ORDER_BY_RATING);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.FAVORITE_MOVIES,
                MOVIES_FAVORITES);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/*", MOVIE_BY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull final Uri uri,
            final String[] projection,
            String selection,
            final String[] selectionArgs,
            final String sortOrder) {

        Cursor cursorToRet;
        switch (sUriMatcher.match(uri)) {
            case MOVIES: {
                cursorToRet = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case MOVIES_ORDER_BY_POPULAR: {
                cursorToRet = getMoviesOrderedByPopular(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case MOVIES_ORDER_BY_RATING: {
                cursorToRet = getMoviesOrderedByRating(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case MOVIES_FAVORITES: {
                cursorToRet = getFavoriteMovies(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case MOVIE_BY_ID: {
                selection = MovieContract.MovieEntry._ID + " = '" + ContentUris.parseId(uri) + "'";
                cursorToRet = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (cursorToRet != null && getContext() != null) {
            cursorToRet.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursorToRet;
    }

    private Cursor getMoviesOrderedByPopular(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getMoviesWithOrder(projection, selection, selectionArgs, sortOrder, String.valueOf(Sorting.POPULAR));
    }

    private Cursor getMoviesOrderedByRating(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getMoviesWithOrder(projection, selection, selectionArgs, sortOrder, String.valueOf(Sorting.TOP_RATED));
    }

    private Cursor getMoviesWithOrder(String[] projection, String selection, String[] selectionArgs, String sortOrder, String typeOfOrder) {
        selection = addSelectionField(selection, MovieContract.MovieEntry.COLUMN_ORDER_TYPE + " = ?");
        selectionArgs = addSelectionArgumentsField(selectionArgs, typeOfOrder);
        return mOpenHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getFavoriteMovies(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        selection = addSelectionField(selection, MovieContract.MovieEntry.COLUMN_FAVORITE + " = ?");
        selectionArgs = addSelectionArgumentsField(selectionArgs, String.valueOf(1));

        return mOpenHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private String addSelectionField(String oldSelection, String fieldToAdd) {
        if (oldSelection == null || oldSelection.isEmpty()) {
            return fieldToAdd;
        } else {
            return oldSelection + " AND " + fieldToAdd;
        }
    }

    private String[] addSelectionArgumentsField(String[] oldSelectionArg, String selectionArgToAdd) {
        if (oldSelectionArg == null || oldSelectionArg.length == 0) {
            return new String[]{selectionArgToAdd};
        } else {
            final List<String> allArguments = new ArrayList<>();
            Collections.addAll(allArguments, oldSelectionArg);
            allArguments.add(selectionArgToAdd);
            return ((String[]) allArguments.toArray());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES_ORDER_BY_POPULAR:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES_ORDER_BY_RATING:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES_FAVORITES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_BY_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(
            @NonNull final Uri uri,
            final ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES: {
                long movieId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (movieId > 0) {
                    returnUri = MovieContract.MovieEntry.buildMovieUri(movieId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int bulkInsert(
            @NonNull final Uri uri,
            @NonNull final ContentValues[] values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long movieId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (movieId != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return returnCount;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(
            @NonNull final Uri uri,
            String selection,
            final String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            final ContentValues values,
            final String selection,
            final String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
