package com.udacity.popmoviesnd.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing a Movie
 */
public final class Movie implements Parcelable {

    @SerializedName("id") private long movieApiId;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("overview") private String overview;
    @SerializedName("poster_path") private String thumbnailPosterPath;
    @SerializedName("vote_average") private float voteAverage;
    @SerializedName("release_date") private String releaseDate;
    private @Sorting int orderType;

    Movie(final Builder builder) {
        this.movieApiId = builder.movieDbId;
        this.movieApiId = builder.movieApiId;
        this.originalTitle = builder.originalTitle;
        this.overview = builder.overview;
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
        this.voteAverage = builder.voteAverage;
        this.releaseDate = builder.releaseDate;
        this.orderType = builder.orderType;
    }

    public String getThumbnailPosterPath() {
        return thumbnailPosterPath;
    }

    public void setThumbnailPosterPath(final String thumbnailPosterPath) {
        this.thumbnailPosterPath = thumbnailPosterPath;
    }

    public @Sorting int getOrderType() {
        return orderType;
    }

    public void setOrderType(@Sorting final int orderType) {
        this.orderType = orderType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Movie(final Parcel in) {
        movieApiId = in.readLong();
        originalTitle = in.readString();
        overview = in.readString();
        thumbnailPosterPath = in.readString();
        voteAverage = in.readFloat();
        releaseDate = in.readString();
        orderType = in.readInt();
    }

    @Override
    public void writeToParcel(
            final Parcel dest,
            final int flags) {

        dest.writeLong(movieApiId);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(thumbnailPosterPath);
        dest.writeFloat(voteAverage);
        dest.writeString(releaseDate);
        dest.writeInt(orderType);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(final Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(final int size) {
            return new Movie[size];
        }
    };

    public static class Builder {

        private long movieDbId;
        private int movieApiId;
        private String originalTitle;
        private String overview;
        private String thumbnailPosterPath;
        private float voteAverage;
        private String releaseDate;
        private @Sorting int orderType;

        public Builder movieDbId(final long movieDbId) {
            this.movieDbId = movieDbId;
            return this;
        }

        public Builder movieApiId(final int movieApiId) {
            this.movieApiId = movieApiId;
            return this;
        }

        public Builder originalTitle(final String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder overview(final String overview) {
            this.overview = overview;
            return this;
        }

        public Builder thumbnailPosterPath(final String path) {
            this.thumbnailPosterPath = path;
            return this;
        }

        public Builder voteAverage(final float voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder releaseDate(final String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder orderType(final @Sorting int orderType) {
            this.orderType = orderType;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

}
