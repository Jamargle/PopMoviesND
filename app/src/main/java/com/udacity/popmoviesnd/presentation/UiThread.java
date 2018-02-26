package com.udacity.popmoviesnd.presentation;

import com.udacity.popmoviesnd.domain.interactor.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler} which will execute actions
 * on the Android UI thread
 */
public final class UiThread implements PostExecutionThread {

    UiThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
