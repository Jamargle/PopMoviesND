package com.udacity.popmoviesnd.presentation.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.app.dependencies.PresenterFactory;
import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.domain.model.Video;
import com.udacity.popmoviesnd.presentation.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MovieDetailFragment extends BaseFragment
        implements MovieDetailFragmentPresenter.MovieDetailFragmentView,
        TrailerAdapter.OnTrailerClickListener,
        ReviewAdapter.OnReviewClickListener {

    @BindView(R.id.original_movie_title) TextView titleView;
    @BindView(R.id.movie_image) ImageView moviePoster;
    @BindView(R.id.overview) TextView overviewView;
    @BindView(R.id.release_year) TextView releaseYearView;
    @BindView(R.id.vote_average) TextView voteAverageView;
    @BindView(R.id.mark_as_favorite_button) Button favoriteButton;
    @BindView(R.id.trailer_title) TextView trailerTitleView;
    @BindView(R.id.trailer_list) RecyclerView trailersListView;
    @BindView(R.id.review_title) TextView reviewTitleView;
    @BindView(R.id.review_list) RecyclerView reviewsListView;

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

    @OnClick(R.id.mark_as_favorite_button)
    void changeFavoriteState() {
        presenter.onChangeFavoriteState();
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
    public void setVoteAverage(final String voteAverage) {
        voteAverageView.setText(voteAverage);
    }

    @Override
    public void setMovieImage(final String posterPath) {
        if (getActivity() != null) {
            Picasso.with(getActivity())
                    .load(posterPath)
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .error(R.drawable.ic_error)
                    .into(moviePoster);
        }
    }

    @Override
    public void setFavoriteButtonText(final boolean isFavorite) {
        favoriteButton.setText(isFavorite
                ? getString(R.string.unmark_for_non_favorite)
                : getString(R.string.mark_as_favorite));
    }

    @Override
    public void onUpdateMovieError() {
        callback.onUpdateMovieError();
    }

    @Override
    public void showTrailers(final List<Video> videos) {
        trailerTitleView.setVisibility(View.VISIBLE);
        trailersListView.setVisibility(View.VISIBLE);
        setUpTrailerList(videos);
    }

    private void setUpTrailerList(final List<Video> videos) {
        trailersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trailersListView.setHasFixedSize(true);
        trailersListView.setAdapter(new TrailerAdapter(videos, this));
    }

    @Override
    public void showErrorFetchingTrailers() {
        Toast.makeText(getActivity(), "TODO Error during download trailers", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReviews(final List<MovieReview> reviews) {
        reviewTitleView.setVisibility(View.VISIBLE);
        reviewsListView.setVisibility(View.VISIBLE);
        setUpReviewList(reviews);
    }

    private void setUpReviewList(final List<MovieReview> reviews) {
        reviewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsListView.setHasFixedSize(true);
        reviewsListView.setAdapter(new ReviewAdapter(reviews, this));
    }

    @Override
    public void showErrorFetchingReviews() {
        Toast.makeText(getActivity(), "TODO Error during download reviews", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrailerClicked(final Video trailer) {
        callback.onTrailerClicked(trailer);
    }

    @Override
    public void onReviewClicked(final MovieReview review) {
        callback.onReviewClicked(review);
    }

    interface Callback {

        void onUpdateMovieError();

        void onTrailerClicked(Video trailer);

        void onReviewClicked(MovieReview review);

    }

}
