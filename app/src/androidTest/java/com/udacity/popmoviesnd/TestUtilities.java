package com.udacity.popmoviesnd;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.udacity.popmoviesnd.data.local.MovieContract;
import com.udacity.popmoviesnd.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {
    public static final String TEST_MOVIE_API_ID = "12345678";
    public static final String TEST_MOVIE_TITLE = "This is the title of the movie";
    public static final String TEST_MOVIE_POSTER = "/1213fasdfa";
    public static final String TEST_MOVIE_OVERVIEW = "This is the overview of the movie";
    public static final float TEST_MOVIE_VOTE_AVERAGE = 4.5f;
    public static final String TEST_MOVIE_RELEASE = "05/10/1988";
    public static final String TEST_MOVIE_ORDER_TYPE = "popular";
    public static final int TEST_MOVIE_FAVORITE = 0;

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            if (columnName.equals(MovieContract.MovieEntry.COLUMN_FAVORITE)) {
                assertEquals((int) entry.getValue() == 1, valueCursor.getInt(idx) == 1);
            } else {
                assertEquals("Value '" + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
            }
        }
    }

    public static ContentValues createMovieValues() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, TEST_MOVIE_API_ID);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, TEST_MOVIE_TITLE);
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER, TEST_MOVIE_POSTER);
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, TEST_MOVIE_OVERVIEW);
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, TEST_MOVIE_VOTE_AVERAGE);
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, TEST_MOVIE_RELEASE);
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORDER_TYPE, TEST_MOVIE_ORDER_TYPE);
        movieValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, TEST_MOVIE_FAVORITE);

        return movieValues;
    }

    public static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    public static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }
}
