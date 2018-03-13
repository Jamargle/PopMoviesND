package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing a Movie
 */
public final class Movie {

    @SerializedName("poster_path") private String thumbnailPosterPath;
    private @Sorting int orderType;

    Movie(final Builder builder) {
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
        this.orderType = builder.orderType;
    }

    public String getThumbnailPosterPath() {
        return thumbnailPosterPath;
    }

    public void setThumbnailPosterPath(final String thumbnailPosterPath) {
        this.thumbnailPosterPath = thumbnailPosterPath;
    }

    public @Sorting
    int getOrderType() {
        return orderType;
    }

    public void setOrderType(@Sorting final int orderType) {
        this.orderType = orderType;
    }

    public static class Builder {

        private String thumbnailPosterPath;
        private @Sorting int orderType;

        public Builder thumbnailPosterPath(final String path) {
            this.thumbnailPosterPath = path;
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
