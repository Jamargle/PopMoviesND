package com.udacity.popmoviesnd.presentation.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MovieListFragment extends BaseFragment
        implements MovieListFragmentPresenter.MovieListFragmentView,
        MovieListAdapter.OnMovieClickListener {

    @BindView(R.id.movies_grid_recyclerview) RecyclerView movieRecyclerView;
    @BindView(R.id.empty_list) TextView emptyListView;
    @BindView(R.id.movies_loading) ProgressBar loadingView;

    private MovieListFragmentPresenter presenter;
    private Callback callback;
    private MovieListAdapter adapter;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        initAdapter();
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.refreshMovies();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull
    @Override
    protected MovieListFragmentPresenter getPresenter() {
        if (presenter == null && getActivity() != null) {
            final WeakReference<Context> contextWeakReference = new WeakReference<>(getActivity().getApplicationContext());
            presenter = PresenterFactory.makeMovieListFragmentPresenter(contextWeakReference);
        }
        return presenter;
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showLoadMoviesError() {

    }

    @Override
    public void hideMoviesError() {

    }

    @Override
    public void updateMoviesToShow(final List<Movie> movies) {
        if (movieRecyclerView.getVisibility() == View.GONE) {
            movieRecyclerView.setVisibility(View.VISIBLE);
        }
        adapter.updateMovies(movies);
    }

    @Override
    public void showNoMoviesToShowScreen() {
        movieRecyclerView.setVisibility(View.GONE);
        emptyListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(final Movie movie) {
        startMovieDetailsActivity(movie);
    }

    private void initAdapter() {
        adapter = new MovieListAdapter(getContext(), new ArrayList<Movie>(), this);
    }

    private void initRecyclerView() {
        movieRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        movieRecyclerView.setLayoutManager(gridLayoutManager);

        movieRecyclerView.setAdapter(adapter);
    }

    private void startMovieDetailsActivity(final Movie movie) {
        callback.onItemSelected(movie);
    }

    interface Callback {

        void onItemSelected(Movie movie);

    }

}
