package com.udacity.popmoviesnd.presentation.details;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.presentation.BaseActivity;

public final class DetailActivity extends BaseActivity
        implements MovieDetailFragment.Callback,
        DetailActivityPresenter.DetailActivityView {

    private DetailActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @NonNull
    @Override
    protected DetailActivityPresenter getPresenter() {
        if (presenter == null) {
            presenter = new DetailActivityPresenterImp();
        }
        return presenter;
    }

}
