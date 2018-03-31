package com.udacity.popmoviesnd.presentation.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.MovieReview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<MovieReview> reviews;
    private final OnReviewClickListener listener;

    ReviewAdapter(
            final List<MovieReview> reviews,
            final OnReviewClickListener listener) {

        this.listener = listener;
        this.reviews = new ArrayList<>();
        this.reviews.addAll(reviews);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType) {

        return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(
            @NonNull final ReviewViewHolder holder,
            final int position) {

        holder.onBind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public interface OnReviewClickListener {

        void onReviewClicked(MovieReview review);

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review_author) TextView author;
        @BindView(R.id.review_content) TextView content;
        private MovieReview review;

        ReviewViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(final MovieReview review) {
            this.review = review;
            author.setText(review.getAuthor());
            content.setText(review.getContent());
        }

        @OnClick(R.id.review_view)
        public void onClick() {
            listener.onReviewClicked(review);
        }


    }

}
