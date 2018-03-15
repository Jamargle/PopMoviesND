package com.udacity.popmoviesnd.app.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public final class StringProvider {

    private final WeakReference<Context> context;

    public StringProvider(final WeakReference<Context> context) {
        this.context = context;
    }

    public String getString(
            @StringRes final int stringResId,
            @NonNull final Object... formatArgs) {

        if (context.get() != null) {
            return context.get().getString(stringResId, Arrays.copyOf(formatArgs, formatArgs.length));
        } else {
            return null;
        }
    }

}
