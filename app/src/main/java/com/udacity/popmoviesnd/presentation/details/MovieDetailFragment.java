package com.udacity.popmoviesnd.presentation.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public final class MovieDetailFragment extends BaseFragment
        implements MovieDetailFragmentPresenter.MovieDetailFragmentView {


    private MovieDetailFragmentPresenter presenter;
    private Callback callback;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof MovieDetailFragment.Callback) {
            callback = (MovieDetailFragment.Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(
            @NonNull final View view,
            @Nullable final Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull
    @Override
    protected MovieDetailFragmentPresenter getPresenter() {
        if (presenter == null && getActivity() != null) {
            final WeakReference<Context> contextWeakReference = new WeakReference<>(getActivity().getApplicationContext());
            presenter = PresenterFactory.makeMovieDetailFragmentPresenter(contextWeakReference);
        }
        return presenter;
    }

    interface Callback {

    }

}
