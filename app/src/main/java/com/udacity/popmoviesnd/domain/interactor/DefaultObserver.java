package com.udacity.popmoviesnd.domain.interactor;

import com.udacity.popmoviesnd.presentation.BasePresenter;

import io.reactivex.observers.DisposableObserver;

/**
 * Default {@link DisposableObserver} base class to be used whenever you want default error handling.
 */
public class DefaultObserver<T> extends DisposableObserver<T> {

    private BasePresenter.BaseView view;

    public DefaultObserver(final BasePresenter.BaseView view) {
        this.view = view;
    }

    @Override
    public void onNext(final T t) {
        if (view != null) {
            onAny();
            processOnNext(t);
        }
    }

    @Override
    public void onComplete() {
        if (view != null) {
            processOnComplete();
        }
    }

    @Override
    public void onError(final Throwable exception) {
        if (view != null) {
            onAny();
            processOnError(exception);
        }
    }

    public void onAny() {
        // no-op by default.
    }

    public void processOnNext(final T t) {
        // no-op by default.
    }

    public void processOnComplete() {
        // no-op by default.
    }

    public void processOnError(final Throwable exception) {
        // no-op by default.
    }

}
