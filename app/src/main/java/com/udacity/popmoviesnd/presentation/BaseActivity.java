package com.udacity.popmoviesnd.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter<BasePresenter.BaseView>>
        extends AppCompatActivity
        implements BasePresenter.BaseView {

    protected abstract P getPresenter();

    @Override
    public void onCreate(
            @Nullable final Bundle savedInstanceState,
            @Nullable final PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
        getPresenter().attachView(this);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().detachView();
    }

}
