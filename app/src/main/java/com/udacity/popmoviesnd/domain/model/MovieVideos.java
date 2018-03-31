package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class MovieVideos {

    @SerializedName("id") private int movieId;
    @SerializedName("results") private List<Video> videos;

    public int getId() {
        return movieId;
    }

    public void setId(final int movieId) {
        this.movieId = movieId;
    }

    public List<Video> getResults() {
        return videos;
    }

    public void setResults(final List<Video> videos) {
        this.videos = videos;
    }

}
