package com.udacity.popmoviesnd.presentation.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MovieDetailFragment extends BaseFragment
        implements MovieDetailFragmentPresenter.MovieDetailFragmentView {

    @BindView(R.id.original_movie_title) TextView titleView;
    @BindView(R.id.movie_image) ImageView moviePoster;
    @BindView(R.id.overview) TextView overviewView;
    @BindView(R.id.release_year) TextView releaseYearView;
    @BindView(R.id.vote_average) TextView voteAverageView;

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

    public void setMovieToShow(final Movie movie) {
        presenter.loadMovieDetails(movie);
    }

    @Override
    public void setTitle(final String originalTitle) {
        titleView.setText(originalTitle);
    }

    @Override
    public void setOverview(final String overview) {
        overviewView.setText(overview);
    }

    @Override
    public void setReleaseYear(final String releaseDate) {
        releaseYearView.setText(releaseDate);
    }

    @Override
    public void setVoteAverage(final float voteAverage) {
        voteAverageView.setText(String.valueOf(voteAverage));
    }

    @Override
    public void setMovieImage(final String posterPath) {
        if (getActivity() != null) {
            Glide.with(getActivity())
                    .load(posterPath)
                    .into(moviePoster);
        }
    }

    interface Callback {

    }

}
