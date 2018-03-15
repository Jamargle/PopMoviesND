package com.udacity.popmoviesnd.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment<P extends BasePresenter<BasePresenter.BaseView>>
        extends Fragment
        implements BasePresenter.BaseView {

    protected abstract @NonNull P getPresenter();

    @Override
    public void onViewCreated(
            @NonNull final View view,
            @Nullable final Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getPresenter().attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getPresenter().getView() == null) {
            getPresenter().attachView(this);
        }
    }

    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().detachView();
    }

}
