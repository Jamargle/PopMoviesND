package com.udacity.popmoviesnd.presentation.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.popmoviesnd.BuildConfig;
import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final List<Movie> movieDataset;
    private OnMovieClickListener listener;
    private Context context;

    MovieListAdapter(
            final Context context,
            final List<Movie> movieList,
            final OnMovieClickListener listener) {

        movieDataset = movieList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType) {

        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(
            @NonNull final MovieViewHolder viewHolder,
            int position) {

        if (movieDataset != null && movieDataset.size() >= position) {
            viewHolder.bindMovie(movieDataset.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return movieDataset.size();
    }

    void updateMovies(final List<Movie> newDataSet) {
        movieDataset.clear();
        movieDataset.addAll(newDataSet);

        notifyDataSetChanged();
    }

    public interface OnMovieClickListener {

        void onMoviePosterClicked(Movie movie);

    }

    final class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image_container) ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_image_container)
        void onClick() {
            listener.onMoviePosterClicked(movieDataset.get(getAdapterPosition()));
        }

        void bindMovie(final Movie movie) {
            final String thumbnailPosterPath = movie.getThumbnailPosterPath();
            final String completePath;
            if (thumbnailPosterPath.contains(BuildConfig.BASE_IMAGE_URL)) {
                completePath = thumbnailPosterPath;
            } else {
                completePath = BuildConfig.BASE_IMAGE_URL + BuildConfig.IMAGE_MEDIUM_SIZE_URL + thumbnailPosterPath;
            }

            Picasso.with(context)
                    .load(completePath).fit()
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .error(R.drawable.ic_error)
                    .into(moviePoster);
        }
    }

}
