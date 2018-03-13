package com.udacity.popmoviesnd.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.udacity.popmoviesnd.domain.model.Sorting.POPULAR;
import static com.udacity.popmoviesnd.domain.model.Sorting.TOP_RATED;

@Retention(RetentionPolicy.SOURCE)
@IntDef({POPULAR, TOP_RATED})
public @interface Sorting {

    int POPULAR = 0;
    int TOP_RATED = 1;

}
