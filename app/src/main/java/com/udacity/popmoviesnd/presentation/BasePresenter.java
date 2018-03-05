package com.udacity.popmoviesnd.presentation;

public interface BasePresenter<V extends BasePresenter.BaseView> {

    V getView();

    void attachView(V view);

    void detachView();

    interface BaseView {

    }

}
