package com.udacity.popmoviesnd.presentation.trailer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popmoviesnd.R;
import com.udacity.popmoviesnd.domain.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final List<Video> trailers;
    private final OnTrailerClickListener listener;

    TrailerAdapter(
            final List<Video> trailers,
            final OnTrailerClickListener listener) {

        this.listener = listener;
        this.trailers = new ArrayList<>();
        this.trailers.addAll(trailers);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType) {

        return new TrailerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(
            @NonNull final TrailerViewHolder holder,
            final int position) {

        holder.onBind(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailersToShow(final List<Video> videos) {
        trailers.clear();
        trailers.addAll(videos);
        notifyDataSetChanged();
    }

    public interface OnTrailerClickListener {

        void onTrailerClicked(Video trailer);

    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailer_name) TextView name_text;
        private Video trailer;

        TrailerViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(final Video trailer) {
            this.trailer = trailer;
            name_text.setText(trailer.getName());
        }

        @OnClick(R.id.trailer_view)
        public void onClick() {
            listener.onTrailerClicked(trailer);
        }


    }

}
