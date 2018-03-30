package com.udacity.popmoviesnd.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.udacity.popmoviesnd.app.util.MovieMapper;
import com.udacity.popmoviesnd.data.local.MovieContract.MovieEntry;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Sorting;
import com.udacity.popmoviesnd.domain.repository.LocalMovieGateway;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public final class LocalMovieGatewayImp implements LocalMovieGateway {

    private final ContentResolver contentResolver;

    public LocalMovieGatewayImp(final Context context) {
        contentResolver = context.getContentResolver();
    }

    @Override
    public Observable<List<Movie>> obtainMovies(@Sorting final int criteria) {
        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Movie>> emitter) {
                // TODO Add sorting criteria to retrieve only the expected movies
                final List<Movie> movies = obtainMoviesFromDb();
                if (movies.isEmpty()) {
                    emitter.onComplete();
                } else {
                    emitter.onNext(movies);
                }
            }
        });
    }

    @Override
    public void persist(final List<Movie> moviesToPersist) {
        if (moviesToPersist != null && moviesToPersist.size() > 0) {
            for (ContentValues movie : MovieMapper.mapToCV(moviesToPersist)) {
                if (movieIDInDB(movie) > 0) {
                    // Remove the favorite field to keep the existing value
                    movie.remove(MovieEntry.COLUMN_FAVORITE);
                    contentResolver.update(
                            MovieEntry.CONTENT_URI,
                            movie,
                            MovieEntry.COLUMN_TITLE + "= ?",
                            new String[]{movie.getAsString(MovieEntry.COLUMN_TITLE)});
                } else {
                    contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, movie);
                }
            }
        }
    }

    @Override
    public int update(final Movie movieToUpdate) {
        final ContentValues movieCV = MovieMapper.mapToCV(movieToUpdate);

        if (movieIDInDB(movieCV) > 0) {
            return contentResolver.update(
                    MovieEntry.CONTENT_URI,
                    movieCV,
                    MovieEntry.COLUMN_TITLE + "= ?",
                    new String[]{movieCV.getAsString(MovieEntry.COLUMN_TITLE)});
        }
        return 0;
    }

    @Override
    public void delete(final Movie movieToDelete) {
        final ContentValues movieCV = MovieMapper.mapToCV(movieToDelete);
        long movieId = movieIDInDB(movieCV);
        if (movieId > 0) {
            contentResolver.delete(
                    MovieEntry.CONTENT_URI,
                    MovieEntry.COLUMN_TITLE + "= ?",
                    new String[]{movieCV.getAsString(MovieEntry.COLUMN_TITLE)});
        }
    }

    private List<Movie> obtainMoviesFromDb() {
        final List<Movie> movieList = new ArrayList<>();
        final Cursor cursor = contentResolver.query(MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            movieList.addAll(MovieMapper.mapToListOfMovies(cursor));
            cursor.close();
        }
        return movieList;
    }

    private long movieIDInDB(final ContentValues movie) {
        long id = -1;
        final String[] projection = new String[]{
                MovieEntry._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_ORDER_TYPE};
        final String selection = MovieEntry.COLUMN_TITLE + " = ? AND " +
                MovieEntry.COLUMN_ORDER_TYPE + " = ?";
        final String[] selectionArgs = new String[]{
                movie.getAsString(MovieEntry.COLUMN_TITLE),
                movie.getAsString(MovieEntry.COLUMN_ORDER_TYPE)};

        final Cursor cursor = contentResolver.query(MovieEntry.CONTENT_URI,
                projection, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));
            }
            cursor.close();
        }
        return id;
    }

}
