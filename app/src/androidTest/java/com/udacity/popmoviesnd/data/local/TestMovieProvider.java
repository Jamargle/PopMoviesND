package com.udacity.popmoviesnd.data.local;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.popmoviesnd.TestUtilities;
import com.udacity.popmoviesnd.data.local.MovieContract.MovieEntry;

public class TestMovieProvider extends AndroidTestCase {

    private static final String LOG_TAG = TestMovieProvider.class.getSimpleName();
    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    private static ContentValues[] createBulkInsertMovieValues() {
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, TestUtilities.TEST_MOVIE_API_ID + i);
            movieValues.put(MovieEntry.COLUMN_TITLE, TestUtilities.TEST_MOVIE_TITLE + " " + (i + 1));
            movieValues.put(MovieEntry.COLUMN_POSTER, TestUtilities.TEST_MOVIE_POSTER);
            movieValues.put(MovieEntry.COLUMN_OVERVIEW, TestUtilities.TEST_MOVIE_OVERVIEW);
            movieValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, TestUtilities.TEST_MOVIE_VOTE_AVERAGE);
            movieValues.put(MovieEntry.COLUMN_RELEASE_DATE, TestUtilities.TEST_MOVIE_RELEASE);
            movieValues.put(MovieEntry.COLUMN_ORDER_TYPE, TestUtilities.TEST_MOVIE_ORDER_TYPE);
            movieValues.put(MovieEntry.COLUMN_FAVORITE, TestUtilities.TEST_MOVIE_FAVORITE);
            returnContentValues[i] = movieValues;
        }
        return returnContentValues;
    }

    private void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            assertEquals("Error: Records not deleted from Movie table during delete", 0, cursor.getCount());
            cursor.close();
        }
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // MovieProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    public void testGetType() {
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.josecognizant.popmovies.app/movie
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_TYPE, type);

        // content://com.josecognizant.popmovies.app/movie/popular
        type = mContext.getContentResolver().getType(MovieEntry.buildPopularMoviesUri());
        // vnd.android.cursor.dir/com.josecognizant.popmovies.app/movie/popular
        assertEquals("Error: the MovieEntry CONTENT_URI for popular movies should return " +
                "MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE, type);

        // content://com.josecognizant.popmovies.app/movie/top_rated
        type = mContext.getContentResolver().getType(MovieEntry.buildTopRatedMoviesUri());
        // vnd.android.cursor.dir/com.josecognizant.popmovies.app/movie/top_rated
        assertEquals("Error: the MovieEntry CONTENT_URI for top rated movies should return " +
                "MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE, type);

        // content://com.josecognizant.popmovies.app/movie/favorite
        type = mContext.getContentResolver().getType(MovieEntry.buildFavoriteMoviesUri());
        // vnd.android.cursor.dir/com.josecognizant.popmovies.app/movie/favorite
        assertEquals("Error: the MovieEntry CONTENT_URI for favorite movies should return " +
                "MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE, type);

        // content://com.josecognizant.popmovies.app/movie/123
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieUri(123));
        // vnd.android.cursor.item/com.josecognizant.popmovies.app/movie/123
        assertEquals("Error: the MovieEntry CONTENT_URI for movie by ID should return " +
                "MovieEntry.CONTENT_ITEM_TYPE", MovieEntry.CONTENT_ITEM_TYPE, type);
    }

    public void testBasicMovieQuery() {
        // insert our test records into the database
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Fantastic.  Now that we have a location, add some weather!
        ContentValues movieValues = TestUtilities.createMovieValues();

        long movieRowId = db.insert(MovieEntry.TABLE_NAME, null, movieValues);
        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor movieCursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (movieCursor != null) {
            // Make sure we get the correct cursor out of the database
            TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, movieValues);
            movieCursor.close();
        }
    }

    public void testInsertReadProvider() {
        ContentValues testValues = TestUtilities.createMovieValues();

        // Register a content observer for our insert.  This time, directly with the content resolver
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long movieRowId = ContentUris.parseId(movieUri);

        // Verify we got a row back.
        assertTrue(movieRowId != -1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating MovieEntry.",
                cursor, testValues);
    }

    public void testUpdateMovie() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestUtilities.createMovieValues();

        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(movieUri);

        // Verify we got a row back.
        assertTrue(movieRowId != -1);
        Log.d(LOG_TAG, "New row id: " + movieRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MovieEntry._ID, movieRowId);
        updatedValues.put(MovieEntry.COLUMN_TITLE, "ANOTHER TITLE");

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor movieCursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        if (movieCursor != null) {
            movieCursor.registerContentObserver(tco);
        }

        int count = mContext.getContentResolver().update(
                MovieEntry.CONTENT_URI,
                updatedValues,
                MovieEntry._ID + "= ?",
                new String[]{Long.toString(movieRowId)}
        );
        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        if (movieCursor != null) {
            movieCursor.unregisterContentObserver(tco);
            movieCursor.close();
        }

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,   // projection
                MovieEntry._ID + " = " + movieRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        TestUtilities.validateCursor("testUpdateMovie.  Error validating movie entry update.",
                cursor, updatedValues);

        if (cursor != null) {
            cursor.close();
        }
    }

    public void testDeleteRecords() {
        testInsertReadProvider();

        // Register a content observer for our location delete.
        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, movieObserver);

        deleteAllRecordsFromProvider();
        movieObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieObserver);
    }

    public void testBulkInsert() {
        ContentValues[] bulkInsertContentValues = createBulkInsertMovieValues();

        // Register a content observer for our bulk insert.
        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, movieObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, bulkInsertContentValues);

        movieObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(movieObserver);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null
        );

        if (cursor != null) {
            // we should have as many records in the database as we've inserted
            assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

            // and let's make sure they match the ones we created
            cursor.moveToFirst();
            for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
                TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating MovieEntry " + i,
                        cursor, bulkInsertContentValues[i]);
            }
            cursor.close();
        }
    }
}
