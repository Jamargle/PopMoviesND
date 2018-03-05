package com.udacity.popmoviesnd.domain.model;

/**
 * Class representing a Movie
 */
public final class Movie {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_MEDIUM_SIZE = "/w185";

    private String thumbnailPosterPath;

    Movie(Builder builder) {
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
    }

    public String getThumbnailPosterPath() {
        return thumbnailPosterPath;
    }

    public void setThumbnailPosterPath(final String thumbnailPosterPath) {
        this.thumbnailPosterPath = thumbnailPosterPath;
    }

    public static class Builder {

        private String thumbnailPosterPath;

        public Builder thumbnailPosterPath(final String path) {
            if (!path.contains(BASE_IMAGE_URL)) {
                this.thumbnailPosterPath = BASE_IMAGE_URL + IMAGE_MEDIUM_SIZE + path;
            } else {
                this.thumbnailPosterPath = path;
            }
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

}
