package com.udacity.popmoviesnd.app.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtils {

    public static final String API_FORMAT = "yyyy-MM-dd";

    private DateUtils() {
    }

    /**
     * Extracts the year in a Date with the API format 'yyyy-MM-dd'
     *
     * @param date String to transform in the API format 'yyyy-MM-dd'
     * @return A integer object containing the representation of the year in the given date or null if date is not parsable with the API_FORMAT
     */
    public static Integer getYear(@NonNull final String date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(API_FORMAT, Locale.getDefault());
        try {
            final Date completeDate = simpleDateFormat.parse(date);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(completeDate);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            return null;
        }
    }

}
